package io.joonhak.filter;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		log.info(" CORS FILTER INIT... ");
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		var req = (HttpServletRequest) request;
		var res = (HttpServletResponse) response;
		res.setHeader("Access-Control-Allow-Origin", "*");
		res.setHeader("Access-Control-Allow-Methods", "*");
		res.setHeader("Access-Control-Allow-Headers", "*");
		res.setHeader("Access-Control-Max-Age", "3600");
		if (!req.getMethod().equals("OPTIONS")) {
			chain.doFilter(req, res);
		}
	}
	
	@Override
	public void destroy() {
		log.info(" CORS FILTER DESTROY... ");
	}
	
}
