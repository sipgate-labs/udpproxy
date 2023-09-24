package com.sipgate.udpproxy.udp.payload.gtpv2.ie;

import static com.sipgate.udpproxy.udp.payload.gtpv2.ie.decoder.BitHelper.isBitSet;

public class Indication extends InformationElement {
	public Indication(final byte type, final byte spare, final byte instance, final byte[] payload) {
		super(type, spare, instance, payload);
	}

	public boolean isDaf() {
		return payload.length >= 1 && isBitSet(payload[0], 8);
	}

	public boolean isDtf() {
		return payload.length >= 1 && isBitSet(payload[0], 7);
	}

	public boolean isHi() {
		return payload.length >= 1 && isBitSet(payload[0], 6);
	}

	public boolean isDfi() {
		return payload.length >= 1 && isBitSet(payload[0], 5);
	}

	public boolean isOi() {
		return payload.length >= 1 && isBitSet(payload[0], 4);
	}

	public boolean isIsrsi() {
		return payload.length >= 1 && isBitSet(payload[0], 3);
	}

	public boolean isIrai() {
		return payload.length >= 1 && isBitSet(payload[0], 2);
	}

	public boolean isSgwci() {
		return payload.length >= 1 && isBitSet(payload[0], 1);
	}

	public boolean isSqci() {
		return payload.length >= 2 && isBitSet(payload[1], 8);
	}

	public boolean isUimsi() {
		return payload.length >= 2 && isBitSet(payload[1], 7);
	}

	public boolean isCfsi() {
		return payload.length >= 2 && isBitSet(payload[1], 6);
	}

	public boolean isCrsi() {
		return payload.length >= 2 && isBitSet(payload[1], 5);
	}

	public boolean isP() {
		return payload.length >= 2 && isBitSet(payload[1], 4);
	}

	public boolean isPt() {
		return payload.length >= 2 && isBitSet(payload[1], 3);
	}

	public boolean isSi() {
		return payload.length >= 2 && isBitSet(payload[1], 2);
	}

	public boolean isMsv() {
		return payload.length >= 2 && isBitSet(payload[1], 1);
	}

	public boolean isRetLoc() {
		return payload.length >= 3 && isBitSet(payload[2], 8);
	}

	public boolean isPbic() {
		return payload.length >= 3 && isBitSet(payload[2], 7);
	}

	public boolean isSrni() {
		return payload.length >= 3 && isBitSet(payload[2], 6);
	}

	public boolean isS6af() {
		return payload.length >= 3 && isBitSet(payload[2], 5);
	}

	public boolean isS4af() {
		return payload.length >= 3 && isBitSet(payload[2], 4);
	}

	public boolean isMbmdt() {
		return payload.length >= 3 && isBitSet(payload[2], 3);
	}

	public boolean isIsrau() {
		return payload.length >= 3 && isBitSet(payload[2], 2);
	}

	public boolean isCcrsi() {
		return payload.length >= 3 && isBitSet(payload[2], 1);
	}

	public boolean isCprai() {
		return payload.length >= 4 && isBitSet(payload[3], 8);
	}

	public boolean isArrl() {
		return payload.length >= 4 && isBitSet(payload[3], 7);
	}

	public boolean isPpof() {
		return payload.length >= 4 && isBitSet(payload[3], 6);
	}

	public boolean isPponPpei() {
		return payload.length >= 4 && isBitSet(payload[3], 5);
	}

	public boolean isPpsi() {
		return payload.length >= 4 && isBitSet(payload[3], 4);
	}

	public boolean isCsfbi() {
		return payload.length >= 4 && isBitSet(payload[3], 3);
	}

	public boolean isClii() {
		return payload.length >= 4 && isBitSet(payload[3], 2);
	}

	public boolean isCpsr() {
		return payload.length >= 4 && isBitSet(payload[3], 1);
	}

	public boolean isNsi() {
		return payload.length >= 5 && isBitSet(payload[4], 8);
	}

	public boolean isUasi() {
		return payload.length >= 5 && isBitSet(payload[4], 7);
	}

	public boolean isDtci() {
		return payload.length >= 5 && isBitSet(payload[4], 6);
	}

	public boolean isBdwi() {
		return payload.length >= 5 && isBitSet(payload[4], 5);
	}

	public boolean isPsci() {
		return payload.length >= 5 && isBitSet(payload[4], 4);
	}

	public boolean isPcri() {
		return payload.length >= 5 && isBitSet(payload[4], 3);
	}

	public boolean isAosi() {
		return payload.length >= 5 && isBitSet(payload[4], 2);
	}

	public boolean isAopi() {
		return payload.length >= 5 && isBitSet(payload[4], 1);
	}

	public boolean isRoaai() {
		return payload.length >= 6 && isBitSet(payload[5], 8);
	}

	public boolean isEpcosi() {
		return payload.length >= 6 && isBitSet(payload[5], 7);
	}

	public boolean isCpopci() {
		return payload.length >= 6 && isBitSet(payload[5], 6);
	}

	public boolean isPmtsmi() {
		return payload.length >= 6 && isBitSet(payload[5], 5);
	}

	public boolean isS11tf() {
		return payload.length >= 6 && isBitSet(payload[5], 4);
	}

	public boolean isPnsi() {
		return payload.length >= 6 && isBitSet(payload[5], 3);
	}

	public boolean isUnaccsi() {
		return payload.length >= 6 && isBitSet(payload[5], 2);
	}

	public boolean isWpmsi() {
		return payload.length >= 6 && isBitSet(payload[5], 1);
	}

	public boolean is5gsnn26() {
		return payload.length >= 7 && isBitSet(payload[6], 8);
	}

	public boolean isReprefi() {
		return payload.length >= 7 && isBitSet(payload[6], 7);
	}

	public boolean is5gsiwk() {
		return payload.length >= 7 && isBitSet(payload[6], 6);
	}

	public boolean isEevrsi() {
		return payload.length >= 7 && isBitSet(payload[6], 5);
	}

	public boolean isLtemui() {
		return payload.length >= 7 && isBitSet(payload[6], 4);
	}

	public boolean isLtempi() {
		return payload.length >= 7 && isBitSet(payload[6], 3);
	}

	public boolean isEnbcrsi() {
		return payload.length >= 7 && isBitSet(payload[6], 2);
	}

	public boolean isTspcmi() {
		return payload.length >= 7 && isBitSet(payload[6], 1);
	}

	public boolean isCsrmfi() {
		return payload.length >= 8 && isBitSet(payload[7], 8);
	}

	public boolean isMtedtn() {
		return payload.length >= 8 && isBitSet(payload[7], 7);
	}

	public boolean isMtedta() {
		return payload.length >= 8 && isBitSet(payload[7], 6);
	}

	public boolean isN5gnmi() {
		return payload.length >= 8 && isBitSet(payload[7], 5);
	}

	public boolean is5gcnrs() {
		return payload.length >= 8 && isBitSet(payload[7], 4);
	}

	public boolean is5gcnri() {
		return payload.length >= 8 && isBitSet(payload[7], 3);
	}

	public boolean is5srhoi() {
		return payload.length >= 8 && isBitSet(payload[7], 2);
	}

	public boolean isEthpdn() {
		return payload.length >= 8 && isBitSet(payload[7], 1);
	}

	public boolean isNspusi() {
		return payload.length >= 9 && isBitSet(payload[8], 8);
	}

	public boolean isPgwrnsi() {
		return payload.length >= 9 && isBitSet(payload[8], 7);
	}

	public boolean isRppcsi() {
		return payload.length >= 9 && isBitSet(payload[8], 6);
	}

	public boolean isPgwchi() {
		return payload.length >= 9 && isBitSet(payload[8], 5);
	}

	public boolean isSissme() {
		return payload.length >= 9 && isBitSet(payload[8], 4);
	}

	public boolean isNsenbi() {
		return payload.length >= 9 && isBitSet(payload[8], 3);
	}

	public boolean isIdfupf() {
		return payload.length >= 9 && isBitSet(payload[8], 2);
	}

	public boolean isEmci() {
		return payload.length >= 9 && isBitSet(payload[8], 1);
	}

	public boolean isLtemsai() {
		return payload.length >= 10 && isBitSet(payload[9], 3);
	}

	public boolean isSrtpi() {
		return payload.length >= 10 && isBitSet(payload[9], 2);
	}

	public boolean isUpipsi() {
		return payload.length >= 10 && isBitSet(payload[9], 1);
	}

	@Override
	public String toString() {
		final var payloadSb = new StringBuilder();
		for (final var b : payload) {
			payloadSb.append(String.format("0x%s ", Integer.toHexString(b & 0xff)));
		}
		return "Indication{" +
				"type=" + this.getType() +
				", length=" + getLength() +
				", spare=" + getSpare() +
				", instance=" + getInstance() +
				", payload=" + payloadSb.toString().trim() +
				'}';
	}
}
