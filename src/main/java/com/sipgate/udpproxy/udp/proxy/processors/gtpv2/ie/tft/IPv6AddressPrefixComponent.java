package com.sipgate.udpproxy.udp.proxy.processors.gtpv2.ie.tft;

import com.sipgate.udpproxy.udp.proxy.processors.gtpv2.ie.decoder.IpV4V6;

public class IPv6AddressPrefixComponent extends GenericPacketFilterComponent {
	public IPv6AddressPrefixComponent(final byte[] payload) {
		super(payload);
	}

	public IPv6AddressPrefixComponent() {
		super(new byte[16 + 1 + 1]); // Address and prefix length + type
		setPacketFilterComponentType(PacketFilterComponentType.IPV6_REMOTE_ADDRESS_PREFIX_LENGTH);
	}

	public String getIpV6Address() {
		final byte[] ipv6Address = new byte[16];
		System.arraycopy(payload, 1, ipv6Address, 0, 16);

		return IpV4V6.decodeV6(ipv6Address);
	}

	public IPv6AddressPrefixComponent setIpV6Address(final String ipV6Address) {
		final byte[] ipv6Address = IpV4V6.encodeV6(ipV6Address);
		System.arraycopy(ipv6Address, 0, payload, 1, 16);

		return this;
	}

	public int getIpV6PrefixLength() {
		return payload[17];
	}

	public IPv6AddressPrefixComponent setIpV6PrefixLength(final int ipV6PrefixLength) {
		payload[17] = (byte) ipV6PrefixLength;
		return this;
	}

	@Override
	public String toString() {
		return "IPv6AddressPrefixComponent{" +
				"packetFilterComponentType=" + getPacketFilterComponentType() +
				", ipV6Address=" + getIpV6Address() +
				", ipV6PrefixLength=" + getIpV6PrefixLength() +
				'}';
	}
}
