package com.sipgate.udpproxy.protocol.gtpv2.ie;

import com.sipgate.udpproxy.protocol.gtpv2.ie.decoder.Tbcd;

public class MobileEquipmentIdentity extends InformationElement	{

	MobileEquipmentIdentity(final byte type, final byte spare, final byte instance, final byte[] payload) {
		super(type, spare, instance, payload);
	}

	@Override
	public String toString() {
		return "MobileEquipmentIdentity{" +
				"mei=" + getImei() +
				'}';
	}

	public String getImei() {
		return Tbcd.decode(payload);
	}

}
