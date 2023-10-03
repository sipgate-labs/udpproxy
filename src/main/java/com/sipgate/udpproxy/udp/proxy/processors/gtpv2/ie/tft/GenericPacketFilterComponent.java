package com.sipgate.udpproxy.udp.proxy.processors.gtpv2.ie.tft;

import com.sipgate.udpproxy.udp.proxy.processors.Message;

public class GenericPacketFilterComponent extends Message {

	public GenericPacketFilterComponent(final byte[] payload) {
		this.payload = payload;
	}

	public static GenericPacketFilterComponent fromBytes(final byte[] rawBytes) {
		final var packetFilterComponentType = PacketFilterComponentType.fromCode(rawBytes[0]);
		if (packetFilterComponentType == null) {
			throw new IllegalArgumentException("Unknown packet filter component type: " + rawBytes[0]);
		}

		switch (packetFilterComponentType) {
			case IPV4_REMOTE_ADDRESS, IPV4_LOCAL_ADDRESS:
				return new IPv4AddressComponent(rawBytes);
			case IPV6_REMOTE_ADDRESS:
				return new IPv6AddressComponent(rawBytes);
			case IPV6_REMOTE_ADDRESS_PREFIX_LENGTH, IPV6_LOCAL_ADDRESS_PREFIX_LENGTH:
				return new IPv6AddressPrefixComponent(rawBytes);
			default:
				return new GenericPacketFilterComponent(rawBytes);
		}
	}

	public PacketFilterComponentType getPacketFilterComponentType() {
		return PacketFilterComponentType.fromCode(payload[0]);
	}

	public GenericPacketFilterComponent setPacketFilterComponentType(final PacketFilterComponentType type) {
		this.payload = new byte[type.length + 1];
		this.payload[0] = (byte) type.code;
		return this;
	}

	public GenericPacketFilterComponent setPacketFilterBytes(final byte[] bytes) {
		if (bytes.length != getPacketFilterComponentType().length) {
			throw new IllegalArgumentException("Invalid length for packet filter component type " + getPacketFilterComponentType() + ": " + bytes.length);
		}
		System.arraycopy(bytes, 0, payload, 1, bytes.length);
		return this;
	}

	@Override
	public byte[] packBytes() {
		return payload;
	}

	@Override
	public String toString() {
		return "GenericPacketFilterComponent{" +
				"packetFilterComponentType=" + getPacketFilterComponentType() +
				'}';
	}

	public enum PacketFilterComponentType {
		IPV4_REMOTE_ADDRESS(16, 8),
		IPV4_LOCAL_ADDRESS(17, 8),
		IPV6_REMOTE_ADDRESS(32, 32),
		IPV6_REMOTE_ADDRESS_PREFIX_LENGTH(33, 17),
		IPV6_LOCAL_ADDRESS_PREFIX_LENGTH(35, 17),
		PROTOCOL_IDENTIFIER_NEXT_HEADER(48, 1),
		SINGLE_LOCAL_PORT(64, 2),
		LOCAL_PORT_RANGE(65, 4),
		SINGLE_REMOTE_PORT(80, 2),
		REMOTE_PORT_RANGE(81, 4),
		SECURITY_PARAMETER_INDEX(96, 4),
		TYPE_OF_SERVICE_TRAFFIC_CLASS(112, 2),
		FLOW_LABEL(128, 3),
		DESTINATION_MAC_ADDRESS(129, 6),
		SOURCE_MAC_ADDRESS(130, 6),
		IEE_802_1Q_C_TAG_VID(131, 2),
		IEE_802_1Q_S_TAG_VID(132, 2),
		IEE_802_1Q_C_TAG_PCP(133, 1),
		IEE_802_1Q_S_TAG_PCP(134, 1),
		ETHERTYPE(135, 2),
		;

		private final int code;
		private final int length;

		PacketFilterComponentType(final int code, final int length) {
			this.code = code;
			this.length = length;
		}

		public int getCode() {
			return code;
		}

		public int getValueLength() {
			return length;
		}

		public static PacketFilterComponentType fromCode(final int code) {
			for (final var packetFilterComponentType : values()) {
				if (packetFilterComponentType.getCode() == code) {
					return packetFilterComponentType;
				}
			}
			return null;
		}
	}
}
