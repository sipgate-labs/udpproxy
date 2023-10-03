package com.sipgate.udpproxy.udp.proxy.processors.gtpv2.ie.tft;

import com.sipgate.udpproxy.udp.proxy.processors.gtpv2.ie.decoder.IpV4V6;

public class IPv6AddressComponent extends GenericPacketFilterComponent {
	public IPv6AddressComponent(final byte[] payload) {
		super(payload);
	}

	public IPv6AddressComponent() {
		super(new byte[16 + 16 + 1]); // Address and mask + type
		setPacketFilterComponentType(PacketFilterComponentType.IPV6_REMOTE_ADDRESS);
	}

	public String getIpV6Address() {
		final byte[] ipv6Address = new byte[16];
		System.arraycopy(payload, 1, ipv6Address, 0, 16);

		return IpV4V6.decodeV6(ipv6Address);
	}

	public IPv6AddressComponent setIpV6Address(final String ipV6Address) {
		final byte[] ipv6Address = IpV4V6.encodeV6(ipV6Address);
		System.arraycopy(ipv6Address, 0, payload, 1, 16);

		return this;
	}

	public String getIpV6Mask() {
		final byte[] ipv6Address = new byte[16];
		System.arraycopy(payload, 17, ipv6Address, 0, 16);

		return IpV4V6.decodeV6(ipv6Address);
	}

	public IPv6AddressComponent setIpV6Mask(final String ipV6Mask) {
		final byte[] ipv6Address = IpV4V6.encodeV6(ipV6Mask);
		System.arraycopy(ipv6Address, 0, payload, 17, 16);

		return this;
	}

	@Override
	public String toString() {
		return "IPv6AddressComponent{" +
				"packetFilterComponentType=" + getPacketFilterComponentType() +
				", ipV6Address=" + getIpV6Address() +
				", ipV6Mask=" + getIpV6Mask() +
				'}';
	}
}
