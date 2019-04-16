package g.rpc.server;

import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;

import g.rpc.service.RPCRequest;
import g.rpc.service.RPCResponse;

public class RPCServerHandler implements Runnable {

	private Socket client;

	private Map<String, Object> services;

	public RPCServerHandler(Socket client, Map<String, Object> services) {
		this.client = client;
		this.services = services;
	}

	public void run() {
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;
		RPCResponse response = new RPCResponse();
		try {
			ois = new ObjectInputStream(client.getInputStream());
			oos = new ObjectOutputStream(client.getOutputStream());
			Object param = ois.readObject();
			RPCRequest request = null;
			if (param instanceof RPCRequest) {
				request = (RPCRequest) param;
			} else {
				response.setError(new InvalidClassException("request is not typeof RPCRequest"));
				oos.writeObject(response);
				oos.flush();
				return;
			}

			Object service = services.get(request.getClassName());
			Class<?> clazz = service.getClass();
			Method method = clazz.getMethod(request.getMethodName(), request.getParameterTypes());
			Object result = method.invoke(service, request.getParams());

			response.setResult(result);
			oos.writeObject(response);
			oos.flush();
			return;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (oos != null)
					oos.close();
				if (ois != null)
					ois.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}
