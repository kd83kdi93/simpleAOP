package simpleAOP;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

class ProxyObject implements InvocationHandler{

	private Object o;
	private ProxyHandle handle;
	
	public ProxyObject(Object o, ProxyHandle handle) {
		this.o = o;
		this.handle = handle;
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		handle.before();
		method.invoke(o, args);
		handle.after();
		return null;
	}

}
