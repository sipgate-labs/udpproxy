package com.sipgate.udpproxy.udp.payload.gtpv2.ie;

import com.sipgate.udpproxy.udp.payload.gtpv2.ie.decoder.BitHelper;
import com.sipgate.udpproxy.udp.payload.gtpv2.ie.tft.GenericPacketFilterComponent;

import java.util.ArrayList;
import java.util.List;

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

	public List<PacketFilter> getPacketFilters() {
		final var packetFilters = new ArrayList<PacketFilter>();
		int offset = 1;
		for (int i = 0; i < getNumberOfPacketFilters(); i++) {
			final byte[] packetFilterRaw = new byte[payload[offset + 2] + 3];
			System.arraycopy(payload, offset, packetFilterRaw, 0, packetFilterRaw.length);
			final var packetFilter = new PacketFilter(packetFilterRaw);
			packetFilters.add(packetFilter);
			offset += packetFilter.getPacketFilterLength() + 3;
		}
		return packetFilters;
	}

	@Override
	public String toString() {
		return "EpsBearerLevelTrafficFlowTemplate{" +
				"tftOperationCode=" + getTftOperationCodeEnum() +
				", numberOfPacketFilters=" + getNumberOfPacketFilters() +
				", packetFilters=" + getPacketFilters() +
				'}';
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

		public List<GenericPacketFilterComponent> getPacketFilterComponents() {
			final var components = new ArrayList<GenericPacketFilterComponent>();
			int offset = 3;
			while (offset < payload.length) {
				final var componentType = GenericPacketFilterComponent.PacketFilterComponentType.fromCode(payload[offset]);
				if (componentType == null) {
					throw new IllegalArgumentException("Unknown packet filter component type: " + payload[offset]);
				}
				final var componentLength = componentType.getValueLength() + 1;
				final var componentPayload = new byte[componentLength];
				try {
					System.arraycopy(payload, offset, componentPayload, 0, componentLength);
				} catch (final ArrayIndexOutOfBoundsException e) {
					throw new IllegalArgumentException("Packet filter component length is invalid! Indicated length: " + componentLength + ", actual length: " + (payload.length - offset));
				}
				System.arraycopy(payload, offset, componentPayload, 0, componentLength);
				final var component = GenericPacketFilterComponent.fromBytes(componentPayload);
				components.add(component);
				offset += componentLength;
			}

			return components;
		}

		@Override
		public String toString() {
			return "PacketFilter{" +
					"packetFilterIdentifier=" + getPacketFilterIdentifier() +
					", packetFilterDirection=" + getPacketFilterDirection() +
					", packetFilterEvaluationPrecedence=" + getPacketFilterEvaluationPrecedence() +
					", packetFilterLength=" + getPacketFilterLength() +
					", packetFilterComponents=" + getPacketFilterComponents() +
					'}';
		}

	}

}
