package com.sipgate.udpproxy.udp.proxy.processors.gtpv2.ie.decoder;

/**
 * Decodes TBCD encoded byte arrays. In those arrays, numbers are encoded in 4 bit chunks, with the most significant
 * nibble being the SECOND digit and the least significant nibble being the FIRST digit.
 * <p>
 * Allows for efficient encoding of long numbers in just a few bytes. The last byte may be terminated with a 0xF.
 * @author Lennart Rosam <rosam@sipgate.de>
 */
public class Tbcd {

	private Tbcd() {}

	/**
	 * Decodes a TBCD encoded byte array into a string.
	 * @param payload The byte array to decode
	 * @return The decoded, human-readable string
	 */
	public static String decode(final byte[] payload) {

		final byte lastByte = payload[payload.length - 1];
		final boolean lastDigitTerminated = ((byte) (lastByte & 0b11110000) >> 4) < 0x0;
		final char[] numbers = new char[payload.length * 2 - (lastDigitTerminated ? 1 : 0)];
		for (int i = 0; i < payload.length; i++) {
			numbers[i * 2] = (char) ((payload[i] & 0b00001111) + 0x30);
			// Last digit might be termination digit
			if (i * 2 + 1 >= numbers.length) {
				break;
			}
			numbers[i * 2 + 1] = (char) (((payload[i] & 0b11110000) >> 4) + 0x30);
		}

		return new String(numbers);
	}
}
