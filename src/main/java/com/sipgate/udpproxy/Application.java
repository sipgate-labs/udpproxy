package com.sipgate.udpproxy;

import picocli.CommandLine;

public class Application {

	public static void main(final String[] args) {
		System.exit(new CommandLine(new UdpProxyCommand()).execute(args));
	}


}
