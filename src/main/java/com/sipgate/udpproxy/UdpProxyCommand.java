package com.sipgate.udpproxy;

import com.sipgate.udpproxy.udp.payload.Protocol;
import com.sipgate.udpproxy.udp.ProxyServer;
import com.sipgate.udpproxy.udp.ServerFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import sun.misc.Signal;

import java.util.Optional;
import java.util.concurrent.Callable;

@Command(
		name = "udpproxy",
		description = "A PoC UDP Proxy that forwards traffic from a proxy source to a proxy target",
		mixinStandardHelpOptions = true,
		version = "1.0.0"
)
public class UdpProxyCommand implements Callable<Integer> {

	@Option(names = {"-p", "--port"}, description = "The port to listen on", defaultValue = "5060")
	private int port;

	@Option(names = {"-i", "--interface"}, description = "The interface to listen on", defaultValue = "any")
	private String iface;

	@Option(names = {"-l", "--listen-ip"}, description = "The IP to listen on", defaultValue = "")
	private String listenIp;

	@Option(names = {"-t", "--type"}, description = "The protocol to proxy", defaultValue = "SIP")
	private Protocol protocol;

	@Override
	public Integer call() {
		final Optional<ProxyServer> maybeServer = ServerFactory.createServer(listenIp, port, iface, protocol);
		if (maybeServer.isEmpty()) {
			return 1;
		}

		final ProxyServer proxyServer = maybeServer.get();
		final var serverThread = new Thread(proxyServer, "proxy-server");

		serverThread.start();

		// FIXME: Handle SIGINT without internal java APIs
		Signal.handle(new Signal("INT"), signal -> proxyServer.stop());

		try {
			serverThread.join();
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		return 0;
	}
}
