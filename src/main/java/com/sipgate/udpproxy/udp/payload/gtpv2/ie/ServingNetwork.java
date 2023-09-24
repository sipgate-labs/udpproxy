package com.sipgate.udpproxy.udp.payload.gtpv2.ie;

import com.sipgate.udpproxy.udp.payload.gtpv2.ie.decoder.MncMcc;

public class ServingNetwork extends InformationElement {

	public ServingNetwork(final byte type, final byte spare, final byte instance, final byte[] payload) {
		super(type, spare, instance, payload);
	}

	private final int getMcc() {
		return MncMcc.decodeMcc(payload[0], payload[1]);
	}

	private final int getMnc() {
		return MncMcc.decodeMnc(payload[1], payload[2]);
	}

	@Override
	public String toString() {
		return "ServingNetwork{" +
				"mcc=" + getMcc() +
				", mnc=" + getMnc() +
				'}';
	}
}
