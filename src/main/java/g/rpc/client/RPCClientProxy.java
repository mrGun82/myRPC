package g.rpc.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import g.rpc.service.RPCRequest;

public class RPCClientProxy implements InvocationHandler {

	private String host;
	private int port;

	public RPCClientProxy(String host, int port) {
		super();
		this.host = host;
		this.port = port;
	}

	@SuppressWarnings("unchecked")
	public <T> T getProxy(Class<T> clazz) {
		return (T) (Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[] { clazz }, this));
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		RPCRequest request = new RPCRequest();
		request.setClassName(method.getDeclaringClass().getName());
		request.setMethodName(method.getName());
		request.setParameterTypes(method.getParameterTypes());
		request.setParams(args);

		RPCClient client = new RPCClient();
		Object res = client.start(request, host, port);
		return res;
	}
}
