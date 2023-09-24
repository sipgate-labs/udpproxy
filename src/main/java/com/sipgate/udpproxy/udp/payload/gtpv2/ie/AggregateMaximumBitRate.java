package com.sipgate.udpproxy.udp.payload.gtpv2.ie;

import com.sipgate.udpproxy.udp.payload.gtpv2.ie.decoder.BitHelper;

public class AggregateMaximumBitRate extends InformationElement	{
	public AggregateMaximumBitRate(final byte type, final byte spare, final byte instance, final byte[] payload) {
		super(type, spare, instance, payload);
	}

	public int getUpLinkBitRate() {
		return BitHelper.toInt32(payload[0], payload[1], payload[2], payload[3]);
	}

	public int getDownLinkBitRate() {
		return BitHelper.toInt32(payload[4], payload[5], payload[6], payload[7]);
	}

	@Override
	public String toString() {
		return "AggregateMaximumBitRate{" +
				"upLinkBitRate=" + getUpLinkBitRate() +
				", downLinkBitRate=" + getDownLinkBitRate() +
				'}';
	}
}
