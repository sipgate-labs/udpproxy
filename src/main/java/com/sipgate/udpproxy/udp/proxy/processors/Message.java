package com.sipgate.udpproxy.udp.proxy.processors;

public abstract class Message {

	private final Payload payload;

	protected Message(final byte[] bytes) {
		this.payload = new Payload(bytes, this);
	}

	protected Message(final Payload payload, final Message parent, final int windowOffset, final int windowSize) {
		this.payload = payload;
		this.payload.addMessage(this, parent, windowOffset, windowOffset + (windowSize - 1));
	}

	/**
	 * Returns the {@link Payload} bytes of this message as a copy
	 * @return the payload bytes
	 */
	public byte[] finalizeForNetwork() {
		return payload.finalizeForNetwork();
	}

	/**
	 * Packs the bytes of this message and its children into the {@link Payload}.
	 */
	public abstract void packBytes();

	protected byte getPayloadByte(final int index) {
		return payload.getPayloadByte(index, this);
	}

	protected byte[] getCopyOfBytes(final int offset) {
		return payload.finalizeForNetwork(offset, this);
	}

	protected void setPayloadBytes(final byte[] bytes) {
		payload.setPayloadBytes(bytes, this);
	}

	protected int getPayloadLength() {
		return payload.getPayloadLength(this);
	}
}
