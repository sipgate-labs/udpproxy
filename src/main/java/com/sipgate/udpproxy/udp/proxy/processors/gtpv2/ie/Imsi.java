package com.sipgate.udpproxy.udp.proxy.processors.gtpv2.ie;

import com.sipgate.udpproxy.udp.proxy.processors.Message;
import com.sipgate.udpproxy.udp.proxy.processors.Payload;
import com.sipgate.udpproxy.udp.proxy.processors.gtpv2.ie.decoder.Tbcd;

public class Imsi extends GenericInformationElement {

	private final String imsi;

	public Imsi(final Payload payload, final Message parent, final int windowOffset, final int windowSize) {
		super(payload, parent, windowOffset, windowSize);
		this.imsi = Tbcd.decode(getCopyOfBytes(IE_HEADER_SIZE));
	}

	@Override
	public String toString() {
		return "Imsi{" +
				"imsi=" + getImsi() +
				'}';
	}

	public String getImsi() {
		return imsi;
	}
}
