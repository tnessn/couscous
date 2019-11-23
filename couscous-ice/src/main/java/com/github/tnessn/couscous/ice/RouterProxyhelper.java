package com.github.tnessn.couscous.ice;

import java.util.HashMap;
import java.util.Map;

import Glacier2.SessionCallback;
import Ice.Communicator;
import Ice.ObjectPrx;
import IceInternal.Util;


// TODO: Auto-generated Javadoc
/**
 * 通过glacier2调用接口.
 *
 * @author huangjinfeng
 */
public class RouterProxyhelper {
	
	/** The Constant HOST. */
	private static final String HOST = "192.168.9.86";
	
	/** The Constant PORT. */
	private static final int PORT = 4065;
	
	/** The Constant PROTOCOL. */
	private static final String PROTOCOL = "tcp";

	/** The instance. */
	private static RouterProxyhelper _instance = null;

	/** The session. */
	private Glacier2.SessionHelper _session;
	
	/** The factory. */
	private Glacier2.SessionFactoryHelper _factory;
	
	/** The user name. */
	private  String userName;
	
	/** The password. */
	private  String password;
	
	/** The map. */
	private  Map<String, String> map;

	/**
	 * The Class JUSessionCallback.
	 */
	private class JUSessionCallback implements SessionCallback {
		
		/** The proxy helper. */
		RouterProxyhelper _proxyHelper;

		/**
		 * Instantiates a new JU session callback.
		 *
		 * @param proxyHelper the proxy helper
		 */
		JUSessionCallback(RouterProxyhelper proxyHelper) {
			_proxyHelper = proxyHelper;
		}

		/* (non-Javadoc)
		 * @see Glacier2.SessionCallback#connected(Glacier2.SessionHelper)
		 */
		@Override
		public void connected(Glacier2.SessionHelper session) throws Glacier2.SessionNotExistException {
			synchronized (_proxyHelper) {
				_proxyHelper.notify();
			}

			System.out.println("connected");
		}

		/* (non-Javadoc)
		 * @see Glacier2.SessionCallback#disconnected(Glacier2.SessionHelper)
		 */
		@Override
		public void disconnected(Glacier2.SessionHelper session) {
			System.out.println("disconnected");
		}

		/* (non-Javadoc)
		 * @see Glacier2.SessionCallback#connectFailed(Glacier2.SessionHelper, java.lang.Throwable)
		 */
		@Override
		public void connectFailed(Glacier2.SessionHelper session, Throwable ex) {
			synchronized (_proxyHelper) {
				_proxyHelper.notify();
			}
			System.out.println("connectFailed");
		}

		/* (non-Javadoc)
		 * @see Glacier2.SessionCallback#createdCommunicator(Glacier2.SessionHelper)
		 */
		@Override
		public void createdCommunicator(Glacier2.SessionHelper session) {
			System.out.println("createdCommunicator");
		}
	}

	/**
	 * Gets the single instance of RouterProxyhelper.
	 *
	 * @return single instance of RouterProxyhelper
	 */
	public static RouterProxyhelper getInstance() {
		if (_instance == null) {
			synchronized (RouterProxyhelper.class) {
				if (_instance == null) {
					_instance = new RouterProxyhelper();
				}
			}
		}
		return _instance;
	}

	/**
	 * Instantiates a new router proxyhelper.
	 */
	private RouterProxyhelper() {
		
		Map<String, String> map = new HashMap<>();
		map.put("osType", "android");
		
		
		this.userName="userName";
		this.password="password";
		this.map=map;
		
		String[] initParams = new String[] {};
		Communicator ice = Ice.Util.initialize(initParams);
		//Communicator ice = Application.communicator();
		Ice.InitializationData initData = Util.getInstance(ice).initializationData().clone();
		initData.properties.setProperty("Ice.Admin.DelayCreation", "1");
		initData.properties.setProperty("Ice.InitPlugins", "0");
		

		_factory = new Glacier2.SessionFactoryHelper(initData, new JUSessionCallback(this));
		_factory.setUseCallbacks(false);
		_factory.setRouterHost(HOST);
		_factory.setPort(PORT);
		_factory.setProtocol(PROTOCOL);
	}

	/**
	 * Checks if is connected.
	 *
	 * @return true, if is connected
	 */
	public boolean isConnected() {
		return _session != null && _session.isConnected();
	}

	/**
	 * Session.
	 *
	 * @return the glacier 2 . session helper
	 */
	private Glacier2.SessionHelper session() {
		if (_session == null || !_session.isConnected()) {
			synchronized (this) {
				if (_session == null || !_session.isConnected()) {
					_factory.setConnectContext(map);
					_session = _factory.connect(userName, password);
					while (true) {
						try {
							wait();
							break;
						} catch (java.lang.InterruptedException ex) {
						}
					}
				}
			}
		}
		if (_session.isConnected()) {
			return _session;
		} else {
			_session = null;
			return null;
		}
	}

	/**
	 * String to proxy.
	 *
	 * @param objectId the object id
	 * @return the object prx
	 */
	public ObjectPrx stringToProxy(String objectId) {
		if (session() == null) {
			return null;
		}
		return _session.communicator().stringToProxy(objectId);
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		Ice.ObjectPrx obj = RouterProxyhelper.getInstance().stringToProxy("pltDeviceService");
/*		PltProxyServicePrx prx = PltDeviceServicePrxHelper.uncheckedCast(obj);
		String register = prx.register("android123", "deviceIdentifierhuangjinfeng123", "apnhuangjinfeng123");
		System.out.println(register);*/
	}

}