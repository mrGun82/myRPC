package g.rpc.server;

import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class RPCServer {

	public RPCServer() {

	}

	public void start(int port, String scanPackages) {
		ServerSocket server = null;
		try {
			server = new ServerSocket(port);
			Map<String, Object> services = getService(scanPackages);
			Executor executor = new ThreadPoolExecutor(5, 10, 10, TimeUnit.SECONDS,
					new LinkedBlockingQueue<Runnable>());
			for (;;) {
				Socket client = server.accept();
				RPCServerHandler service = new RPCServerHandler(client, services);
				executor.execute(service);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(server !=null)
				try {
					server.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
		}

	}

	private Map<String, Object> getService(String scanPackages) {
		try {
			Map<String,Object> services = new HashMap<String, Object>();
			String[] clazzes = scanPackages.split(",");
			List<Class<?>> classes = new ArrayList<Class<?>>();
			for(String cl : clazzes) {
				List<Class<?>> classList = getClasses(cl);
				classes.addAll(classList);
			}
			for(Class<?> cla:classes) {
				Object obj = cla.newInstance();
				services.put(cla.getAnnotation(Service.class).value().getName(), obj);
			}
			return services;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static List<Class<?>> getClasses(String scanPackage) throws ClassNotFoundException{
		List<Class<?>> classes = new ArrayList<Class<?>>();
		File directory = null;
		try {
			ClassLoader cld = Thread.currentThread().getContextClassLoader();
			if(cld==null)
				throw new ClassNotFoundException("ClassLoader is null");
			String path = scanPackage.replace('.', '/');
			URL resource = cld.getResource(path);
			if(resource == null)
				throw new ClassNotFoundException("resource is null");
			directory = new File(resource.getFile());
		} catch (Exception e) {
			throw new ClassNotFoundException(scanPackage +"["+directory+"] is not a effective resource");
		}
		
		if(directory.exists()) {
			String[] files = directory.list();
			File[] fileList = directory.listFiles();
			
			for(int i=0;fileList!=null&&i<fileList.length;i++) {
				File file = fileList[i];
				if(file.isFile() && file.getName().endsWith(".class")) {
					Class<?> clazz = Class.forName(scanPackage+"."+files[i].substring(0,files[i].length()-6));
					if(clazz.getAnnotation(Service.class)!=null) {
						classes.add(clazz);
					}
				}else if(file.isDirectory()) {
					List<Class<?>> result = getClasses(scanPackage+"."+file.getName());
					if(result != null && result.size()!=0) {
						classes.addAll(result);
					}
				}
			}
		}else {
			throw new ClassNotFoundException(scanPackage+" is not a package");
		}
		return classes;
	}
}
