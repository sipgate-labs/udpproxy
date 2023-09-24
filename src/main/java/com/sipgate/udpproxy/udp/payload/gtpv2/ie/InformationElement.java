package com.sipgate.udpproxy.udp.payload.gtpv2.ie;

import java.util.LinkedList;
import java.util.List;

public class InformationElement {

	private byte type;
	private short length;
	private byte spare;
	private byte instance;
	protected byte[] payload;

	protected InformationElement(final byte type, final byte spare, final byte instance, final byte[] payload) {
		this.type = type;
		this.length = (short) payload.length;
		this.spare = spare;
		this.instance = instance;
		this.payload = payload;
	}

	public byte getType() {
		return type;
	}

	public short getLength() {
		return length;
	}

	public byte getSpare() {
		return spare;
	}

	public byte getInstance() {
		return instance;
	}

	public String toString() {
		final var payloadSb = new StringBuilder();
		for (final var b : payload) {
			payloadSb.append(String.format("0x%s ", Integer.toHexString(b & 0xff)));
		}
		return "InformationElement{" +
				"type=" + type +
				", length=" + length +
				", spare=" + spare +
				", instance=" + instance +
				", payload=" + payloadSb.toString().trim() +
				'}';
	}

	public static InformationElement fromBytes(final byte[] rawBytes) {
		final var length = ((rawBytes[1] & 0xff) << 8) | (rawBytes[2] & 0xff);
		if (rawBytes.length != length + 4) {
			throw new IllegalArgumentException("Length field of IE is invalid! Indicated length: " + length + ", actual length: " + (rawBytes.length - 4));
		}
		final var payload = new byte[length];
		System.arraycopy(rawBytes, 4, payload, 0, length);
		final InformationElement ieByType = createByType(rawBytes[0], (byte) (rawBytes[3] & 0b11110000), (byte) (rawBytes[3] & 0b00001111), payload);
		return ieByType;
	}

	public static List<InformationElement> fromBytes(final byte[] rawBytes, final int offset) {
		final var informationElements = new LinkedList<InformationElement>();

		var currentOffset = offset;
		while (currentOffset < rawBytes.length) {
			final var length = ((rawBytes[currentOffset + 1] & 0xff) << 8) | (rawBytes[currentOffset + 2] & 0xff);
			final var ieBytes = new byte[length + 4];
			System.arraycopy(rawBytes, currentOffset, ieBytes, 0, length + 4);
			currentOffset += length + 4;
			informationElements.add(InformationElement.fromBytes(ieBytes));
		}


		return informationElements;
	}

	private static InformationElement createByType(final byte type, final byte spare, final byte instance, final byte[] payload) {
		switch (type) {
			case 0x01:
				return new Imsi(type, spare, instance, payload);
			case 0x02:
				return new Cause(type, spare, instance, payload);
			case 0x03:
				return new RestartCounter(type, spare, instance, payload);
			case 0x47:
				return new AccessPointName(type, spare, instance, payload);
			case 0x48:
				return new AggregateMaximumBitRate(type, spare, instance, payload);
			case 0x49:
				return new EpsBearerId(type, spare, instance, payload);
			case 0x4b:
				return new MobileEquipmentIdentity(type, spare, instance, payload);
			case 0x4c:
				return new Msidn(type, spare, instance, payload);
			case 0x4d:
				return new Indication(type, spare, instance, payload);
			case 0x4f:
				return new PdnAddressAllocation(type, spare, instance, payload);
			case 0x52:
				return new RatType(type, spare, instance, payload);
			case 0x53:
				return new ServingNetwork(type, spare, instance, payload);
			case 0x56:
				return new UserLocationInformation(type, spare, instance, payload);
			case 0x57:
				return new FullyQualifiedTunnelEndpointIdentifier(type, spare, instance, payload);
			case 0x5d:
				return new BearerContext(type, spare, instance, payload);
			case 0x63:
				return new PdnType(type, spare, instance, payload);
			case 0x7f:
				return new ApnRestriction(type, spare, instance, payload);
			case (byte) 0x80:
				return new SelectionMode(type, spare, instance, payload);
			default:
				return new InformationElement(type, spare, instance, payload);
		}
	}


}
