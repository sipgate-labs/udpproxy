package com.sipgate.udpproxy.udp;

import com.sipgate.udpproxy.protocol.ProxyTargetResolver;
import com.sipgate.udpproxy.protocol.sip.SipTargetResolver;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A proxy server listens for traffic from a proxy source and forwards it to a proxy target.
 */
public class ProxyServer implements Runnable {
	private final DatagramSocket serverSocket;
	private final int bufferSize;
	private final ExecutorService executorService = Executors.newFixedThreadPool(1000);
	private final Map<String, Dialog> dialogs = new HashMap<>();
	private final ProxyTargetResolver targetResolver = new SipTargetResolver(); // FIXME: Hardcoded resolver

	private boolean running = true;

	public ProxyServer(final DatagramSocket serverSocket, final int bufferSize) {
		this.serverSocket = serverSocket;
		this.bufferSize = bufferSize;
	}

	@Override
	public void run() {
		System.out.println("Proxy server listening on " + serverSocket.getLocalSocketAddress());
		while (running) {
			try {
				// Receive packet from proxy source
				final var buffer = new byte[bufferSize];
				final var fromClient = new DatagramPacket(buffer, buffer.length);
				serverSocket.receive(fromClient);
				System.out.println("Got packet from proxy server client: " + fromClient.getAddress() + ":" + fromClient.getPort());

				// Create dialog if not exists and listen for traffic from proxy target
				final var dialogKey = getDialogKey(fromClient);
				final var dialog = dialogs.computeIfAbsent(dialogKey, key -> Dialog.create(serverSocket, bufferSize, fromClient));
				executorService.submit(dialog);

				// Lookup target and forward packet to proxy target
				final var target = targetResolver.resolveTarget(fromClient);
				dialog.sendToTarget(fromClient, target.host(), target.port());
			} catch (final Exception e) {
				System.err.println("Error while proxying traffic on server socket: " + serverSocket.getLocalSocketAddress() + ": " + e.getMessage());
				running = false;
			}
		}

		stop();
	}

	public void stop() {
		running = false;
		dialogs.values().parallelStream().forEach(Dialog::stop);
		executorService.shutdown();
		if (!serverSocket.isClosed()) {
			serverSocket.close();
		}
	}

	private String getDialogKey(final DatagramPacket packet) {
		return packet.getAddress().getHostAddress() + ":" + packet.getPort();
	}

}
