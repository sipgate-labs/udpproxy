package com.sipgate.udpproxy.udp.proxy.processors.gtpv2.ie;

import com.sipgate.udpproxy.udp.proxy.processors.Message;
import com.sipgate.udpproxy.udp.proxy.processors.Payload;

public class GenericInformationElement extends InformationElement {
	public GenericInformationElement(final Payload payload, final Message parent, final int windowOffset, final int windowSize) {
		super(payload, parent, windowOffset, windowSize);
	}

	@Override
	public void packBytes() {
		// Stub! Generic implementation has no properties to modify.
	}

	@Override
	public String toString() {
		final var payloadSb = new StringBuilder();
		for (int i = IE_HEADER_SIZE; i < getPayloadLength(); i++) {
			payloadSb.append(String.format("0x%s ", Integer.toHexString(getPayloadByte(i) & 0xff)));
		}
		return "InformationElement{" +
				"type=" + getType() +
				", length=" + getLength() +
				", payload=" + payloadSb.toString().trim() +
				'}';
	}
}
