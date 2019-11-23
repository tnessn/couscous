/**
 * 
 */
package com.github.tnessn.couscous.lang.util;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.tnessn.couscous.lang.util.JsonUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class JsonUtilTest.
 *
 * @author huangjinfeng
 * @Description TODO
 */
public class JsonUtilTest {
	
	
	/**
	 * Test 1.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void test1() throws IOException {
		
		List<Person> list=new ArrayList<>();
		list.add(new Person("hjf", 123,"tt"));
		list.add(new Person("rrr", 456,"gg"));
		
		String str = JsonUtils.bean2JsonStr(list);
		System.out.println(str);
		
		JsonNode readTree = JsonUtils.mapper.readTree(str);
		
		System.out.println(readTree.getNodeType());
		
		JsonNode jsonNode = readTree.get(0);
		System.out.println(jsonNode.path("name").asText());
		
		
		
	}


}
