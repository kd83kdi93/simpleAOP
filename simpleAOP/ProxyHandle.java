package simpleAOP;

/**
 * ProxyHandle接口作为AOP扩展功能接口，扩展功能必须实现该接口
 * @author hasee
 *
 */
public interface ProxyHandle {
	void before();
	void after();
}
