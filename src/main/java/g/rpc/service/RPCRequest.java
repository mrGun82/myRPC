package g.rpc.service;

import java.io.Serializable;

public class RPCRequest implements Serializable {
	private static final long serialVersionUID = -4615618456422539816L;
	private String className;
	private String methodName;
	private Class<?>[] parameterTypes;
	private Object[] params;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Class<?>[] getParameterTypes() {
		return parameterTypes;
	}

	public void setParameterTypes(Class<?>[] parameterTypes) {
		this.parameterTypes = parameterTypes;
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}
}
