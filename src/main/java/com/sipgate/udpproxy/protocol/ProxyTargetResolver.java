package com.sipgate.udpproxy.protocol;

import java.net.DatagramPacket;

public interface ProxyTargetResolver {

	/**
	 * Resolves the target for the given packet by inspecting the contents
	 * of the packet.
	 *
	 * @param inboundPacket the packet received from the source
	 * @return the target to which the packet should be forwarded
	 */
	ProxyTarget resolveTarget(DatagramPacket inboundPacket);
}
