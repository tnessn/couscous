package com.github.tnessn.couscous.ice;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import Ice.ObjectPrx;
import IceGrid.AdminPrx;
import IceGrid.AdminSessionPrx;
import IceGrid.PermissionDeniedException;
import IceGrid.RegistryPrx;
import IceGrid.RegistryPrxHelper;
import IceGrid.SessionPrx;

// TODO: Auto-generated Javadoc
/**
 * The Class IceClientUtil.
 *
 * @author Administrator
 * @since 2017/2/9
 */
public class IceClientUtil {
	
	/** The communicator. */
	private static volatile Ice.Communicator communicator = null;
	
	/** The class object prx map. */
	private static Map<Class<?>, ObjectPrx> classObjectPrxMap = new HashMap<>();
	
	/** The last access timestamp. */
	private static volatile long lastAccessTimestamp;
	
	/** The monitor thread. */
	private static volatile MonitorThread monitorThread;
	
	/** The idle time out seconds. */
	private static long idleTimeOutSeconds = 0;
	
	/** The ice locator. */
	private static String iceLocator = null;
	
	/** The Constant locatorKey. */
	private static final String locatorKey = "--Ice.Default.Locator";
	
	/** The user. */
	private static String USER;
	
	/** The password. */
	private static String PASSWORD;

	/**
	 * Gets the communicator.
	 *
	 * @return the communicator
	 */
	public static Ice.Communicator getCommunicator() {
		if (communicator == null) {
			synchronized (IceClientUtil.class) {
				if (communicator == null) {
					if (iceLocator == null) {
						ResourceBundle rb = ResourceBundle.getBundle("iceClient", Locale.ENGLISH);
						iceLocator = rb.getString(locatorKey);
						idleTimeOutSeconds = Integer.parseInt(rb.getString("idleTimeOutSeconds"));
						USER=rb.getString("user");
						PASSWORD=rb.getString("password");
						System.out.println("Ice client locator is " + iceLocator
								+ " proxy cache time out seconds: " + idleTimeOutSeconds);
					}
					String[] initParams = new String[] { locatorKey + "=" + iceLocator };
					communicator = Ice.Util.initialize(initParams);
					createMonitorThread();
				}
			}
		}
		lastAccessTimestamp = System.currentTimeMillis();
		return communicator;
	}

	/**
	 * Creates the monitor thread.
	 */
	private static void createMonitorThread() {
		monitorThread = new MonitorThread();
		monitorThread.setDaemon(true);
		monitorThread.start();
	}

	/**
	 * Close communicator.
	 *
	 * @param removeServiceCache the remove service cache
	 */
	public static void closeCommunicator(boolean removeServiceCache) {
		synchronized (IceClientUtil.class) {
			if (communicator != null) {
				safeShutDown();
				monitorThread.interrupt();
				if (removeServiceCache && !classObjectPrxMap.isEmpty()) {
					try {
						classObjectPrxMap.clear();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * Safe shut down.
	 */
	private static void safeShutDown() {
		try {
			communicator.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			communicator.destroy();
			communicator = null;
		}
	}

	/**
	 * Creates the ice proxy.
	 *
	 * @param communicator the communicator
	 * @param serviceCls the service cls
	 * @return the ice. object prx
	 */
	private static Ice.ObjectPrx createIceProxy(Ice.Communicator communicator, Class<?> serviceCls) {
		Ice.ObjectPrx proxy;
		String className = serviceCls.getName();
		String serviceName = serviceCls.getSimpleName();
		int pos = serviceName.lastIndexOf("Prx");
		if (pos <= 0) {
			throw new java.lang.IllegalArgumentException(
					"Invalid ObjectPrx class, class name must end with Prx");
		}
		String realServiceName = serviceName.substring(0, pos);
		try {
			Ice.ObjectPrx base = communicator.stringToProxy(realServiceName);
			proxy = (ObjectPrx) Class.forName(className + "Helper").newInstance();
			Method method = proxy.getClass().getDeclaredMethod("uncheckedCast", Ice.ObjectPrx.class);
			proxy = (Ice.ObjectPrx) method.invoke(proxy, base);
			return proxy;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * The Class MonitorThread.
	 */
	static class MonitorThread extends Thread {
		
		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			while (!Thread.currentThread().isInterrupted()) {
				try {
					Thread.sleep(5000L);
					if (lastAccessTimestamp + idleTimeOutSeconds * 1000L < System.currentTimeMillis()) {
						closeCommunicator(true);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/**
	 * Gets the admin prx.
	 *
	 * @return the admin prx
	 */
	public static AdminPrx getAdminPrx() {
		AdminSessionPrx session = null;
		AdminPrx adminPrx = null;
		try {
			ObjectPrx base = IceClientUtil.getCommunicator().stringToProxy("IceGrid/Registry");
			RegistryPrx registry = RegistryPrxHelper.uncheckedCast(base);
			session = registry.createAdminSession(USER, PASSWORD);
			adminPrx = session.getAdmin();
		} catch (PermissionDeniedException e) {
			throw new RuntimeException("ice registry connection error endpoint",e);
		}
		return adminPrx;
	}

	/**
	 * Gets the session prx.
	 *
	 * @return the session prx
	 */
	public static SessionPrx getSessionPrx() {
		SessionPrx session = null;
		try {
			ObjectPrx base = IceClientUtil.getCommunicator().stringToProxy("IceGrid/Registry");
			RegistryPrx registry = RegistryPrxHelper.uncheckedCast(base);
			session = registry.createSession(USER, PASSWORD);
		} catch (PermissionDeniedException e) {
			throw new RuntimeException("ice registry connection error endpoint",e);
		}
		return session;
	}
}