package com.sipgate.udpproxy.protocol;

import java.net.DatagramPacket;

public interface PacketRewriter {

	/**
	 * Rewrites the payload of the given packet. This method is called before the packet is forwarded to the target.
	 * Use this if the protocol in question must be modified (e.g. remove headers, replace IP addresses, ports, etc.).
	 *
	 * @param inboundPacket the packet received from the source
	 * @return	the modified payload
	 */
	byte[] rewritePayload(final DatagramPacket inboundPacket);
}
