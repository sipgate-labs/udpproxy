package com.sipgate.udpproxy.udp.proxy.processors.gtpv2.ie;

import com.sipgate.udpproxy.udp.proxy.processors.Message;
import com.sipgate.udpproxy.udp.proxy.processors.Payload;
import com.sipgate.udpproxy.udp.proxy.processors.gtpv2.ie.decoder.Tbcd;

public class MobileEquipmentIdentity extends GenericInformationElement	{

	private final String mei;

	public MobileEquipmentIdentity(final Payload payload, final Message parent, final int windowOffset, final int windowSize) {
		super(payload, parent, windowOffset, windowSize);
		this.mei = Tbcd.decode(getCopyOfBytes(IE_HEADER_SIZE));
	}

	@Override
	public String toString() {
		return "MobileEquipmentIdentity{" +
				"mei=" + getMei() +
				'}';
	}

	public String getMei() {
		return mei;
	}

}
