package com.sipgate.udpproxy.udp.payload.gtpv2.ie.decoder;

/**
 * Decodes MncMcc from a byte array.
 * <p>
 * Source: https://github.com/Takuto88/cell-id-decoder/blob/main/src/main/java/de/takuto/cellid/Application.java
 * @author Lennart Rosam <hello@takuto.de>
 */
public class MncMcc {
	private MncMcc(){}

	public static int decodeMcc(final byte firstOctet, final byte secondOctet) {

		final int mccFirstDigit = firstOctet & 0x0F;
		final int mccSecondDigit = (firstOctet & 0xF0) >> 4;
		final int mccThirdDigit =  secondOctet & 0x0F;

		return (mccFirstDigit * 100) + (mccSecondDigit * 10) + mccThirdDigit ;
	}

	public static int decodeMnc(final byte secondOctet, final byte thirdOctet) {
		final int mncFirstDigit = (thirdOctet & 0xF0) >> 4;
		final int mncSecondDigit = thirdOctet & 0x0F;
		final int mncThirdDigit = (secondOctet & 0xF0) >> 4;
		final boolean hasFiller = mncThirdDigit == 0xF;
		final boolean isSingleDigitMnc = (mncSecondDigit == 0x00 && hasFiller);
		final boolean isTwoDigitMnc = hasFiller && !isSingleDigitMnc;

		if (isSingleDigitMnc) {
			return mncFirstDigit;
		}

		if (isTwoDigitMnc) {
			return (mncFirstDigit * 10) + mncSecondDigit;
		}

		return (mncFirstDigit * 100) + (mncSecondDigit * 10) + mncThirdDigit;
	}

}
