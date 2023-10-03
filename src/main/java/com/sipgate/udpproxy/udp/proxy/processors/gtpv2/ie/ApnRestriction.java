package com.sipgate.udpproxy.udp.proxy.processors.gtpv2.ie;

import com.sipgate.udpproxy.udp.proxy.processors.Message;
import com.sipgate.udpproxy.udp.proxy.processors.Payload;

public class ApnRestriction extends GenericInformationElement {

	private final Restriction restriction;

	public ApnRestriction(final Payload payload, final Message parent, final int windowOffset, final int windowSize) {
		super(payload, parent, windowOffset, windowSize);
		this.restriction = Restriction.fromValue(getPayloadByte(IE_HEADER_SIZE));
	}

	public Restriction getRestriction() {
		return restriction;
	}

	@Override
	public String toString() {
		return "ApnRestriction{" +
				"restriction=" + getRestriction() +
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
