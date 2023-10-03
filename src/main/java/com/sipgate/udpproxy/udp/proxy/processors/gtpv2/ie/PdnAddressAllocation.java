package com.sipgate.udpproxy.udp.proxy.processors.gtpv2.ie;

import com.sipgate.udpproxy.udp.proxy.processors.gtpv2.ie.decoder.IpV4V6;

public class PdnAddressAllocation extends PdnType {
	public PdnAddressAllocation(final byte type, final byte spare, final byte instance, final byte[] payload) {
		super(type, spare, instance, payload);
	}


	public String getIpv4Address() {
		final Type pdnType = getPdnTypeValue();
		if (!Type.IP_V4.equals(pdnType) && !Type.IP_V4_V6.equals(pdnType)) {
			return null;
		}

		final byte[] ipv4 = new byte[4];
		System.arraycopy(this.payload, pdnType.equals(Type.IP_V4_V6) ? 17 : 1, ipv4, 0, 4);
		return IpV4V6.decodeV4(ipv4);
	}

	public String getIpv6PrefixLength() {
		if (!Type.IP_V6.equals(getPdnTypeValue()) && !Type.IP_V4_V6.equals(getPdnTypeValue())) {
			return null;
		}

		return String.format("%d", payload[1]);
	}

	public String getIpv6Address() {
		if (!Type.IP_V6.equals(getPdnTypeValue()) && !Type.IP_V4_V6.equals(getPdnTypeValue())) {
			return null;
		}

		final byte[] ipv6 = new byte[16];
		System.arraycopy(this.payload, 2, ipv6, 0, 16);
		return IpV4V6.decodeV6(ipv6);
	}

	@Override
	public String toString() {
		return "PdnAddressAllocation{" +
				"pdnType=" + getPdnTypeValue() +
				", ipv4Address=" + getIpv4Address() +
				", ipv6PrefixLength=" + getIpv6PrefixLength() +
				", ipv6Address=" + getIpv6Address() +
				'}';
	}
}
