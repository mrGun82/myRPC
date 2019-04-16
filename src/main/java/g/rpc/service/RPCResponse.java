package g.rpc.service;

import java.io.Serializable;

public class RPCResponse implements Serializable {

	private static final long serialVersionUID = 249776705063038676L;
	private Throwable error;
	private Object result;

	public Throwable getError() {
		return error;
	}

	public void setError(Throwable error) {
		this.error = error;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

}
