package com.sipgate.udpproxy.udp.proxy.processors.gtpv2.ie;

import com.sipgate.udpproxy.udp.proxy.processors.Message;
import com.sipgate.udpproxy.udp.proxy.processors.Payload;
import com.sipgate.udpproxy.udp.proxy.processors.gtpv2.ie.decoder.Apn;

public class AccessPointName extends InformationElement {

	private String apn;

	public AccessPointName(final Payload payload, final Message parent, final int windowOffset, final int windowSize) {
		super(payload, parent, windowOffset, windowSize);


		this.apn = Apn.decode(getCopyOfBytes(IE_HEADER_SIZE));
	}

	public String getApn() {
		return apn;
	}

	public AccessPointName setApn(final String apn) {
		this.apn = apn;
		return this;
	}

	@Override
	public void packBytes() {
		final byte[] apnBytes = Apn.encode(apn);
		setLength((short) apnBytes.length);
		final byte[] payload = packHeaderWithEmptyPayload(apnBytes.length);
		System.arraycopy(apnBytes, 0, payload, IE_HEADER_SIZE, apnBytes.length - IE_HEADER_SIZE);
		setPayloadBytes(payload);
	}

	@Override
	public String toString() {
		return "AccessPointName{" +
				"apn='" + getApn() + '\'' +
				'}';
	}
}
