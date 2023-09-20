package com.sipgate.udpproxy.protocol.sip;

import com.sipgate.udpproxy.protocol.ProxyTarget;
import com.sipgate.udpproxy.protocol.ProxyTargetResolver;

import java.net.DatagramPacket;

public class SipTargetResolver implements ProxyTargetResolver {
	@Override
	public ProxyTarget resolveTarget(final DatagramPacket inboundPacket) {
		// FIXME: sipgate customer loadbalancer hardcoded. Inspect packet for real target
		return new ProxyTarget("217.10.77.242", 5060);
	}
}
