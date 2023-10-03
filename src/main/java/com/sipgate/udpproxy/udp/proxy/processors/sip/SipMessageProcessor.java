package com.sipgate.udpproxy.udp.proxy.processors.sip;

import com.sipgate.udpproxy.udp.proxy.processors.Message;
import com.sipgate.udpproxy.udp.proxy.processors.MessageProcessor;

import java.net.DatagramPacket;

public class SipMessageProcessor implements MessageProcessor {

	/**
	 * Removes the Route header from the SIP packet.
	 * sipgate customer loadbalancers will respond with 403 if the Route header is present.
	 *
	 * @param packet the packet to rewrite
	 * @param message the message to rewrite
	 */
	@Override
	public void rewriteMessage(final DatagramPacket packet, final Message message) {
		final SipMessage sipMessage = (SipMessage) message;
		final String original = sipMessage.getMessage();
		final StringBuilder sb = new StringBuilder();
		original.lines().forEach(line -> {
			if (!line.startsWith("Route:")) {
				sb.append(line).append("\r\n");
			}
		});
		sipMessage.setMessage(sb.toString());
	}
}
