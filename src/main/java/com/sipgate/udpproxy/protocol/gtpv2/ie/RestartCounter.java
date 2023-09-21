package com.sipgate.udpproxy.protocol.gtpv2.ie;

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
		return (payload[0] & 0xff);
	}
}
