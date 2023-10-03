package com.sipgate.udpproxy.udp.proxy.processors;

import java.net.DatagramPacket;

public interface ProxyTargetProcessor {

	/**
	 * Resolves the target for the given packet by inspecting the contents
	 * of the packet.
	 *
	 * @param inboundPacket the packet received from the source
	 * @return the target to which the packet should be forwarded
	 */
	ProxyTarget resolveTarget(DatagramPacket inboundPacket);
}
