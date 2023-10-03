package com.sipgate.udpproxy.udp.proxy.processors.gtpv2.ie.decoder;

import java.nio.charset.StandardCharsets;

public class Apn {

	public static String decode(final byte[] payload) {
		// Encoding: TS 123.003, Clause 9.1: Each label is encoded as a length octet followed by that number of octets
		final var apn = new StringBuilder();
		var nextLengthIndex = 0;
		for (int i = 0; i < payload.length; i++) {
			if (i != nextLengthIndex) {
				apn.append((char) payload[i]);
				continue;
			}

			if (i != 0) {
				apn.append(".");
			}
			nextLengthIndex = i + (payload[i] & 0xff) + 1;
		}


		return apn.toString();
	}

	public static byte[] encode(final String apn) {
		final var labels = apn.split("\\.");
		final var payload = new byte[apn.getBytes(StandardCharsets.US_ASCII).length];

		int payloadIndex = 0;
		for (final var label : labels) {
			payload[payloadIndex] = (byte) label.length();
			final var labelBytes = label.getBytes(StandardCharsets.US_ASCII);
			System.arraycopy(labelBytes, 0, payload, payloadIndex + 1, labelBytes.length);
			payloadIndex += labelBytes.length + 1;
		}

		return payload;
	}
}
