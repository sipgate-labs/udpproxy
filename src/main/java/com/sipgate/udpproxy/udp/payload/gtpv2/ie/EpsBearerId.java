package com.sipgate.udpproxy.udp.payload.gtpv2.ie;

import com.sipgate.udpproxy.udp.payload.gtpv2.ie.decoder.BitHelper;

public class EpsBearerId extends InformationElement {
	public EpsBearerId(final byte type, final byte spare, final byte instance, final byte[] payload) {
		super(type, spare, instance, payload);
	}

	public int getEpsBearerId() {
		return BitHelper.toInt(payload[0], 1, 4);
	}

	@Override
	public String toString() {
		return "EpsBearerId{" +
				"epsBearerId=" + getEpsBearerId() +
				'}';
	}
}
