package com.sipgate.udpproxy.udp.proxy.processors;

import java.net.DatagramPacket;

public interface MessageProcessor {

	/**
	 * Rewrites the payload of a UDP packet. This method is called before the packet is forwarded to the target.
	 * Use this if the protocol in question must be modified (e.g. remove headers, replace IP addresses, ports, etc.).
	 *
	 * @param packet the packet from the source to be rewritten
	 * @param message the message to be rewritten
	 */
	void rewriteMessage(DatagramPacket packet, Message message);
}
