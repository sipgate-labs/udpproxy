package com.sipgate.udpproxy.udp.proxy.processors.gtpv2.ie;

import com.sipgate.udpproxy.udp.proxy.processors.Message;
import com.sipgate.udpproxy.udp.proxy.processors.Payload;
import com.sipgate.udpproxy.udp.proxy.processors.gtpv2.ie.decoder.BitHelper;

public class EpsBearerId extends GenericInformationElement {

	private final int epsBearerId;

	public EpsBearerId(final Payload payload, final Message parent, final int windowOffset, final int windowSize) {
		super(payload, parent, windowOffset, windowSize);
		epsBearerId = BitHelper.toInt(getPayloadByte(IE_HEADER_SIZE), 1, 4);
	}

	public int getEpsBearerId() {
		return epsBearerId;
	}

	@Override
	public String toString() {
		return "EpsBearerId{" +
				"epsBearerId=" + getEpsBearerId() +
				'}';
	}
}
