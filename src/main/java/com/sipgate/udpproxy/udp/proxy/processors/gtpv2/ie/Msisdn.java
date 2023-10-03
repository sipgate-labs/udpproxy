package com.sipgate.udpproxy.udp.proxy.processors.gtpv2.ie;

import com.sipgate.udpproxy.udp.proxy.processors.Message;
import com.sipgate.udpproxy.udp.proxy.processors.Payload;
import com.sipgate.udpproxy.udp.proxy.processors.gtpv2.ie.decoder.Tbcd;

public class Msisdn extends GenericInformationElement {
	private final String msidn;

	public Msisdn(final Payload payload, final Message parent, final int windowOffset, final int windowSize) {
		super(payload, parent, windowOffset, windowSize);
		this.msidn = Tbcd.decode(getCopyOfBytes(IE_HEADER_SIZE));
	}

	@Override
	public String toString() {
		return "Msidn{" +
				"msidn=" + getMsidn() +
				'}';
	}

	public String getMsidn() {
		return msidn;
	}
}
