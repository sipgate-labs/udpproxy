package com.sipgate.udpproxy.udp.payload.gtpv2.ie;

public class AccessPointName extends InformationElement {
	public AccessPointName(final byte type, final byte spare, final byte instance, final byte[] payload) {
		super(type, spare, instance, payload);
	}

	public String getApn() {
		// Encoding: TS 123.003, Clause 9.1: Each label is encoded as a length octet followed by that number of octets
		final var apn = new StringBuilder();
		var nextLengthIndex = 0;
		for (int i = 0; i < payload.length; i++) {
			if (i != nextLengthIndex) {
				apn.append((char) payload[i]);
				continue;
			}

			if (i != 0) {
				apn.append(".");
			}
			nextLengthIndex = i + (payload[i] & 0xff) + 1;
		}


		return apn.toString();
	}

	@Override
	public String toString() {
		return "AccessPointName{" +
				"apn='" + getApn() + '\'' +
				'}';
	}
}
