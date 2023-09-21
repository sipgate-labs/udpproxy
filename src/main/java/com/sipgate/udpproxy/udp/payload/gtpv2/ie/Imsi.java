package com.sipgate.udpproxy.udp.payload.gtpv2.ie;

import com.sipgate.udpproxy.udp.payload.gtpv2.ie.decoder.Tbcd;

public class Imsi extends InformationElement {

	Imsi(final byte type, final byte spare, final byte instance, final byte[] payload) {
		super(type, spare, instance, payload);
	}

	@Override
	public String toString() {
		return "Imsi{" +
				"imsi=" + getImsi() +
				'}';
	}

	public String getImsi() {
		return Tbcd.decode(payload);
	}
}
