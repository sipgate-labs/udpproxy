package com.sipgate.udpproxy.udp.proxy.processors.gtpv2.ie;

import com.sipgate.udpproxy.udp.payload.PackableToBytes;
import com.sipgate.udpproxy.udp.proxy.processors.gtpv2.ie.decoder.BitHelper;
import com.sipgate.udpproxy.udp.proxy.processors.gtpv2.ie.tft.GenericPacketFilterComponent;

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
		payload[0] = BitHelper.setThreeBitInt(tftOperationCode, 6, 8, payload[0]);
		return this;
	}

	public TftOperationCode getTftOperationCodeEnum() {
		return TftOperationCode.fromCode(getTftOperationCode());
	}

	public int getNumberOfPacketFilters() {
		return BitHelper.toInt(payload[0], 1, 4);
	}

	public EpsBearerLevelTrafficFlowTemplate setNumberOfPacketFilters(final int numberOfPacketFilters) {
		payload[0] = BitHelper.setLowerNibble(numberOfPacketFilters, payload[0]);
		return this;
	}

	public boolean isExtensionBitSet() {
		return BitHelper.isBitSet(payload[0], 5);
	}

	public EpsBearerLevelTrafficFlowTemplate setExtensionBit(final boolean extensionBit) {
		payload[0] = BitHelper.setBitTo(payload[0], 5, extensionBit);
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

	public EpsBearerLevelTrafficFlowTemplate setPacketFilters(final List<PacketFilter> packetFilters) {
		final var packetFilterBytes = new ArrayList<byte[]>();
		for (final var packetFilter : packetFilters) {
			packetFilterBytes.add(packetFilter.packBytes());
		}
		final int newLength = packetFilterBytes.stream().mapToInt(b -> b.length).sum();
		final var newPayload = new byte[1 + newLength];
		newPayload[0] = payload[0]; // TFT operation code, E-Bit and number of packet filters

		int offset = 1;
		for (final var packetFilter : packetFilterBytes) {
			System.arraycopy(packetFilter, 0, newPayload, offset, packetFilter.length);
			offset += packetFilter.length;
		}

		this.payload = newPayload;
		return setNumberOfPacketFilters(packetFilters.size()); // Update num of packet filters
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

	public static class PacketFilter implements PackableToBytes {
		public static final int FILTER_DIRECTION_DOWNLINK_ONLY = 1;
		public static final int FILTER_DIRECTION_UPLINK_ONLY = 2;
		public static final int FILTER_DIRECTION_BIDIRECTIONAL = 3;

		private byte[] payload;


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
				System.arraycopy(payload, offset, componentPayload, 0, componentLength);

				System.arraycopy(payload, offset, componentPayload, 0, componentLength);
				final var component = GenericPacketFilterComponent.fromBytes(componentPayload);
				components.add(component);
				offset += componentLength;
			}

			return components;
		}

		public PacketFilter setPacketFilterComponents(List<GenericPacketFilterComponent> components) {
			final var componentBytes = new ArrayList<byte[]>();
			for (final var component : components) {
				componentBytes.add(component.packBytes());
			}
			final int newLength = componentBytes.stream().mapToInt(b -> b.length).sum();
			final var newPayload = new byte[3 + newLength];
			newPayload[0] = payload[0];
			newPayload[1] = payload[1];
			newPayload[2] = (byte) newLength;

			int offset = 3;
			for (final var component : componentBytes) {
				System.arraycopy(component, 0, newPayload, offset, component.length);
				offset += component.length;
			}

			this.payload = newPayload;
			return this;
		}

		@Override
		public byte[] packBytes() {
			return this.payload;
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
