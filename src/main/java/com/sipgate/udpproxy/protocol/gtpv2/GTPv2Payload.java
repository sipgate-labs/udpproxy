package com.sipgate.udpproxy.protocol.gtpv2;

import com.sipgate.udpproxy.protocol.gtpv2.ie.InformationElement;

import java.util.List;

public class GTPv2Payload {

	private final byte[] payload;

	// In order of payload bytes
	private int version;
	private boolean piggybackingFlagSet;
	private boolean teidFlagSet;
	private boolean messagePriorityFlagSet;
	private MessageType messageType;
	private int messageLength;
	private int tunnelEndpointIdentifier;
	private int sequenceNumber;
	private byte spare;
	private List<InformationElement> informationElements;


	private GTPv2Payload(byte[] payload) {
		this.payload = payload;

		this.version = (payload[0] & 0b11100000) >>> 5;
		this.piggybackingFlagSet = (payload[0] & 0b00010000) != 0;
		this.teidFlagSet = (payload[0] & 0b00001000) != 0;
		this.messagePriorityFlagSet = (payload[0] & 0b00000100) != 0;
		this.messageType = getMessageType(payload[1]);
		this.messageLength = ((payload[2] & 0xff) << 8) | (payload[3] & 0xff);

		final var offsetSequenceNumber = teidFlagSet ? 8 : 4;
		final var offsetSpare = teidFlagSet ? 11 : 7;

		if (teidFlagSet) {
			this.tunnelEndpointIdentifier = ((payload[4] & 0xff) << 24) | ((payload[5] & 0xff) << 16) | ((payload[6] & 0xff) << 8) | payload[7] & 0xff;
		}
		this.sequenceNumber = ((payload[offsetSequenceNumber] & 0xff) << 16) | ((payload[offsetSequenceNumber + 1] & 0xff) << 8) | payload[offsetSequenceNumber + 2] & 0xff;
		this.spare = payload[offsetSpare];
		this.informationElements = InformationElement.fromBytes(payload, offsetSpare + 1);
	}

	public byte[] toBytes() {
		return payload;
	}

	public static GTPv2Payload fromBytes(final byte[] bytes) {
		return new GTPv2Payload(bytes);
	}

	@Override
	public String toString() {
		return "GTPv2Payload{" +
				"version=" + version +
				", piggybackingFlagSet=" + piggybackingFlagSet +
				", teidFlagSet=" + teidFlagSet +
				", messagePriorityFlagSet=" + messagePriorityFlagSet +
				", messageType=" + messageType +
				", messageLength=" + messageLength +
				", tunnelEndpointIdentifier=" + tunnelEndpointIdentifier +
				", sequenceNumber=" + sequenceNumber +
				", spare=" + spare +
				", informationElements=" + informationElements +
				'}';
	}

	private MessageType getMessageType(final byte b) {
		for (final MessageType type : MessageType.values()) {
			if (type.byteValue.equals(b)) {
				return type;
			}
		}

		return MessageType.UNKNOWN;
	}

	public enum MessageType {

		// Not all messages are implemented - only the once needed for our setup
		ECHO_REQUEST("Echo Request", (byte) 0x01),
		ECHO_RESPONSE("Echo Response", (byte) 0x02),
		CREATE_SESSION_REQUEST("Create Session Request", (byte) 0x20),
		CREATE_SESSION_RESPOSNE("Create Session Response", (byte) 0x21),
		MODIFY_BEARER_REQUEST("Modify Bearer Request", (byte) 0x22),
		MODIFY_BEARER_RESPONSE("Modify Bearer Response", (byte) 0x23),
		DELETE_SESSION_REQUEST("Delete Session Request", (byte) 0x24),
		DELETE_SESSION_RESPONSE("Delete Session Response", (byte) 0x25),
		CREATE_BEARER_REQUEST("Create Bearer Request", (byte) 0x5f),
		CREATE_BEARER_RESPONSE("Create Bearer Response", (byte) 0x60),
		UNKNOWN("Not implemented", (byte) 0x00);


		private final String humanReadable;
		private final Byte byteValue;

		MessageType(final String humanReadable, final Byte byteValue) {
			this.humanReadable = humanReadable;
			this.byteValue = byteValue;
		}

		@Override
		public String toString() {
			return String.format("%s (0x%s)", humanReadable, Integer.toHexString(byteValue & 0xff));
		}
	}

}
