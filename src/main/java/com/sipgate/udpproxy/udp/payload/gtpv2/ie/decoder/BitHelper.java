package com.sipgate.udpproxy.udp.payload.gtpv2.ie.decoder;

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
	 * Sets the lower nibble of a byte to a given 4 bit integer.
	 *
	 * @param fourBitInt The 4 bit integer to set the lower nibble to (0-15)
	 * @param b The byte to set the lower nibble in
	 * @return The modified byte with the lower nibble set
	 */
	public static byte setLowerNibble(final int fourBitInt, final byte b) {
		if (fourBitInt > 0b1111) {
			throw new IllegalArgumentException("fourBitInt must be between 0 and 15");
		}

		return (byte) ((b & 0b11110000) | (fourBitInt & 0b00001111));
	}

	/**
	 * Sets the upper nibble of a byte to a given 4 bit integer.
	 *
	 * @param fourBitInt The 4 bit integer to set the upper nibble to (0-15)
	 * @param b The byte to set the upper nibble in
	 * @return The modified byte with the upper nibble set
	 */
	public static byte setUpperNibble(final int fourBitInt, final byte b) {
		if (fourBitInt > 0b1111) {
			throw new IllegalArgumentException("fourBitInt must be between 0 and 15");
		}

		return (byte) ((b & 0b00001111) | (fourBitInt << 4));
	}

	public static byte setThreeBitInt(final int threeBitInt, final int lowestOrderBit, final int highestOrderBit, final byte b) {
		if (threeBitInt > 0b111) {
			throw new IllegalArgumentException("threeBitInt must be between 0 and 7");
		}

		byte mask = 0b00000000;
		for (int i = highestOrderBit; i >= lowestOrderBit; i--) {
			mask = setBit(mask, i);
		}
		return (byte) ((b & ~mask) | (threeBitInt << lowestOrderBit - 1));
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
	 * Converts a byte to an integer that is between lowestBit and highestBit.
	 * @param b the byte to convert
	 * @param lowestBit the lowest bit to convert (1-8 inclusive)
	 * @param highestBit the highest bit to convert (1-8 inclusive)
	 * @return The integer
	 */
	public static int toInt(final byte b, final int lowestBit, final int highestBit) {
		byte mask = 0b00000000;
		for (int i = highestBit; i >= lowestBit; i--) {
			mask = setBit(mask, i);
		}
		return (b & mask) >>> lowestBit - 1;
	}

	public static int toInt(final byte b) {
		return toInt(b, 1, 8);
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
