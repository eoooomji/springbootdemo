package com.example.demo.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

// ~Adapter : interface -> class

//public class ViewNameInterceptor extends HandlerInterceptorAdapter {
public class ViewNameInterceptor implements HandlerInterceptor{
	public ViewNameInterceptor() {

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		try {
			System.out.println("request =>" + request);
			String viewName = getViewName(request);
			request.setAttribute("viewName", viewName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

	private String getViewName(HttpServletRequest request) throws Exception {
		String contextPath = request.getContextPath();
		System.out.println("contextPath:" + contextPath);

		// URI정보를 얻고 싶을때 javax.servlet.include.request_uri 을 사용한다.
		String uri = (String) request.getAttribute("javax.servlet.include.request_uri");
		System.out.println("uri=> " + uri); // uti => null
		if (uri == null || uri.trim().equals("")) {
			uri = request.getRequestURI();
			System.out.println("if uri=> " + uri); // member/memberForm.do
		}

		int begin = 0;
		if (!((contextPath == null) || ("".equals(contextPath)))) {
			begin = contextPath.length();
		}

		int end;
		if (uri.indexOf(";") != -1) {
			end = uri.indexOf(";");
		} else if (uri.indexOf("?") != -1) {
			end = uri.indexOf("?");
		} else {
			end = uri.length();
		}

		
		String fileName = uri.substring(begin, end);
		//System.out.println("fileName ::=>" + fileName);
		if (fileName.indexOf(".") != -1) {
			fileName = fileName.substring(0, fileName.lastIndexOf("."));
		}
		if (fileName.lastIndexOf("/") != -1) {
			fileName = fileName.substring(fileName.lastIndexOf("/", 1), fileName.length());
		}
	//	System.out.println("fileName=>" + fileName);
		return fileName;
	}
}
