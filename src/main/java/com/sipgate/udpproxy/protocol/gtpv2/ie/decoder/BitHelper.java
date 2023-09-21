package com.sipgate.udpproxy.protocol.gtpv2.ie.decoder;

import java.nio.ByteBuffer;

/**
 * Helper class for bit operations. My brain hurts when I try to do this in my head, so I wrote this class
 * to make my code more expressive.
 *
 * @author Lennart Rosam <rosam@sipgate.de>
 */
public class BitHelper {

	private BitHelper() {
	}

	/**
	 * Checks if a given bit is set in a byte.
	 *
	 * @param b           The byte to check
	 * @param bitPosition The position of the bit to check (1-8)
	 * @return true if the bit is set, false otherwise
	 */
	public static boolean isBitSet(final byte b, final int bitPosition) {
		final int mask = 1 << (bitPosition - 1);
		return (b & mask) != 0;
	}

	/**
	 * Sets a bit in a byte.
	 *
	 * @param b           The byte to set the bit in
	 * @param bitPosition The position of the bit to set (1-8)
	 * @return The modified byte with the bit set
	 */
	public static byte setBit(final byte b, final int bitPosition) {
		final int mask = 1 << (bitPosition - 1);
		return (byte) (b | mask);
	}

	/**
	 * Sets a bit in a byte to either 1 or 0 indicated by the boolean.
	 *
	 * @param b           The byte to set the bit in
	 * @param bitPosition The position of the bit to set (1-8)
	 * @param value       The value to set the bit to. True for 1, false for 0
	 * @return The modified byte with the bit set
	 */
	public static byte setBitTo(final byte b, final int bitPosition, final boolean value) {
		if (value) {
			return setBit(b, bitPosition);
		}
		return clearBit(b, bitPosition);
	}

	/***
	 * Clears a bit in a byte.
	 *
	 * @param b The byte to clear the bit in
	 * @param bitPosition The position of the bit to clear (1-8)
	 * @return The modified byte with the bit cleared
	 */
	public static byte clearBit(final byte b, final int bitPosition) {
		final int mask = ~(1 << (bitPosition - 1));
		return (byte) (b & mask);
	}

	/**
	 * Returns just the lower nibble of a byte.
	 * @param b The byte to get the lower nibble from
	 * @return The lower nibble
	 */
	public static byte lowerNibble(final byte b) {
		return (byte) ((b & 0b00001111) << 4);
	}

	/**
	 * Returns just the upper nibble of a byte.
	 * @param b The byte to get the upper nibble from
	 * @return The upper nibble
	 */
	public static byte upperNibble(final byte b) {
		return (byte) ((b & 0b11110000) >>> 4);
	}

	/**
	 * Converts a 16 bit integer to an 32 bit integer.
	 *
	 * @param fourthOctet  The fourth octet of the final integer
	 * @param fifthOctet   The fifth octet of the final integer
	 * @return The integer
	 */
	public static int int16ToInt32(final byte fourthOctet, final byte fifthOctet) {
		return ByteBuffer.wrap(new byte[]{0x0, 0x0, fourthOctet, fifthOctet}).getInt();
	}

	/**
	 * Converts a 24 bit integer to an 32 bit integer. Wierd telco datatype that is used in GTPv2.
	 * @param thirdOctet The third octet of the final integer
	 * @param fourthOctet The fourth octet of the final integer
	 * @param fifthOctet The fifth octet of the final integer
	 * @return The integer
	 */
	public static int int24ToInt32(final byte thirdOctet, final byte fourthOctet, final byte fifthOctet) {
		return ByteBuffer.wrap(new byte[]{0x0, thirdOctet, fourthOctet, fifthOctet}).getInt();
	}

	/**
	 * Converts 4 bytes to an 32 bit integer. This is just a convenience method to make the code more readable.
	 *
	 * @param firstOctet The first octet of the final integer
	 * @param secondOctet The second octet of the final integer
	 * @param thirdOctet The third octet of the final integer
	 * @param fourthOctet The fourth octet of the final integer
	 * @return The integer
	 */
	public static int toInt32(final byte firstOctet, final byte secondOctet, final byte thirdOctet, final byte fourthOctet) {
		return ByteBuffer.wrap(new byte[]{firstOctet, secondOctet, thirdOctet, fourthOctet}).getInt();
	}
}
