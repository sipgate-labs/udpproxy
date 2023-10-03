package com.sipgate.udpproxy.udp.proxy.processors.gtpv2.ie;

import com.sipgate.udpproxy.udp.proxy.processors.Message;
import com.sipgate.udpproxy.udp.proxy.processors.Payload;
import com.sipgate.udpproxy.udp.proxy.processors.gtpv2.ie.decoder.BitHelper;

public class RestartCounter extends GenericInformationElement {

	private final int restartCounter;

	public RestartCounter(final Payload payload, final Message parent, final int windowOffset, final int windowSize) {
		super(payload, parent, windowOffset, windowSize);
		this.restartCounter = BitHelper.toInt(getPayloadByte(0));
	}

	@Override
	public String toString() {
		return "RestartCounter{" +
				"counter=" + getRestartCounter() +
				'}';
	}

	public int getRestartCounter() {
		return restartCounter;
	}
}
