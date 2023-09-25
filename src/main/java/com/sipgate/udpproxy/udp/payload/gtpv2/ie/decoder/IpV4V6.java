package com.sipgate.udpproxy.udp.payload.gtpv2.ie.decoder;

public class IpV4V6 {

	public static String decodeV4(final byte[] payload) {
		return String.format("%d.%d.%d.%d", payload[0] & 0xff, payload[1] & 0xff, payload[2] & 0xff, payload[3] & 0xff);
	}

	public static byte[] encodeV4(final String ipv4) {
		final var octets = ipv4.split("\\.");
		final var payload = new byte[4];
		for (int i = 0; i < 4; i++) {
			payload[i] = (byte) Integer.parseInt(octets[i]);
		}
		return payload;
	}

	public static String decodeV6(final byte[] payload) {
		final var ipv6Address = new StringBuilder();
		for (int i = 0; i < 16; i += 2) {
			ipv6Address.append(String.format("%02x%02x", payload[i] & 0xff, payload[i + 1] & 0xff));
			if (i < 14) {
				ipv6Address.append(":");
			}
		}
		return ipv6Address.toString();
	}

	public static byte[] encodeV6(final String ipv6) {
		if (ipv6.contains("::")) {
			throw new IllegalArgumentException("IPv6 shorthand form is not supported! (Must not contain ::");
		}

		final var octets = ipv6.split(":");
		final var payload = new byte[16];
		for (int i = 0; i < 8; i++) {
			final var octet = octets[i];
			payload[i * 2] = (byte) Integer.parseInt(octet.substring(0, 2), 16);
			payload[i * 2 + 1] = (byte) Integer.parseInt(octet.substring(2, 4), 16);
		}
		return payload;
	}
}
