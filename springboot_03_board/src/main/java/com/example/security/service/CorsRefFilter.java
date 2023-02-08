package com.example.security.service;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/*
 * 실행순서          상수명						   실제값
 *   1    Ordered.HIGHEST_PRECENDENCE      -2147483648
 *   2	  Ordered.LOWEST_PRECENDENCE        2147483648
 * 
 * ================================================
 *   1    Ordered.HIGHEST_PRECENDENCE      -2147483648
 *   2	  Ordered.HIGHEST_PRECENDENCE + 1  -2147483647
 *   3    Ordered.HIGHEST_PRECENDENCE + 2  -2147483646
 * 값이 낮을 수록 제일 먼저 실행된다.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE) // 제일 먼저 실행된다. 
public class CorsRefFilter implements Filter {

	// 컨트롤러에 접근하기 전 필터를 거쳐서 컨트롤러에 접근한다.
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;

		// origin에 대해 접근을 허용한다는 응답
		response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
		
		response.setHeader("Access-Control-Allow-Credentials", "true");
		
		response.setHeader("Access-Control-Allow-Methods",
				"ACL, CANCELUPLOAD, CHECKIN, CHECKOUT, COPY, DELETE, GET, HEAD, LOCK, MKCALENDAR, MKCOL, MOVE, OPTIONS, POST, PROPFIND, PROPPATCH, PUT, REPORT, SEARCH, UNCHECKOUT, UNLOCK, UPDATE, VERSION-CONTROL");

		response.setHeader("Access-Control-Max-Age", "3600");
		
		response.setHeader("Access-Control-Allow-Headers",
				"Origin, X-Requested-With, Content-Type, Accept, Key, Authorization");

		if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			chain.doFilter(req, res);
		}

	} // end doFilter()
	
	// 필터가 생성 되었을 때 처음 실행되는 것
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		Filter.super.init(filterConfig);
	} // end init()
	
	// 종료가 되기 직전 실행되는 것
	@Override
	public void destroy() {
		Filter.super.destroy();
	} // end destroy()

}
