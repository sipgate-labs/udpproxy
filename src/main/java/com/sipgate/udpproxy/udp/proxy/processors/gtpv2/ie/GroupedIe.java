package com.sipgate.udpproxy.udp.proxy.processors.gtpv2.ie;

import com.sipgate.udpproxy.udp.proxy.processors.Message;
import com.sipgate.udpproxy.udp.proxy.processors.Payload;
import com.sipgate.udpproxy.udp.proxy.processors.gtpv2.ie.decoder.BitHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class GroupedIe extends InformationElement {

	protected final List<InformationElement> informationElements = new ArrayList<>();

	protected GroupedIe(final Payload payload, final Message parent, final int windowOffset, final int windowSize) {
		super(payload, parent, windowOffset, windowSize);

		var currentOffset = 0;
		while (currentOffset < getPayloadLength()) {
			final int type = getPayloadByte(0);
			final short length = (short) BitHelper.int16ToInt32(getPayloadByte(1), getPayloadByte(2));
			currentOffset += length + IE_HEADER_SIZE;
			InformationElement.fromPayload(type, payload, this, currentOffset, length + IE_HEADER_SIZE);
		}

	}

	public List<InformationElement> getInformationElements() {
		return Collections.unmodifiableList(informationElements);
	}
}
