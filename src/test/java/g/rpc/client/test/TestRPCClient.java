package g.rpc.client.test;

import org.junit.Test;

import g.rpc.client.RPCClientProxy;
import g.rpc.service.Message;
import g.rpc.service.MessageService;

public class TestRPCClient {

	@Test
	public void test() {
		RPCClientProxy proxy = new RPCClientProxy("127.0.0.1",9999);
		MessageService service = proxy.getProxy(MessageService.class);
		
		Message msg = service.getMessage("hello");
		System.out.println(msg);
	}

}
