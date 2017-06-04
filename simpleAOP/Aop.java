package simpleAOP;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
/**
 * 扩展功能类除了实现ProxyHandle接口外还需要在类名上加上Aop
 * 的注解
 * @author hasee
 *
 */
public @interface Aop {
	/**
	 * name参数为代理对象的类名
	 * @return
	 */
	public String name() default "";
}
