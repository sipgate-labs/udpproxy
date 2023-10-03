package com.sipgate.udpproxy.udp.proxy.processors.sip;

import com.sipgate.udpproxy.udp.proxy.processors.ProxyTarget;
import com.sipgate.udpproxy.udp.proxy.processors.ProxyTargetProcessor;

import java.net.DatagramPacket;

public class SipTargetProcessor implements ProxyTargetProcessor {
	@Override
	public ProxyTarget resolveTarget(final DatagramPacket inboundPacket) {
		// FIXME: sipgate customer loadbalancer hardcoded. Inspect packet for real target
		return new ProxyTarget("217.10.77.242", 5060);
	}
}
