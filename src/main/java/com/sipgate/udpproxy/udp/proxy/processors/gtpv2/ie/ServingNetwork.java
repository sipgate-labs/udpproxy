package com.sipgate.udpproxy.udp.proxy.processors.gtpv2.ie;

import com.sipgate.udpproxy.udp.proxy.processors.Message;
import com.sipgate.udpproxy.udp.proxy.processors.Payload;
import com.sipgate.udpproxy.udp.proxy.processors.gtpv2.ie.decoder.MncMcc;

public class ServingNetwork extends GenericInformationElement {

	private final int mcc;
	private final int mnc;

	public ServingNetwork(final Payload payload, final Message parent, final int windowOffset, final int windowSize) {
		super(payload, parent, windowOffset, windowSize);
		this.mcc = MncMcc.decodeMcc(getPayloadByte(IE_HEADER_SIZE), getPayloadByte(IE_HEADER_SIZE + 1));
		this.mnc = MncMcc.decodeMnc(getPayloadByte(IE_HEADER_SIZE + 1), getPayloadByte(IE_HEADER_SIZE + 2));
	}

	private int getMcc() {
		return mcc;
	}

	private int getMnc() {
		return mnc;
	}

	@Override
	public String toString() {
		return "ServingNetwork{" +
				"mcc=" + getMcc() +
				", mnc=" + getMnc() +
				'}';
	}
}
