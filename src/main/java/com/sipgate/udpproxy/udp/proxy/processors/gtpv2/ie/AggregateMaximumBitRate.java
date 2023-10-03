package com.sipgate.udpproxy.udp.proxy.processors.gtpv2.ie;

import com.sipgate.udpproxy.udp.proxy.processors.Message;
import com.sipgate.udpproxy.udp.proxy.processors.Payload;
import com.sipgate.udpproxy.udp.proxy.processors.gtpv2.ie.decoder.BitHelper;

public class AggregateMaximumBitRate extends GenericInformationElement {
	private final int upLinkBitRate;
	private final int downLinkBitRate;

	public AggregateMaximumBitRate(final Payload payload, final Message parent, final int windowOffset, final int windowSize) {
		super(payload, parent, windowOffset, windowSize);
		this.upLinkBitRate = BitHelper.toInt32(
				getPayloadByte(IE_HEADER_SIZE),
				getPayloadByte(IE_HEADER_SIZE + 1),
				getPayloadByte(IE_HEADER_SIZE + 2),
				getPayloadByte(IE_HEADER_SIZE + 3)
		);

		this.downLinkBitRate = BitHelper.toInt32(
				getPayloadByte(IE_HEADER_SIZE + 4),
				getPayloadByte(IE_HEADER_SIZE + 5),
				getPayloadByte(IE_HEADER_SIZE + 6),
				getPayloadByte(IE_HEADER_SIZE + 7)
		);
	}

	public int getUpLinkBitRate() {
		return upLinkBitRate;
	}

	public int getDownLinkBitRate() {
		return downLinkBitRate;
	}

	@Override
	public String toString() {
		return "AggregateMaximumBitRate{" +
				"upLinkBitRate=" + getUpLinkBitRate() +
				", downLinkBitRate=" + getDownLinkBitRate() +
				'}';
	}
}
