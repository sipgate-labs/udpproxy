package com.sipgate.udpproxy.protocol.gtpv2.ie;

import com.sipgate.udpproxy.protocol.gtpv2.ie.decoder.Tbcd;

public class Msidn extends InformationElement {

	Msidn(final byte type, final byte spare, final byte instance, final byte[] payload) {
		super(type, spare, instance, payload);
	}

	@Override
	public String toString() {
		return "Msidn{" +
				"msidn=" + getMsidn() +
				'}';
	}

	public String getMsidn() {
		return Tbcd.decode(payload);
	}
}
