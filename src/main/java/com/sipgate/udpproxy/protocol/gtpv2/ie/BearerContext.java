package com.sipgate.udpproxy.protocol.gtpv2.ie;

public class BearerContext extends GroupedIe{

	BearerContext(final byte type, final byte spare, final byte instance, final byte[] payload) {
		super(type, spare, instance, payload);
	}

	@Override
	public String toString() {
		return "BearerContext{" +
				"informationElements=" + informationElements +
				'}';
	}
}
