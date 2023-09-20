package com.sipgate.udpproxy;

import com.sipgate.udpproxy.udp.ProxyServer;
import sun.misc.Signal;

import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.Optional;

public class Application {

	private final ProxyServer server;

	public Application() throws SocketException {
		this.server = new ProxyServer(
				new DatagramSocket(new InetSocketAddress(5060)), // FIXME: hardcoded listen port
				4096
		);
	}

	public static void main(final String[] args) {
		final var maybeApp = createApp();
		if (maybeApp.isEmpty()) {
			return;
		}

		final var app = maybeApp.get();
		app.proxyUdp();
	}

	private static Optional<Application> createApp() {
		try {
			return Optional.of(new Application());
		} catch (final SocketException e) {
			System.err.println("Cannot create server: " + e.getMessage());
			return Optional.empty();
		}
	}

	public void proxyUdp() {
		final var serverThread = new Thread(server, "proxy-server");

		serverThread.start();

		Signal.handle(new Signal("INT"), signal -> server.stop());

		try {
			serverThread.join();
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
		}

	}
}
