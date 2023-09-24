package com.sipgate.udpproxy.udp.payload.gtpv2.ie;

import com.sipgate.udpproxy.udp.payload.gtpv2.ie.decoder.BitHelper;
import com.sipgate.udpproxy.udp.payload.gtpv2.ie.decoder.IpV4V6;

import static com.sipgate.udpproxy.udp.payload.gtpv2.ie.decoder.BitHelper.isBitSet;

public class FullyQualifiedTunnelEndpointIdentifier extends InformationElement {

	public FullyQualifiedTunnelEndpointIdentifier(final byte type, final byte spare, final byte instance, final byte[] payload) {
		super(type, spare, instance, payload);
	}

	public boolean isIpv4Present() {
		return payload.length >= 1 && isBitSet(payload[0], 8);
	}

	public boolean isIpv6Present() {
		return payload.length >= 1 && isBitSet(payload[0], 7);
	}

	public int getInterfaceType() {
		return BitHelper.toInt(payload[0], 1, 6);
	}

	public InterfaceType getInterfaceTypeValue() {
		return InterfaceType.fromValue(getInterfaceType());
	}

	public int getTeidGreKey() {
		return BitHelper.toInt32(payload[1], payload[2], payload[3], payload[4]);
	}

	public String getIpv4Address() {
		if (!isIpv4Present()) {
			return null;
		}

		final byte[] ipv4 = new byte[4];
		System.arraycopy(this.payload, 5, ipv4, 0, 4);
		return IpV4V6.decodeV4(ipv4);
	}

	public String getIpv6Address() {
		if (!isIpv6Present()) {
			return null;
		}

		final byte[] ipv6 = new byte[16];
		System.arraycopy(this.payload, 5, ipv6, 0, 16);
		return IpV4V6.decodeV6(ipv6);
	}

	@Override
	public String toString() {
		return "FullyQualifiedTunnelEndpointIdentifier{" +
				"ipv4Present=" + isIpv4Present() +
				", ipv6Present=" + isIpv6Present() +
				", interfaceType=" + getInterfaceTypeValue() +
				", teidGreKey=" + getTeidGreKey() +
				", ipv4Address='" + getIpv4Address() + '\'' +
				", ipv6Address='" + getIpv6Address() + '\'' +
				'}';
	}

	public enum InterfaceType {
		S1_U_ENODEB_GTP_U(0),
		S1_U_SGW_GTP_U(1),
		S12_RNC_GTP_U(2),
		S12_SGW_GTP_U(3),
		S5_S8_SGW_GTP_U(4),
		S5_S8_PGW_GTP_U(5),
		S5_S8_SGW_GTP_C(6),
		S5_S8_PGW_GTP_C(7),
		S5_S8_SGW_PMIPV6(8),
		S5_S8_PGW_PMIPV6_ALT(9),
		S11_MME_GTP_C(10),
		S11_S4_SGW_GTP_C(11),
		S10_N26_MME_GTP_C(12),
		S3_MME_GTP_C(13),
		S3_SGSN_GTP_C(14),
		S4_SGSN_GTP_U(15),
		S4_SGW_GTP_U(16),
		S4_SGSN_GTP_C(17),
		S16_SGSN_GTP_C(18),
		ENODEB_GNODEB_GTP_U_FOR_DL_FORWARDING(19),
		ENODEB_GNODEB_GTP_U_FOR_UL_FORWARDING(20),
		RNC_GTP_U_FOR_DATA_FORWARDING(21),
		SGSN_GTP_U_FOR_DATA_FORWARDING(22),
		SGW_UPF_GTP_U_FOR_DL_FORWARDING(23),
		SM_MBMS_GW_GTP_C(24),
		SN_MBMS_GW_GTP_C(25),
		SM_MME_GTP_C(26),
		SN_SGSN_GTP_C(27),
		SGW_GTPU_FOR_UL_FORWARDING(28),
		SN_SGSN_GTP_U(29),
		S2B_EPDG_GTP_C(30),
		S2B_U_EPDG_GTP_U(31),
		S2B_PGW_GTP_C(32),
		S2B_U_PGW_GTP_U(33),
		S2A_TWAN_GTP_U(34),
		S2A_TWAN_GTP_C(35),
		S2A_PGW_GTP_C(36),
		S2A_PGW_GTP_U(37),
		S11_MME_GTP_U(38),
		S11_SGW_GTP_U(39),
		N26_AMF_GTP_C(40),
		N19MB_UPF_GTP_U(41),
		;

		private final int value;

		InterfaceType(final int value) {
			this.value = value;
		}

		public static InterfaceType fromValue(final int value) {
			for (final InterfaceType interfaceType : InterfaceType.values()) {
				if (interfaceType.value == value) {
					return interfaceType;
				}
			}
			return null;
		}

		public int getValue() {
			return value;
		}
	}
}
