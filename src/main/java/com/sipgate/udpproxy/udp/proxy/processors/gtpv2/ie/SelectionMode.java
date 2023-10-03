package com.sipgate.udpproxy.udp.proxy.processors.gtpv2.ie;

import com.sipgate.udpproxy.udp.proxy.processors.gtpv2.ie.decoder.BitHelper;

public class SelectionMode extends InformationElement {
	public SelectionMode(final byte type, final byte spare, final byte instance, final byte[] payload) {
		super(type, spare, instance, payload);
	}

	public int getMode() {
		return BitHelper.toInt(payload[0], 1, 2);
	}

	public Mode getModeValue() {
		return Mode.fromValue(BitHelper.toInt(payload[0], 1, 2));
	}

	@Override
	public String toString() {
		return "SelectionMode{" +
				"mode=" + getModeValue() +
				'}';
	}

	public enum Mode {
		MS_OR_NETWORK_PROVIDED_APN_SUBSCRIPTION_VERIFIED(0),
		MS_PROVIDED_APN_SUBSCRIPTION_NOT_VERIFIED(1),
		NETWORK_PROVIDED_APN_SUBSCRIPTION_NOT_VERIFIED(2),
		RESERVED(3),
		;

		private final int value;

		Mode(final int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public static Mode fromValue(final int value) {
			for (final var mode : Mode.values()) {
				if (mode.getValue() == value) {
					return mode;
				}
			}
			return null;
		}
	}
}
