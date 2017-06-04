package simpleAOP;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AopScanner {
	private List<Object> objects = new ArrayList<Object>();
	private String packageName = null;
	private ProxyObjectCreator poc;
	private int sameIndex = -1;
	
	/**
	 * 只能支持一级包，不支持包的子包
	 * @param packageName 包名
	 */
	public AopScanner(String packageName) {
		this.packageName = packageName;
		String absolutePath = getAbsolutePath(packageName);
		getProxyObject(absolutePath);
	}
	
	
	public <T> T getObject(Class<T> c) {
		for (Object o : objects) {
			if (c.isInstance(o)) {
				return c.cast(o);
			}
		}
		return null;
	}

	private String getAbsolutePath(String packName) {
		return System.getProperty("user.dir")+"/bin/"+packName;
	}

	private void getProxyObject(String path) {
		File[] files = getFiles(path);
		createProxyObjectFromFiles(files);
	}


	private void createProxyObjectFromFiles(File[] files) {
		Object o = null;
		for (File f : files) {
			if (f.getName().endsWith("class")) {
				o = getProxyFromAnnotation(f);
				if (o != null) {
					if (sameIndex == -1) {
						objects.add(o);
					} else {
						objects.set(sameIndex, o);
						sameIndex = -1;
					}
				}
			}
		}
	}


	private File[] getFiles(String path) {
		File file = new File(path);
		File[] files = file.listFiles();
		return files;
	}

	private Object getProxyFromAnnotation(File f) {
		Aop aop = null;
		Object resObject = null;
		ProxyHandle proxyHandle = null;
		String fullClassName = getClassFullName(f);
		try {
			aop = Class.forName(fullClassName).getAnnotation(Aop.class);
			if (aop != null) {
				resObject = Class.forName(aop.name()).newInstance();
				proxyHandle = (ProxyHandle)Class.forName(fullClassName).newInstance();
				resObject = checkInterFaceEqual(resObject);
				return poc.create(resObject, proxyHandle); 
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}


	private Object checkInterFaceEqual(Object resObject) {
		int index = -1;
		Object result = resObject;
		Class[] resObjectInterFaces = resObject.getClass().getInterfaces();
		for (Object tmpObject : objects) {
			index++;
			Class[] tmpObjectInterFaces = tmpObject.getClass().getInterfaces();
			if (Arrays.equals(resObjectInterFaces, tmpObjectInterFaces)) {
				result = tmpObject;
				sameIndex = index;
				break;
			}
		}
		return result;
	}


	private String getClassFullName(File f) {
		String className = f.getName().split("\\.")[0];
		String fullClassName = packageName + "." + className;
		return fullClassName;
	}
}
