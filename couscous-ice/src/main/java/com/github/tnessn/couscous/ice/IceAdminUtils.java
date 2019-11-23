package com.github.tnessn.couscous.ice;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.github.tnessn.couscous.lang.util.JsonUtils;

import Ice.Identity;
import IceGrid.AccessDeniedException;
import IceGrid.AdapterInfo;
import IceGrid.AdapterNotExistException;
import IceGrid.AdminPrx;
import IceGrid.ApplicationDescriptor;
import IceGrid.ApplicationInfo;
import IceGrid.ApplicationNotExistException;
import IceGrid.ApplicationUpdateDescriptor;
import IceGrid.DeploymentException;
import IceGrid.FileParserPrx;
import IceGrid.FileParserPrxHelper;
import IceGrid.NodeDescriptor;
import IceGrid.NodeInfo;
import IceGrid.NodeNotExistException;
import IceGrid.NodeUnreachableException;
import IceGrid.NodeUpdateDescriptor;
import IceGrid.ObjectDescriptor;
import IceGrid.ParseException;
import IceGrid.PropertyDescriptor;
import IceGrid.ReplicaGroupDescriptor;
import IceGrid.ServerInstanceDescriptor;
import IceGrid.ServerNotExistException;
import IceGrid.ServerStartException;
import IceGrid.ServerState;
import IceGrid.ServerStopException;
import IceGrid.TemplateDescriptor;

// TODO: Auto-generated Javadoc
/**
 * 操作注册中心工具类，跟业务无关.
 *
 * @author huangjinfeng
 */
public class IceAdminUtils {
	
	/** The icegridadmin process. */
	private static Process icegridadminProcess;
	
	/** The file parser. */
	private static String _fileParser;
	
	/** The descriptor. */
	private static ApplicationDescriptor descriptor;

	/**
	 * Instantiates a new ice admin utils.
	 */
	private IceAdminUtils() {
	}

	/**
	 * Parses the file.
	 *
	 * @param file the file
	 * @return the application descriptor
	 */
	public static ApplicationDescriptor parseFile(File file) {
		if (icegridadminProcess == null) {
			try {
				icegridadminProcess = Runtime.getRuntime().exec("icegridadmin --server");
			} catch (IOException e) {
				throw new RuntimeException("execute icegridadmin --server error", e);
			}
		}
		try {
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(icegridadminProcess.getInputStream(), "US-ASCII"));
			String str = reader.readLine();
			reader.close();
			if (str == null || str.length() == 0) {
				throw new RuntimeException("The icegridadmin subprocess failed");
			}
			_fileParser = str;
		} catch (java.io.IOException e) {
			destroyIceGridAdmin();
			throw new RuntimeException(e);
		}

		FileParserPrx fileParser = FileParserPrxHelper
				.checkedCast(IceClientUtil.getCommunicator().stringToProxy(_fileParser).ice_router(null));
		try {
			ApplicationDescriptor parse = fileParser.parse(file.getAbsolutePath(), null);
			destroyIceGridAdmin();
			return parse;
		} catch (ParseException e) {
			destroyIceGridAdmin();
			throw new RuntimeException("Failed to parse file '" + file.getAbsolutePath(), e);
		}
	}

	/**
	 * Destroy ice grid admin.
	 */
	private static void destroyIceGridAdmin() {
		if (icegridadminProcess != null) {
			try {
				icegridadminProcess.destroy();
			} catch (Exception e) {
			}
			icegridadminProcess = null;
			_fileParser = null;
		}
	}

	/**
	 * Stop server.
	 *
	 * @param serverId the server id
	 */
	public static void stopServer(String serverId) {
		AdminPrx adminPrx = IceClientUtil.getAdminPrx();
		try {
			adminPrx.stopServer(serverId);
		} catch (DeploymentException | NodeUnreachableException | ServerNotExistException | ServerStopException e) {
			throw new RuntimeException("stop server error id:" + serverId, e);
		}
	}

	/**
	 * Gets the all node.
	 *
	 * @return the all node
	 */
	public static List<NodeInfo> getAllNode() {
		List<NodeInfo> list = new ArrayList<>();
		AdminPrx adminPrx = IceClientUtil.getAdminPrx();
		String[] allNodeNames = adminPrx.getAllNodeNames();
		for (String nodeName : allNodeNames) {
			try {
				list.add(adminPrx.getNodeInfo(nodeName));
			} catch (NodeNotExistException | NodeUnreachableException e) {
				throw new RuntimeException("get all node error", e);
			}
		}
		return list;
	}

	/**
	 * Shutdown ice grid registry.
	 */
	public static void shutdownIceGridRegistry() {
		AdminPrx adminPrx = IceClientUtil.getAdminPrx();
		adminPrx.shutdown();
	}

	/**
	 * Removes the nodes.
	 *
	 * @param appName the app name
	 * @param nodeNames the node names
	 */
	public static void removeNodes(String appName, String... nodeNames) {
		AdminPrx adminPrx = IceClientUtil.getAdminPrx();
		ApplicationUpdateDescriptor descriptor = new ApplicationUpdateDescriptor();
		descriptor.name = appName;
		descriptor.removeNodes = nodeNames;
		try {
			adminPrx.updateApplication(descriptor);
		} catch (AccessDeniedException | ApplicationNotExistException | DeploymentException e) {
			throw new RuntimeException("remove nodes error:" +JsonUtils.bean2JsonStr(nodeNames), e);
		}
	}

	/**
	 * Adds the node.
	 *
	 * @param appName the app name
	 * @param nodeName the node name
	 */
	public static void addNode(String appName, String nodeName) {
		AdminPrx adminPrx = IceClientUtil.getAdminPrx();
		ApplicationUpdateDescriptor descriptor = new ApplicationUpdateDescriptor();
		List<NodeUpdateDescriptor> nodesList = new ArrayList<>();
		NodeUpdateDescriptor nodeUpdateDescriptor = new NodeUpdateDescriptor();
		nodeUpdateDescriptor.name = nodeName;
		nodesList.add(nodeUpdateDescriptor);
		descriptor.name = appName;
		descriptor.nodes = nodesList;
		try {
			adminPrx.updateApplication(descriptor);
		} catch (AccessDeniedException | ApplicationNotExistException | DeploymentException e) {
			throw new RuntimeException("add node error:" + nodeName, e);
		}
	}

	/**
	 * Adds the application.
	 *
	 * @param applicationDescriptor the application descriptor
	 */
	public static void addApplication(ApplicationDescriptor applicationDescriptor) {
		AdminPrx adminPrx = IceClientUtil.getAdminPrx();
		try {
			adminPrx.addApplication(applicationDescriptor);
		} catch (AccessDeniedException | DeploymentException e) {
			throw new RuntimeException("add application error", e);
		}
	}

	/**
	 * Sync application.
	 *
	 * @param applicationDescriptor the application descriptor
	 */
	public static void syncApplication(ApplicationDescriptor applicationDescriptor) {
		AdminPrx adminPrx = IceClientUtil.getAdminPrx();
		try {
			adminPrx.syncApplication(applicationDescriptor);
		} catch (AccessDeniedException | ApplicationNotExistException | DeploymentException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Sync application without restart.
	 *
	 * @param applicationDescriptor the application descriptor
	 */
	public static void syncApplicationWithoutRestart(ApplicationDescriptor applicationDescriptor) {
		AdminPrx adminPrx = IceClientUtil.getAdminPrx();
		try {
			adminPrx.syncApplicationWithoutRestart(applicationDescriptor);
		} catch (AccessDeniedException | ApplicationNotExistException | DeploymentException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Gets the servers name by app name.
	 *
	 * @param appName the app name
	 * @return the servers name by app name
	 */
	public static List<String> getServersNameByAppName(String appName) {
		AdminPrx adminPrx = IceClientUtil.getAdminPrx();
		String[] serverIds = adminPrx.getAllServerIds();
		return Arrays.asList(serverIds);
	}

	/**
	 * Gets the servers by app name and server template name.
	 *
	 * @param appName the app name
	 * @param serverTemplateName the server template name
	 * @return the servers by app name and server template name
	 */
	public static List<String> getServersByAppNameAndServerTemplateName(String appName, String serverTemplateName) {
		List<String> list = new ArrayList<>();
		AdminPrx adminPrx = IceClientUtil.getAdminPrx();
		ApplicationDescriptor applicationDescriptor = null;
		try {
			applicationDescriptor = adminPrx.getApplicationInfo(appName).descriptor;
		} catch (ApplicationNotExistException e) {
			return list;
		}
		Map<String, NodeDescriptor> nodes = applicationDescriptor.nodes;
		for (Map.Entry<String, NodeDescriptor> entry : nodes.entrySet()) {
			List<ServerInstanceDescriptor> serverInstances = entry.getValue().serverInstances;
			for (ServerInstanceDescriptor s : serverInstances) {
				if (s.template.equals(serverTemplateName)) {
					list.add(getServerId(appName, serverTemplateName, s.parameterValues));
				}
			}
		}
		return list;
	}

	/**
	 * Gets the server id.
	 *
	 * @param appName the app name
	 * @param serverTemplateName the server template name
	 * @param parameterValues the parameter values
	 * @return the server id
	 */
	private static String getServerId(String appName, String serverTemplateName, Map<String, String> parameterValues) {
		String name = getServerNameByAppNameAndTemplateName(appName, serverTemplateName);
		if (StringUtils.isNotBlank(name)) {
			for (Map.Entry<String, String> entry : parameterValues.entrySet()) {
				name = name.replace("${".concat(entry.getKey()).concat("}"), entry.getValue());
			}
		}
		return name;
	}

	/**
	 * Gets the server state.
	 *
	 * @param serverId the server id
	 * @return the server state
	 */
	public static ServerState getServerState(String serverId) {
		AdminPrx adminPrx = IceClientUtil.getAdminPrx();
		ServerState serverState = null;
		try {
			serverState = adminPrx.getServerState(serverId);
		} catch (DeploymentException | NodeUnreachableException | ServerNotExistException e) {
			throw new RuntimeException("getServerState error,serverId:" + serverId, e);
		}
		return serverState;
	}

	/**
	 * Gets the progress id.
	 *
	 * @param serverId the server id
	 * @return the progress id
	 */
	public static int getProgressId(String serverId) {
		AdminPrx adminPrx = IceClientUtil.getAdminPrx();
		try {
			return adminPrx.getServerPid(serverId);
		} catch (DeploymentException | NodeUnreachableException | ServerNotExistException e) {
			throw new RuntimeException("getProgressId error,serverId:" + serverId, e);
		}
	}

	/**
	 * Gets the node info.
	 *
	 * @param node the node
	 * @return the node info
	 */
	public static NodeInfo getNodeInfo(String node) {
		AdminPrx adminPrx = IceClientUtil.getAdminPrx();
		NodeInfo nodeInfo = null;
		try {
			nodeInfo = adminPrx.getNodeInfo(node);
		} catch (NodeNotExistException | NodeUnreachableException e) {
			throw new RuntimeException("getNodeInfo error,node:" + node, e);
		}

		return nodeInfo;
	}

	/**
	 * Gets the adapter info.
	 *
	 * @param adapterId the adapter id
	 * @return the adapter info
	 */
	public static AdapterInfo[] getAdapterInfo(String adapterId) {
		AdminPrx adminPrx = IceClientUtil.getAdminPrx();
		AdapterInfo[] adapterInfos = null;
		try {
			adapterInfos = adminPrx.getAdapterInfo(adapterId);
		} catch (AdapterNotExistException e) {
			throw new RuntimeException("getAdapterInfo error,adapterId:" + adapterId, e);
		}
		return adapterInfos;
	}

	/**
	 * Gets the server template names by app name.
	 *
	 * @param appName the app name
	 * @return the server template names by app name
	 */
	public static Set<String> getServerTemplateNamesByAppName(String appName) {
		AdminPrx adminPrx = IceClientUtil.getAdminPrx();
		ApplicationInfo applicationInfo = null;
		try {
			applicationInfo = adminPrx.getApplicationInfo(appName);
		} catch (ApplicationNotExistException e) {
			throw new RuntimeException("application not exist", e);
		}
		return getServerTemplateNamesByAppName(applicationInfo.descriptor);
	}
	
	
	/**
	 * Gets the server template names by app name.
	 *
	 * @param applicationDescriptor the application descriptor
	 * @return the server template names by app name
	 */
	public static Set<String> getServerTemplateNamesByAppName(ApplicationDescriptor  applicationDescriptor) {
		Map<String, TemplateDescriptor> map = applicationDescriptor.serverTemplates;
		return map.keySet();
	}
	

	/**
	 * Gets the server name by app name and template name.
	 *
	 * @param appName the app name
	 * @param templateName the template name
	 * @return the server name by app name and template name
	 */
	public static String getServerNameByAppNameAndTemplateName(String appName, String templateName) {
		AdminPrx adminPrx = IceClientUtil.getAdminPrx();
		ApplicationInfo applicationInfo = null;
		try {
			applicationInfo = adminPrx.getApplicationInfo(appName);
		} catch (ApplicationNotExistException e) {
			throw new RuntimeException("application not exist", e);
		}
		Map<String, TemplateDescriptor> map = applicationInfo.descriptor.serverTemplates;
		for (Map.Entry<String, TemplateDescriptor> entry : map.entrySet()) {
			if (entry.getKey().equals(templateName)) {
				//return String.valueOf(((JSONObject) JSON.toJSON(entry.getValue().descriptor)).get("id"));
				return  JsonUtils.bean2JsonNode(entry.getValue().descriptor).get("id").asText();
			}
		}
		return null;
	}

	/**
	 * Gets the server template properties.
	 *
	 * @param serverTemplateName the server template name
	 * @param applicationDescriptor the application descriptor
	 * @return the server template properties
	 */
	public static List<PropertyDescriptor> getServerTemplateProperties(String serverTemplateName,
			ApplicationDescriptor applicationDescriptor) {
		if (applicationDescriptor != null) {
			Map<String, TemplateDescriptor> templateDescriptorMap = applicationDescriptor.serverTemplates;
			if (templateDescriptorMap != null) {
				for (Map.Entry<String, TemplateDescriptor> entry : templateDescriptorMap.entrySet()) {
					if (entry.getKey().equals(serverTemplateName)) {
						return entry.getValue().descriptor.propertySet.properties;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Gets the server template properties.
	 *
	 * @param appName the app name
	 * @param serverTemplateName the server template name
	 * @return the server template properties
	 */
	public static List<PropertyDescriptor> getServerTemplateProperties(String appName, String serverTemplateName) {
		return getServerTemplateProperties(serverTemplateName, getApplicationDescriptor(appName));
	}

	/**
	 * Gets the application descriptor.
	 *
	 * @param appName the app name
	 * @return the application descriptor
	 */
	public static ApplicationDescriptor getApplicationDescriptor(String appName) {
		AdminPrx adminPrx = IceClientUtil.getAdminPrx();
		try {
			return adminPrx.getApplicationInfo(appName).descriptor;
		} catch (ApplicationNotExistException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Update server template properties.
	 *
	 * @param serverTemplateName the server template name
	 * @param propertyDescriptorList the property descriptor list
	 * @param applicationDescriptor the application descriptor
	 */
	public static void updateServerTemplateProperties(String serverTemplateName,
		List<PropertyDescriptor> propertyDescriptorList, ApplicationDescriptor applicationDescriptor) {
		for(PropertyDescriptor p:propertyDescriptorList) {
			delServerTemplateProperties(serverTemplateName, applicationDescriptor, p.name);
		}
		addServerTemplateProperties(serverTemplateName, propertyDescriptorList, applicationDescriptor);
	}
	
	
	/**
	 * Adds the server template properties.
	 *
	 * @param serverTemplateName the server template name
	 * @param propertyDescriptorList the property descriptor list
	 * @param applicationDescriptor the application descriptor
	 */
	public static void addServerTemplateProperties(String serverTemplateName,List<PropertyDescriptor> propertyDescriptorList, ApplicationDescriptor applicationDescriptor) {
			Map<String, TemplateDescriptor> templateDescriptorMap = applicationDescriptor.serverTemplates;
			if (templateDescriptorMap != null) {
				for (Map.Entry<String, TemplateDescriptor> entry : templateDescriptorMap.entrySet()) {
					if (entry.getKey().equals(serverTemplateName)) {
						for(PropertyDescriptor p:propertyDescriptorList) {
						entry.getValue().descriptor.propertySet.properties.add(p);
						}
					}
				}
			}
		}
	

	/**
	 * Del server template properties.
	 *
	 * @param serverTemplateName the server template name
	 * @param applicationDescriptor the application descriptor
	 * @param key the key
	 */
	public static void delServerTemplateProperties(String serverTemplateName, ApplicationDescriptor applicationDescriptor,String key) {
		Map<String, TemplateDescriptor> templateDescriptorMap = applicationDescriptor.serverTemplates;
		if (templateDescriptorMap != null) {
			for (Map.Entry<String, TemplateDescriptor> entry : templateDescriptorMap.entrySet()) {
				if (entry.getKey().equals(serverTemplateName)) {
					List<PropertyDescriptor> properties = entry.getValue().descriptor.propertySet.properties;
					for(PropertyDescriptor p:properties) {
						if(p.name.equals(key)) {
							properties.remove(p);
							break;
						}
					}
					break;
				}
			}
		}
	}

	/**
	 * Enable server.
	 *
	 * @param serverId the server id
	 * @param b the b
	 */
	public static void enableServer(String serverId, boolean b) {
		AdminPrx adminPrx = IceClientUtil.getAdminPrx();
		try {
			adminPrx.enableServer(serverId, b);
		} catch (DeploymentException | NodeUnreachableException | ServerNotExistException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Adds the object to replica group.
	 *
	 * @param applicationDescriptor the application descriptor
	 * @param replicaGroupId the replica group id
	 * @param identityList the identity list
	 */
	public static void addObjectToReplicaGroup(ApplicationDescriptor applicationDescriptor, String replicaGroupId,
			List<String> identityList) {
		List<ReplicaGroupDescriptor> replicaGroups = applicationDescriptor.replicaGroups;
		for (ReplicaGroupDescriptor r : replicaGroups) {
			if (r.id.equals(replicaGroupId)) {
				ObjectDescriptor e = null;
				for (String identity : identityList) {
					e = new ObjectDescriptor();
					e.id = new Identity(identity, null);
					r.objects.add(e);
				}
			}
		}
	}

	/**
	 * Restart server.
	 *
	 * @param serverId the server id
	 */
	public static void restartServer(String serverId) {
		stopServer(serverId);
		startServer(serverId);
	}

	/**
	 * Start server.
	 *
	 * @param serverId the server id
	 */
	public static void startServer(String serverId) {
		// TODO Auto-generated method stub
		AdminPrx adminPrx = IceClientUtil.getAdminPrx();
		try {
			adminPrx.startServer(serverId);
		} catch (DeploymentException | NodeUnreachableException | ServerNotExistException | ServerStartException e) {
			throw new RuntimeException("start server error,serverId:".concat(serverId));
		}
	}

	/**
	 * Gets the node descriptor.
	 *
	 * @param applicationDescriptor the application descriptor
	 * @param nodeName the node name
	 * @return the node descriptor
	 */
	public static NodeDescriptor getNodeDescriptor(ApplicationDescriptor applicationDescriptor, String nodeName) {
		Map<String, NodeDescriptor> nodes = applicationDescriptor.nodes;
		return nodes.get(nodeName);
	}

	/**
	 * Gets the server instances.
	 *
	 * @param applicationDescriptor the application descriptor
	 * @param nodeName the node name
	 * @return the server instances
	 */
	public static List<ServerInstanceDescriptor> getServerInstances(ApplicationDescriptor applicationDescriptor,
			String nodeName) {
		return IceAdminUtils.getNodeDescriptor(applicationDescriptor, nodeName).serverInstances;
	}

	/**
	 * Removes the server instance.
	 *
	 * @param applicationDescriptor the application descriptor
	 * @param nodeName the node name
	 * @param serverTemplateName the server template name
	 * @param index the index
	 * @return true, if successful
	 */
	public static boolean removeServerInstance(ApplicationDescriptor applicationDescriptor , String nodeName, String serverTemplateName, String index) {
		List<ServerInstanceDescriptor> serverInstances = IceAdminUtils.getServerInstances(applicationDescriptor,	nodeName);
		for (ServerInstanceDescriptor s : serverInstances) {
			if (s.template.equals(serverTemplateName) && s.parameterValues.get("index").equals(index)) {
				serverInstances.remove(s);
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * Gets the all application descriptor.
	 *
	 * @return the all application descriptor
	 */
	public static List<ApplicationDescriptor> getAllApplicationDescriptor(){
		List<ApplicationDescriptor> list=new ArrayList<>();
		AdminPrx adminPrx = IceClientUtil.getAdminPrx();
		String[] allApplicationNames = adminPrx.getAllApplicationNames();
		for(String applicationName:allApplicationNames) {
			list.add(getApplicationDescriptor(applicationName));
		}
		return list;
	}
	
	
	/**
	 * Gets the all application names.
	 *
	 * @return the all application names
	 */
	public static List<String> getAllApplicationNames(){
		AdminPrx adminPrx = IceClientUtil.getAdminPrx();
		return Arrays.asList(adminPrx.getAllApplicationNames());
	}

}
