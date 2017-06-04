package simpleAOP;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
/**
 * ��չ���������ʵ��ProxyHandle�ӿ��⻹��Ҫ�������ϼ���Aop
 * ��ע��
 * @author hasee
 *
 */
public @interface Aop {
	/**
	 * name����Ϊ������������
	 * @return
	 */
	public String name() default "";
}
