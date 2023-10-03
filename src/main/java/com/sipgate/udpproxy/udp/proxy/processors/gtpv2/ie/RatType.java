package com.sipgate.udpproxy.udp.proxy.processors.gtpv2.ie;

import com.sipgate.udpproxy.udp.proxy.processors.gtpv2.ie.decoder.BitHelper;

public class RatType extends InformationElement {

	public RatType(final byte type, final byte spare, final byte instance, final byte[] payload) {
		super(type, spare, instance, payload);
	}

	@Override
	public String toString() {
		return "RatType{" +
				"ratType=" + getRatTypeValue() +
				'}';
	}

	public int getRatType() {
		return BitHelper.toInt(payload[0]);
	}

	public RatTypeValue getRatTypeValue() {
		return RatTypeValue.fromValue(getRatType());
	}

	public enum RatTypeValue {
		UTRAN(1),
		GERAN(2),
		WLAN(3),
		GAN(4),
		HSPA_EVOLUTION(5),
		EUTRAN(6),
		VIRTUAL(7),
		EUTRAN_NB_IOT(8),
		LTE_M(9),
		NR(10),
		WB_E_UTRAN_LEO(11),
		WB_E_UTRAN_MEO(12),
		WB_E_UTRAN_GEO(13),
		WB_E_UTRAN_OTHERSAT(14),
		EUTRAN_NB_IOT_LEO(15),
		EUTRAN_NB_IOT_MEO(16),
		EUTRAN_NB_IOT_GEO(17),
		EUTRAN_NB_IOT_OTHERSAT(18),
		LTE_M_LEO(19),
		LTE_M_MEO(20),
		LTE_M_GEO(21),
		LTE_M_OTHERSAT(22),
		// 23 - 255: Spare
		;

		private final int value;

		RatTypeValue(final int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public static RatTypeValue fromValue(final int value) {
			for (final RatTypeValue ratTypeValue : RatTypeValue.values()) {
				if (ratTypeValue.value == value) {
					return ratTypeValue;
				}
			}
			return null;
		}
	}
}
