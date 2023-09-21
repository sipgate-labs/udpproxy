package com.sipgate.udpproxy.udp;

import com.sipgate.udpproxy.udp.payload.PacketRewriter;
import com.sipgate.udpproxy.udp.payload.PayloadProtocol;
import com.sipgate.udpproxy.udp.payload.sip.SipRewriter;

import java.io.IOException;
import java.net.*;
import java.util.function.Supplier;

/**
 * A dialog represents a communication between a client and a proxy target.
 */
public class Dialog implements Runnable {
	private static final int RECV_TIMEOUT_MS = 1000 * 60 * 5; // 5 minutes
	private final DatagramSocket proxySource;
	private final DatagramSocket proxyTarget;
	private final PacketRewriter packetRewriter;
	private final InetAddress clientAddress;
	private final int clientPort;
	private final int bufferSize;
	private long lastActivity = System.currentTimeMillis();
	private boolean running = true;
	private Thread thread;

	Dialog(final DatagramSocket proxySource, final DatagramSocket proxyTarget, final int bufferSize, final PacketRewriter rewriter, final InetAddress clientAddress, final int clientPort) {
		this.proxySource = proxySource;
		this.proxyTarget = proxyTarget;
		this.bufferSize = bufferSize;
		this.clientAddress = clientAddress;
		this.clientPort = clientPort;
		this.packetRewriter = rewriter;

		try {
			proxyTarget.setSoTimeout(RECV_TIMEOUT_MS);
		} catch (final SocketException e) {
			throw new RuntimeException(e);
		}
	}

	public static Dialog create(final DatagramSocket proxySource, final int bufferSize, final DatagramPacket clientPacket, final PayloadProtocol payloadProtocol) {
		final Supplier<PacketRewriter>  rewriter = () -> {
			switch (payloadProtocol) {
				case SIP:
					return new SipRewriter();
				default:
					throw new IllegalArgumentException("Unsupported protocol: " + payloadProtocol);
			}
		};
		try {
			return new Dialog(proxySource, new DatagramSocket(), bufferSize, rewriter.get(), clientPacket.getAddress(), clientPacket.getPort());
		} catch (final SocketException e) {
			System.err.println("Error while creating client socket: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@Override
	public void run() {
		thread = Thread.currentThread();
		while (System.currentTimeMillis() - lastActivity < RECV_TIMEOUT_MS && running) {
			try {
				// Listen for answers from proxy target
				final var buffer = new byte[bufferSize];
				final var packetFromTarget = new DatagramPacket(buffer, buffer.length);
				proxyTarget.receive(packetFromTarget);
				System.out.println("Got packet from proxy server target: " + packetFromTarget.getAddress() + ":" + packetFromTarget.getPort());

				lastActivity = System.currentTimeMillis();

				// Forward modified answer to proxy source
				final var payload = packetRewriter.rewritePayload(packetFromTarget);
				final var packetToSource = new DatagramPacket(
						payload,
						payload.length,
						clientAddress,
						clientPort);

				proxySource.send(packetToSource);
			} catch (final IOException e) {
				System.err.println("Error while processing answer from: " + proxyTarget.getLocalSocketAddress() + ":" + proxyTarget.getLocalPort() + ": " + e.getMessage());
				running = false;
			}
		}
		stop();
	}

	public void sendToTarget(final DatagramPacket unmodifiedPacket, final String targetAddress, final int targetPort) {
		try {
			final var payload = packetRewriter.rewritePayload(unmodifiedPacket);
			final var packet = new DatagramPacket(payload, payload.length, new InetSocketAddress(targetAddress, targetPort));
			proxyTarget.send(packet);
		} catch (final IOException e) {
			System.err.println("Error while sending packet to target " + targetAddress + ":" + proxyTarget.getLocalPort() + ": " + e.getMessage());
			running = false;
		}
	}


	public void stop() {
		running = false;
		if (proxyTarget != null && !proxyTarget.isClosed()) {
			proxyTarget.close();
		}

		if (thread != null && thread.isAlive()) {
			thread.interrupt();
		}
	}
}
