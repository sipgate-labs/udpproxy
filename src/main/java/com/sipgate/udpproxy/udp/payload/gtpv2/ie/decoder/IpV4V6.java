package com.sipgate.udpproxy.udp.payload.gtpv2.ie.decoder;

public class IpV4V6 {

	public static String decodeV4(final byte[] payload) {
		return String.format("%d.%d.%d.%d", payload[0] & 0xff, payload[1] & 0xff, payload[2] & 0xff, payload[3] & 0xff);
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
}
