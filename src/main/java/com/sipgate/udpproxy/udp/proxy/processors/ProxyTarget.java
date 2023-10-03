package com.sipgate.udpproxy.udp.proxy.processors;

/**
 * Represents a target to which the proxy forwards the packets.
 * @param host the host IP or Hostname of the target
 * @param port the port of the target
 */
public record ProxyTarget(String host, int port) {
}
