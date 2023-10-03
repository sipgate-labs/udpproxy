package com.sipgate.udpproxy.udp.proxy.processors.gtpv2.ie;

import com.sipgate.udpproxy.udp.proxy.processors.Message;
import com.sipgate.udpproxy.udp.proxy.processors.Payload;

import static com.sipgate.udpproxy.udp.proxy.processors.gtpv2.ie.decoder.BitHelper.isBitSet;

public class Indication extends GenericInformationElement {

	public Indication(final Payload payload, final Message parent, final int windowOffset, final int windowSize) {
		super(payload, parent, windowOffset, windowSize);
	}

	public boolean isDaf() {
		return getPayloadLength() >= IE_HEADER_SIZE + 1 && isBitSet(getPayloadByte(IE_HEADER_SIZE), 8);
	}

	public boolean isDtf() {
		return getPayloadLength() >= IE_HEADER_SIZE + 1 && isBitSet(getPayloadByte(IE_HEADER_SIZE), 7);
	}

	public boolean isHi() {
		return getPayloadLength() >= IE_HEADER_SIZE + 1 && isBitSet(getPayloadByte(IE_HEADER_SIZE), 6);
	}

	public boolean isDfi() {
		return getPayloadLength() >= IE_HEADER_SIZE + 1 && isBitSet(getPayloadByte(IE_HEADER_SIZE), 5);
	}

	public boolean isOi() {
		return getPayloadLength() >= IE_HEADER_SIZE + 1 && isBitSet(getPayloadByte(IE_HEADER_SIZE), 4);
	}

	public boolean isIsrsi() {
		return getPayloadLength() >= IE_HEADER_SIZE + 1 && isBitSet(getPayloadByte(IE_HEADER_SIZE), 3);
	}

	public boolean isIrai() {
		return getPayloadLength() >= IE_HEADER_SIZE + 1 && isBitSet(getPayloadByte(IE_HEADER_SIZE), 2);
	}

	public boolean isSgwci() {
		return getPayloadLength() >= IE_HEADER_SIZE + 1 && isBitSet(getPayloadByte(IE_HEADER_SIZE), 1);
	}

	public boolean isSqci() {
		return getPayloadLength() >= 2 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 1), 8);
	}

	public boolean isUimsi() {
		return getPayloadLength() >= 2 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 1), 7);
	}

	public boolean isCfsi() {
		return getPayloadLength() >= 2 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 1), 6);
	}

	public boolean isCrsi() {
		return getPayloadLength() >= 2 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 1), 5);
	}

	public boolean isP() {
		return getPayloadLength() >= 2 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 1), 4);
	}

	public boolean isPt() {
		return getPayloadLength() >= 2 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 1), 3);
	}

	public boolean isSi() {
		return getPayloadLength() >= 2 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 1), 2);
	}

	public boolean isMsv() {
		return getPayloadLength() >= 2 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 1), 1);
	}

	public boolean isRetLoc() {
		return getPayloadLength() >= 3 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 2), 8);
	}

	public boolean isPbic() {
		return getPayloadLength() >= 3 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 2), 7);
	}

	public boolean isSrni() {
		return getPayloadLength() >= 3 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 2), 6);
	}

	public boolean isS6af() {
		return getPayloadLength() >= 3 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 2), 5);
	}

	public boolean isS4af() {
		return getPayloadLength() >= 3 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 2), 4);
	}

	public boolean isMbmdt() {
		return getPayloadLength() >= 3 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 2), 3);
	}

	public boolean isIsrau() {
		return getPayloadLength() >= 3 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 2), 2);
	}

	public boolean isCcrsi() {
		return getPayloadLength() >= 3 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 2), 1);
	}

	public boolean isCprai() {
		return getPayloadLength() >= 4 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 3), 8);
	}

	public boolean isArrl() {
		return getPayloadLength() >= 4 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 3), 7);
	}

	public boolean isPpof() {
		return getPayloadLength() >= 4 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 3), 6);
	}

	public boolean isPponPpei() {
		return getPayloadLength() >= 4 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 3), 5);
	}

	public boolean isPpsi() {
		return getPayloadLength() >= 4 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 3), 4);
	}

	public boolean isCsfbi() {
		return getPayloadLength() >= 4 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 3), 3);
	}

	public boolean isClii() {
		return getPayloadLength() >= 4 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 3), 2);
	}

	public boolean isCpsr() {
		return getPayloadLength() >= 4 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 3), 1);
	}

	public boolean isNsi() {
		return getPayloadLength() >= 5 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 4), 8);
	}

	public boolean isUasi() {
		return getPayloadLength() >= 5 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 4), 7);
	}

	public boolean isDtci() {
		return getPayloadLength() >= 5 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 4), 6);
	}

	public boolean isBdwi() {
		return getPayloadLength() >= 5 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 4), 5);
	}

	public boolean isPsci() {
		return getPayloadLength() >= 5 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 4), 4);
	}

	public boolean isPcri() {
		return getPayloadLength() >= 5 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 4), 3);
	}

	public boolean isAosi() {
		return getPayloadLength() >= 5 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 4), 2);
	}

	public boolean isAopi() {
		return getPayloadLength() >= 5 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 4), 1);
	}

	public boolean isRoaai() {
		return getPayloadLength() >= 6 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 5), 8);
	}

	public boolean isEpcosi() {
		return getPayloadLength() >= 6 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 5), 7);
	}

	public boolean isCpopci() {
		return getPayloadLength() >= 6 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 5), 6);
	}

	public boolean isPmtsmi() {
		return getPayloadLength() >= 6 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 5), 5);
	}

	public boolean isS11tf() {
		return getPayloadLength() >= 6 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 5), 4);
	}

	public boolean isPnsi() {
		return getPayloadLength() >= 6 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 5), 3);
	}

	public boolean isUnaccsi() {
		return getPayloadLength() >= 6 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 5), 2);
	}

	public boolean isWpmsi() {
		return getPayloadLength() >= 6 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 5), 1);
	}

	public boolean is5gsnn26() {
		return getPayloadLength() >= 7 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 6), 8);
	}

	public boolean isReprefi() {
		return getPayloadLength() >= 7 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 6), 7);
	}

	public boolean is5gsiwk() {
		return getPayloadLength() >= 7 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 6), 6);
	}

	public boolean isEevrsi() {
		return getPayloadLength() >= 7 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 6), 5);
	}

	public boolean isLtemui() {
		return getPayloadLength() >= 7 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 6), 4);
	}

	public boolean isLtempi() {
		return getPayloadLength() >= 7 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 6), 3);
	}

	public boolean isEnbcrsi() {
		return getPayloadLength() >= 7 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 6), 2);
	}

	public boolean isTspcmi() {
		return getPayloadLength() >= 7 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 6), 1);
	}

	public boolean isCsrmfi() {
		return getPayloadLength() >= 8 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 7), 8);
	}

	public boolean isMtedtn() {
		return getPayloadLength() >= 8 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 7), 7);
	}

	public boolean isMtedta() {
		return getPayloadLength() >= 8 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 7), 6);
	}

	public boolean isN5gnmi() {
		return getPayloadLength() >= 8 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 7), 5);
	}

	public boolean is5gcnrs() {
		return getPayloadLength() >= 8 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 7), 4);
	}

	public boolean is5gcnri() {
		return getPayloadLength() >= 8 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 7), 3);
	}

	public boolean is5srhoi() {
		return getPayloadLength() >= 8 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 7), 2);
	}

	public boolean isEthpdn() {
		return getPayloadLength() >= 8 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 7), 1);
	}

	public boolean isNspusi() {
		return getPayloadLength() >= 9 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 8), 8);
	}

	public boolean isPgwrnsi() {
		return getPayloadLength() >= 9 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 8), 7);
	}

	public boolean isRppcsi() {
		return getPayloadLength() >= 9 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 8), 6);
	}

	public boolean isPgwchi() {
		return getPayloadLength() >= 9 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 8), 5);
	}

	public boolean isSissme() {
		return getPayloadLength() >= 9 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 8), 4);
	}

	public boolean isNsenbi() {
		return getPayloadLength() >= 9 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 8), 3);
	}

	public boolean isIdfupf() {
		return getPayloadLength() >= 9 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 8), 2);
	}

	public boolean isEmci() {
		return getPayloadLength() >= 9 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 8), 1);
	}

	public boolean isLtemsai() {
		return getPayloadLength() >= IE_HEADER_SIZE + 10 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 9), 3);
	}

	public boolean isSrtpi() {
		return getPayloadLength() >= IE_HEADER_SIZE + 10 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 9), 2);
	}

	public boolean isUpipsi() {
		return getPayloadLength() >= IE_HEADER_SIZE + 10 && isBitSet(getPayloadByte(IE_HEADER_SIZE + 9), 1);
	}

	@Override
	public String toString() {
		final var payloadSb = new StringBuilder();
		for (final var b : getCopyOfBytes(IE_HEADER_SIZE)) {
			payloadSb.append(String.format("0x%s ", Integer.toHexString(b & 0xff)));
		}
		return "Indication{" +
				"type=" + this.getType() +
				", length=" + getLength() +
				", payload=" + payloadSb.toString().trim() +
				'}';
	}
}
