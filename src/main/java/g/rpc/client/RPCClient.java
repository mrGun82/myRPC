package g.rpc.client;

import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import g.rpc.service.RPCRequest;
import g.rpc.service.RPCResponse;

public class RPCClient {

	public Object start(RPCRequest request, String host, int port) throws Throwable {
		Socket server = new Socket(host, port);
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(server.getOutputStream());
			oos.writeObject(request);
			oos.flush();
			
			ois = new ObjectInputStream(server.getInputStream());
			Object res = ois.readObject();
			RPCResponse response = null;
			if(res instanceof RPCResponse) {
				response = (RPCResponse)res;
			}else {
				throw new InvalidClassException("response is not typeof RPCResponse");
			}
			if(response.getError()!=null) {
				throw response.getError();
			}
			return response.getResult();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(oos!=null) oos.close();
				if(ois!=null) ois.close();
				if(server!=null) server.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
		}
		return null;
	}
}
