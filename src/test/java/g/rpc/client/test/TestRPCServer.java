package g.rpc.client.test;

import org.junit.Test;

import g.rpc.server.RPCServer;

public class TestRPCServer {

	@Test
	public void test() {
		RPCServer server = new RPCServer();
		server.start(9999, "g.rpc.service");
	}

}
