package com.sipgate.udpproxy.udp.payload.gtpv2.ie;

import com.sipgate.udpproxy.udp.payload.gtpv2.ie.decoder.BitHelper;
import com.sipgate.udpproxy.udp.payload.gtpv2.ie.decoder.IpV4V6;

public class EpsBearerLevelTrafficFlowTemplate extends InformationElement {
	public EpsBearerLevelTrafficFlowTemplate(final byte type, final byte spare, final byte instance, final byte[] payload) {
		super(type, spare, instance, payload);
	}

	public int getTftOperationCode() {
		return BitHelper.toInt(payload[0], 6, 8);
	}

	public EpsBearerLevelTrafficFlowTemplate setTftOperationCode(final int tftOperationCode) {
		payload[0] = BitHelper.setBitTo(payload[0], 6, BitHelper.isBitSet((byte) tftOperationCode, 6));
		payload[0] = BitHelper.setBitTo(payload[0], 7, BitHelper.isBitSet((byte) tftOperationCode, 7));
		payload[0] = BitHelper.setBitTo(payload[0], 8, BitHelper.isBitSet((byte) tftOperationCode, 8));
		return this;
	}

	public TftOperationCode getTftOperationCodeEnum() {
		return TftOperationCode.fromCode(getTftOperationCode());
	}

	public int getNumberOfPacketFilters() {
		return BitHelper.toInt(payload[0], 1, 4);
	}

	public EpsBearerLevelTrafficFlowTemplate setNumberOfPacketFilters(final int numberOfPacketFilters) {
		payload[0] = BitHelper.setBitTo(payload[0], 1, BitHelper.isBitSet((byte) numberOfPacketFilters, 1));
		payload[0] = BitHelper.setBitTo(payload[0], 2, BitHelper.isBitSet((byte) numberOfPacketFilters, 2));
		payload[0] = BitHelper.setBitTo(payload[0], 3, BitHelper.isBitSet((byte) numberOfPacketFilters, 3));
		payload[0] = BitHelper.setBitTo(payload[0], 4, BitHelper.isBitSet((byte) numberOfPacketFilters, 4));
		return this;
	}

	public enum TftOperationCode {
		IGNORE_THIS_IE(0),
		CREATE_NEW_TFT(1),
		DELETE_EXISTING_TFT(2),
		ADD_PACKET_FILTER_TO_EXISTING_TFT(3),
		REPLACE_PACKET_FILTERS_IN_EXISTING_TFT(4),
		DELETE_PACKET_FILTERS_FROM_EXISTING_TFT(5),
		NO_TFT_OPERATION(6),
		RESERVED(7);

		private final int code;

		TftOperationCode(final int code) {
			this.code = code;
		}

		public int getCode() {
			return code;
		}

		public static TftOperationCode fromCode(final int code) {
			for (final var tftOperationCode : values()) {
				if (tftOperationCode.getCode() == code) {
					return tftOperationCode;
				}
			}
			return null;
		}
	}

	public class PacketFilter {
		public static final int FILTER_DIRECTION_DOWNLINK_ONLY = 1;
		public static final int FILTER_DIRECTION_UPLINK_ONLY = 2;
		public static final int FILTER_DIRECTION_BIDIRECTIONAL = 3;

		private final byte[] payload;


		public PacketFilter(final byte[] payload) {
			this.payload = payload;
		}

		public int getPacketFilterIdentifier() {
			return BitHelper.toInt(payload[0], 1, 4) + 1;
		}

		public PacketFilter setPacketFilterIdentifier(int packetFilterIdentifier) {
			packetFilterIdentifier--;
			payload[0] = BitHelper.setBitTo(payload[0], 1, BitHelper.isBitSet((byte) packetFilterIdentifier, 1));
			payload[0] = BitHelper.setBitTo(payload[0], 2, BitHelper.isBitSet((byte) packetFilterIdentifier, 2));
			payload[0] = BitHelper.setBitTo(payload[0], 3, BitHelper.isBitSet((byte) packetFilterIdentifier, 3));
			payload[0] = BitHelper.setBitTo(payload[0], 4, BitHelper.isBitSet((byte) packetFilterIdentifier, 4));
			return this;
		}

		public int getPacketFilterDirection() {
			return BitHelper.toInt(payload[1], 5, 6);
		}

		public PacketFilter setPacketFilterDirection(final int packetFilterDirection) {
			payload[1] = BitHelper.setBitTo(payload[1], 5, BitHelper.isBitSet((byte) packetFilterDirection, 6));
			payload[1] = BitHelper.setBitTo(payload[1], 6, BitHelper.isBitSet((byte) packetFilterDirection, 5));
			return this;
		}

		public int getPacketFilterEvaluationPrecedence() {
			return BitHelper.toInt(payload[1]);
		}

		public PacketFilter setPacketFilterEvaluationPrecedence(final int packetFilterEvaluationPrecedence) {
			payload[1] = (byte) packetFilterEvaluationPrecedence;
			return this;
		}

		public int getPacketFilterLength() {
			return BitHelper.toInt(payload[2]);
		}

		public PacketFilter setPacketFilterLength(final int packetFilterLength) {
			payload[2] = (byte) packetFilterLength;
			return this;
		}

		public PacketFilterComponentType getPacketFilterComponentType() {
			return PacketFilterComponentType.fromCode(BitHelper.toInt(payload[3]));
		}

		public PacketFilter setPacketFilterComponentType(final PacketFilterComponentType packetFilterComponentType) {
			payload[3] = (byte) packetFilterComponentType.getCode();
			// TODO: set length and re-init payload array based on length
			return this;
		}

		public String getIpv4RemoteAddress() {
			if (getPacketFilterComponentType() != PacketFilterComponentType.IPV4_REMOTE_ADDRESS) {
				throw new IllegalStateException("Packet filter component type is not IPV4_REMOTE_ADDRESS");
			}

			final byte[] ipv4Address = new byte[4];
			System.arraycopy(payload, 4, ipv4Address, 0, 4);

			return IpV4V6.decodeV4(ipv4Address);
		}

		public String getIpv4RemoteAddressMask() {
			if (getPacketFilterComponentType() != PacketFilterComponentType.IPV4_REMOTE_ADDRESS) {
				throw new IllegalStateException("Packet filter component type is not IPV4_REMOTE_ADDRESS");
			}

			final byte[] ipv4AddressMask = new byte[4];
			System.arraycopy(payload, 8, ipv4AddressMask, 0, 4);

			return IpV4V6.decodeV4(ipv4AddressMask);
		}

		public PacketFilter setIpv4RemoteAddress(final String ipv4RemoteAddress) {
			if (getPacketFilterComponentType() != PacketFilterComponentType.IPV4_REMOTE_ADDRESS) {
				throw new IllegalStateException("Packet filter component type is not IPV4_REMOTE_ADDRESS");
			}

			final byte[] ipv4Address = IpV4V6.encodeV4(ipv4RemoteAddress);
			System.arraycopy(ipv4Address, 0, payload, 4, 4);

			return this;
		}

		public enum PacketFilterComponentType {
			IPV4_REMOTE_ADDRESS(16),
			IPV4_LOCAL_ADDRESS(17),
			IPV6_REMOTE_ADDRESS(32),
			IPV6_REMOTE_ADDRESS_PREFIX_LENGTH(33),
			IPV6_LOCAL_ADDRESS_PREFIX_LENGTH(35),
			PROTOCOL_IDENTIFIER_NEXT_HEADER(48),
			SINGLE_LOCAL_PORT(64),
			LOCAL_PORT_RANGE(65),
			SINGLE_REMOTE_PORT(80),
			REMOTE_PORT_RANGE(81),
			SECURITY_PARAMETER_INDEX(96),
			TYPE_OF_SERVICE_TRAFFIC_CLASS(112),
			FLOW_LABEL(128),
			DESTINATION_MAC_ADDRESS(129),
			SOURCE_MAC_ADDRESS(130),
			IEE_802_1Q_C_TAG_VID(131),
			IEE_802_1Q_S_TAG_VID(132),
			IEE_802_1Q_C_TAG_PCP(133),
			IEE_802_1Q_S_TAG_PCP(134),
			ETHERTYPE(135),
			;

			private final int code;

			PacketFilterComponentType(final int code) {
				this.code = code;
			}

			public int getCode() {
				return code;
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

}
