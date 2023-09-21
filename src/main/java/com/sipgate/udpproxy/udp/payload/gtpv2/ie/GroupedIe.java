package com.sipgate.udpproxy.udp.payload.gtpv2.ie;

import java.util.List;

public abstract class GroupedIe extends InformationElement {
	protected List<InformationElement> informationElements;

	GroupedIe(final byte type, final byte spare, final byte instance, final byte[] payload) {
		super(type, spare, instance, payload);
		informationElements = InformationElement.fromBytes(payload, 0);
	}
}
