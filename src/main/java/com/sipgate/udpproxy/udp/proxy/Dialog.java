package com.sipgate.udpproxy.udp.proxy;

import com.sipgate.udpproxy.udp.proxy.processors.MessageProcessor;
import com.sipgate.udpproxy.udp.proxy.processors.Message;
import com.sipgate.udpproxy.udp.proxy.processors.sip.SipMessage;
import com.sipgate.udpproxy.udp.proxy.processors.sip.SipMessageProcessor;

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
	private final MessageProcessor messageProcessor;
	private final InetAddress clientAddress;
	private final ProxyProtocol proxyProtocol;
	private final int clientPort;
	private final int bufferSize;
	private long lastActivity = System.currentTimeMillis();
	private boolean running = true;
	private Thread thread;

	Dialog(final DatagramSocket proxySource, final DatagramSocket proxyTarget, final ProxyProtocol proxyProtocol, final int bufferSize, final MessageProcessor rewriter, final InetAddress clientAddress, final int clientPort) {
		this.proxySource = proxySource;
		this.proxyTarget = proxyTarget;
		this.bufferSize = bufferSize;
		this.clientAddress = clientAddress;
		this.clientPort = clientPort;
		this.messageProcessor = rewriter;
		this.proxyProtocol = proxyProtocol;

		try {
			proxyTarget.setSoTimeout(RECV_TIMEOUT_MS);
		} catch (final SocketException e) {
			throw new RuntimeException(e);
		}
	}

	public static Dialog create(final DatagramSocket proxySource, final int bufferSize, final DatagramPacket clientPacket, final ProxyProtocol proxyProtocol) {
		final Supplier<MessageProcessor>  rewriter = () -> switch (proxyProtocol) {
			case SIP -> new SipMessageProcessor();
		};
		try {
			return new Dialog(proxySource, new DatagramSocket(), proxyProtocol, bufferSize, rewriter.get(), clientPacket.getAddress(), clientPacket.getPort());
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

				final var message = createMessage(packetFromTarget);
				// Forward modified answer to proxy source
				messageProcessor.rewriteMessage(packetFromTarget, message);
				message.packBytes();
				final var payload = message.finalizeForNetwork();
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
			final var message = createMessage(unmodifiedPacket);
			messageProcessor.rewriteMessage(unmodifiedPacket, message);
			message.packBytes();
			final var payload = message.finalizeForNetwork();
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

	private Message createMessage(final DatagramPacket packet) {
		return switch (proxyProtocol) {
			case SIP -> new SipMessage(packet.getData());
		};
	}
}
