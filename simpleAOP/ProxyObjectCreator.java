package simpleAOP;

import java.lang.reflect.Proxy;

/**
 * 代理对象的创建工厂
 * @author hasee
 *
 */
public class ProxyObjectCreator {
	private ProxyObjectCreator() {}
	
	@SuppressWarnings("unchecked")
	public static <T> T create(T t, ProxyHandle handle) {
		ProxyObject pto = new ProxyObject(t, handle);
		T result = (T) Proxy.newProxyInstance(t.getClass().getClassLoader(),
											  t.getClass().getInterfaces(), 
											  pto);
		return result;
	}
}
