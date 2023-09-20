package com.sipgate.udpproxy.protocol.sip;

import com.sipgate.udpproxy.protocol.PacketRewriter;
import java.net.DatagramPacket;

public class SipRewriter implements PacketRewriter {

	/**
	 * Removes the Route header from the SIP packet.
	 * sipgate customer loadbalancers will respond with 403 if the Route header is present.
	 */
	@Override
	public byte[] rewritePayload(final DatagramPacket inboundPacket) {
		final byte[] payload = new byte[inboundPacket.getLength()];
		System.arraycopy(inboundPacket.getData(), inboundPacket.getOffset(), payload, 0, inboundPacket.getLength());
		final String original = new String(payload);
		final StringBuilder sb = new StringBuilder();
		original.lines().forEach(line -> {
			if (!line.startsWith("Route:")) {
				sb.append(line).append("\r\n");
			}
		});

		return sb.toString().getBytes();
	}
}
