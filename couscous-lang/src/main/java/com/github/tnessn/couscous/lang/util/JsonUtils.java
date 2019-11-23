package com.github.tnessn.couscous.lang.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

// TODO: Auto-generated Javadoc
/**
 * JacksonUtil.
 *
 * @author huangjinfeng
 */
public class JsonUtils {

	/** The mapper. */
	public static ObjectMapper mapper = new ObjectMapper();

	/**
	 * Bean 2 json bytes.
	 *
	 * @param obj the obj
	 * @return the byte[]
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static byte[] bean2JsonBytes (Object obj) throws IOException {
		return mapper.writeValueAsBytes(obj);
	}
	
	/**
	 * Bean 2 json file.
	 *
	 * @param obj the obj
	 * @param filePath the file path
	 * @return the file
	 */
	public static File bean2JsonFile (Object obj,String filePath) {
		File file=new  File(filePath);
		try {
			mapper.writeValue(file, obj);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return file;
	}
	
	/**
	 * Bean 2 json str.
	 *
	 * @param obj the obj
	 * @return the string
	 */
	public static String bean2JsonStr(Object obj)  {
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
	

	/**
	 * Json 2 bean.
	 *
	 * @param <T> the generic type
	 * @param jsonStr the json str
	 * @param objClass the obj class
	 * @return the t
	 */
	public static <T> T json2Bean(String jsonStr, Class<T> objClass) {
		try {
			return mapper.readValue(jsonStr, objClass);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Json 2 bean.
	 *
	 * @param <T> the generic type
	 * @param jsonFile the json file
	 * @param objClass the obj class
	 * @return the t
	 */
	public static <T> T json2Bean(File jsonFile, Class<T> objClass) {
		try {
			return mapper.readValue(jsonFile, objClass);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Json 2 bean.
	 *
	 * @param <T> the generic type
	 * @param jsonUrl the json url
	 * @param objClass the obj class
	 * @return the t
	 */
	public static <T> T json2Bean(URL jsonUrl, Class<T> objClass) {
		try {
			return mapper.readValue(jsonUrl, objClass);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Bean 2 json node.
	 *
	 * @param obj the obj
	 * @return the json node
	 */
	public static JsonNode bean2JsonNode (Object obj) {
		String str = bean2JsonStr(obj);
		try {
			return mapper.readTree(str);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
}
