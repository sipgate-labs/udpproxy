package com.sipgate.udpproxy.udp;

import com.sipgate.udpproxy.udp.payload.Protocol;

import java.net.*;
import java.util.Optional;

public final class ServerFactory {

	private ServerFactory() {
	}

	public static Optional<ProxyServer> createServer(final String ip, final int port, final String device, final Protocol protocol) {

		try {
			return Optional.of(new ProxyServer(
					new DatagramSocket(getSocketAddress(ip, port, device)),
					4096,
					protocol
			));
		} catch (final SocketException e) {
			System.err.println("Cannot create server: " + e.getMessage());
		}

		return Optional.empty();
	}

	private static InetSocketAddress getSocketAddress(final String ip, final int port, final String device) throws SocketException {
		if ("any".equals(device)) {
			if (ip.isEmpty()) {
				return new InetSocketAddress(port);
			}

			return new InetSocketAddress(ip, port);
		}

		final NetworkInterface networkInterface = NetworkInterface.getByName(device);
		while (networkInterface.getInetAddresses().hasMoreElements()) {
			final InetAddress inetAddress = networkInterface.getInetAddresses().nextElement();
			if (inetAddress.getHostAddress().equals(ip) || ip.isEmpty()) {
				return new InetSocketAddress(inetAddress, port);
			}
		}

		throw new SocketException("Cannot find interface " + device + " with ip " + ip);
	}
}
