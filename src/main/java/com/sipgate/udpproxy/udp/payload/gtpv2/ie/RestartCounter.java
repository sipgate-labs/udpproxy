package com.sipgate.udpproxy.udp.payload.gtpv2.ie;

import com.sipgate.udpproxy.udp.payload.gtpv2.ie.decoder.BitHelper;

public class RestartCounter extends InformationElement {

	RestartCounter(final byte type, final byte spare, final byte instance, final byte[] payload) {
		super(type, spare, instance, payload);
	}

	@Override
	public String toString() {
		return "RestartCounter{" +
				"counter=" + getRestartCounter() +
				'}';
	}

	public int getRestartCounter() {
		return BitHelper.toInt(payload[0]);
	}
}
