package com.sipgate.udpproxy.udp.payload.gtpv2.ie;

public class ApnRestriction extends InformationElement {
	public ApnRestriction(final byte type, final byte spare, final byte instance, final byte[] payload) {
		super(type, spare, instance, payload);
	}

	public int getRestriction() {
		return payload[0];
	}

	public Restriction getRestrictionValue() {
		return Restriction.fromValue(payload[0]);
	}

	@Override
	public String toString() {
		return "ApnRestriction{" +
				"restriction=" + getRestrictionValue() +
				'}';
	}

	public enum Restriction {
		NO_RESTRICTION(0),
		PUBLIC_1(1),
		PUBLIC_2(2),
		PRIVATE_1(3),
		PRIVATE_2(4);

		private final int value;

		Restriction(final int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public static Restriction fromValue(final int value) {
			for (final var restriction : Restriction.values()) {
				if (restriction.getValue() == value) {
					return restriction;
				}
			}
			return null;
		}
	}
}
