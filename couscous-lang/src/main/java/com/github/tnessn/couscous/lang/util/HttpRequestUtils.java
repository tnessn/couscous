package com.github.tnessn.couscous.lang.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class HttpRequestUtils.
 *
 * @author huangjinfeng
 */
public class HttpRequestUtils {

	/**
	 * Checks if is ajax.
	 *
	 * @param request the request
	 * @return true, if is ajax
	 */
	public static boolean isAjax(ServletRequest request) {
		String header = ((HttpServletRequest) request).getHeader("X-Requested-With");
		boolean isAjax = "XMLHttpRequest".equals(header) ? true : false;
		return isAjax;
	}

	/**
	 * Checks if is json content.
	 *
	 * @param request the request
	 * @return true, if is json content
	 */
	public static boolean isJsonContent(ServletRequest request) {
		String contentType = ((HttpServletRequest) request).getHeader("Content-type");
		if (StringUtils.isNotBlank(contentType)) {
			if (StringUtils.indexOfIgnoreCase(contentType, "application/json") >= 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if is html.
	 *
	 * @param request the request
	 * @return true, if is html
	 */
	public static boolean isHTML(ServletRequest request) {
		HttpServletRequest req = (HttpServletRequest) request;
		String servletPath = req.getServletPath();
		String contextType = req.getContentType();
		String method = req.getMethod();
		return isHTML(servletPath, method, contextType);
	}

	/**
	 * Checks if is html.
	 *
	 * @param servletPath the servlet path
	 * @param method the method
	 * @param contextType the context type
	 * @return true, if is html
	 */
	public static boolean isHTML(String servletPath, String method, String contextType) {
		String urlExtension = StringUtils.substringAfterLast(servletPath, ".");
		return ("HTML".equalsIgnoreCase(urlExtension) || "HTM".equalsIgnoreCase(urlExtension))
				&& "GET".equalsIgnoreCase(method) && StringUtils.containsIgnoreCase(contextType, "text/html");
	}

	/**
	 * Read json req.
	 *
	 * @param request the request
	 * @return the string
	 */
	public static String readJsonReq(ServletRequest request) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
			StringBuilder sb = new StringBuilder();
			String temp;
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
			}
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return "";
	}

	/**
	 * Write json resp.
	 *
	 * @param obj the obj
	 * @param response the response
	 */
	public static void writeJsonResp(Object obj, ServletResponse response) {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.setCharacterEncoding("UTF-8");
		httpResponse.setContentType("application/json; charset=utf-8");
		httpResponse.setStatus(200);

		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(JsonUtils.bean2JsonStr(obj));
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	/**
	 * Write json resp.
	 *
	 * @param obj the obj
	 * @param response the response
	 * @param httpStatusCode the http status code
	 */
	public static void writeJsonResp(Object obj, ServletResponse response, int httpStatusCode) {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.setCharacterEncoding("UTF-8");
		httpResponse.setContentType("application/json; charset=utf-8");
		httpResponse.setStatus(httpStatusCode);

		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(JsonUtils.bean2JsonStr(obj));
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		System.out.println(isHTML("/idpstat.html", "get", "text/html;charset=utf-8"));
		System.out.println(isHTML("/idpstat.htm", "get", "text/html;charset=utf-8"));
		System.out.println(isHTML("/idpstat.html", "get", "text/html;charset=utf-8"));
	}

}
