package com.sipgate.udpproxy.udp.payload.gtpv2.ie;

import com.sipgate.udpproxy.udp.payload.gtpv2.ie.decoder.BitHelper;

public class PdnType extends InformationElement {
	public PdnType(final byte type, final byte spare, final byte instance, final byte[] payload) {
		super(type, spare, instance, payload);
	}

	public int getPdnType() {
		return BitHelper.toInt(payload[0], 1, 3);
	}

	public Type getPdnTypeValue() {
		return Type.fromValue(BitHelper.toInt(payload[0], 1, 3));
	}

	@Override
	public String toString() {
		return "PdnType{" +
				"pdnType=" + getPdnTypeValue() +
				'}';
	}

	public enum Type {
		IP_V4(1),
		IP_V6(2),
		IP_V4_V6(3),
		NON_IP(4),
		ETHERNET(5);

		private final int value;

		Type(final int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public static Type fromValue(final int value) {
			for (final var type : Type.values()) {
				if (type.getValue() == value) {
					return type;
				}
			}
			return null;
		}
	}
}
