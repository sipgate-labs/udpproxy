package com.sipgate.udpproxy.udp.payload.gtpv2.ie;

import java.util.List;

public abstract class GroupedIe extends InformationElement {

	GroupedIe(final byte type, final byte spare, final byte instance, final byte[] payload) {
		super(type, spare, instance, payload);
	}

	public List<InformationElement> getInformationElements() {
		return InformationElement.fromBytes(payload, 0);
	}
}
