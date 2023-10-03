package com.sipgate.udpproxy.udp.proxy.processors.sip;

import com.sipgate.udpproxy.udp.proxy.processors.Message;

public class SipMessage extends Message {
	private String message;

	public SipMessage(final byte[] bytes) {
		super(bytes);
		this.message = new String(bytes);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	@Override
	public void packBytes() {
		setPayloadBytes(message.getBytes());
	}


}
