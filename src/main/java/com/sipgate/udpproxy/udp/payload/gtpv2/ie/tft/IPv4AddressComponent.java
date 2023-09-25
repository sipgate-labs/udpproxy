package com.sipgate.udpproxy.udp.payload.gtpv2.ie.tft;

import com.sipgate.udpproxy.udp.payload.gtpv2.ie.decoder.IpV4V6;

public class IPv4AddressComponent extends GenericPacketFilterComponent {
	public IPv4AddressComponent(final byte[] payload) {
		super(payload);
	}

	public IPv4AddressComponent() {
		super(new byte[8 + 8 + 1]); // Address and mask + type
		setPacketFilterComponentType(PacketFilterComponentType.IPV4_REMOTE_ADDRESS);
	}

	public IPv4AddressComponent setPacketFilterComponentType(final PacketFilterComponentType packetFilterComponentType) {
		if (PacketFilterComponentType.IPV4_REMOTE_ADDRESS != packetFilterComponentType
				&& PacketFilterComponentType.IPV4_LOCAL_ADDRESS != packetFilterComponentType) {
			throw new IllegalArgumentException(
					"Packet filter component type must be IPV4_REMOTE_ADDRESS OR IPV4_LOCAL_ADDRESS"
			);
		}
		payload[0] = (byte) packetFilterComponentType.getCode();
		return this;
	}

	public String getIpv4Address() {
		final byte[] ipv4Address = new byte[4];
		System.arraycopy(payload, 1, ipv4Address, 0, 4);

		return IpV4V6.decodeV4(ipv4Address);
	}

	public IPv4AddressComponent setIpv4RemoteAddress(final String ipv4RemoteAddress) {
		final byte[] ipv4Address = IpV4V6.encodeV4(ipv4RemoteAddress);
		System.arraycopy(ipv4Address, 0, payload, 1, 4);

		return this;
	}

	public String getIpv4Mask() {
		final byte[] ipv4Address = new byte[4];
		System.arraycopy(payload, 9, ipv4Address, 0, 4);

		return IpV4V6.decodeV4(ipv4Address);
	}

	public IPv4AddressComponent setIpv4Mask(final String ipv4RemoteAddress) {
		final byte[] ipv4Address = IpV4V6.encodeV4(ipv4RemoteAddress);
		System.arraycopy(ipv4Address, 0, payload, 9, 4);

		return this;
	}

	@Override
	public String toString() {
		return "IPv4AddressComponent{" +
				"packetFilterComponentType=" + getPacketFilterComponentType() +
				", ipv4Address=" + getIpv4Address() +
				", ipv4Mask=" + getIpv4Mask() +
				'}';
	}

}
