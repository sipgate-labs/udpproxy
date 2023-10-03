package com.sipgate.udpproxy.udp.proxy.processors.gtpv2.ie;

import com.sipgate.udpproxy.udp.proxy.processors.Message;
import com.sipgate.udpproxy.udp.proxy.processors.Payload;

public class BearerContext extends GroupedIe {

	public BearerContext(final Payload payload, final Message parent, final int windowOffset, final int windowSize) {
		super(payload, parent, windowOffset, windowSize);
	}

	@Override
	public String toString() {
		return "BearerContext{" +
				"informationElements=" + getInformationElements() +
				'}';
	}

	@Override
	public void packBytes() {
		informationElements.forEach(InformationElement::packBytes);
	}
}
