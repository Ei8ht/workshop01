package com.icd.wksh.securities;

import com.google.gson.Gson;
import com.icd.wksh.commons.Constant;
import com.icd.wksh.commons.Response;
import com.icd.wksh.services.JwtUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	private static final Logger log = LoggerFactory.getLogger(JwtRequestFilter.class);

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private Gson gson;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		log.debug("JwtRequestFilter: doFilterInternal");
		final String requestTokenHeader = request.getHeader("Authorization");
		final String websocketToken = request.getParameter("access_token");
		String username = null;
		String jwtToken = null;
		// JWT Token is in the form "Bearer token". Remove Bearer word and get
		// only the Token
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ") || request.getRequestURI().contains("/book-websocket") && !StringUtils.isEmpty(websocketToken))  {

			if(!StringUtils.isEmpty(requestTokenHeader)){
				jwtToken = requestTokenHeader.substring(7);
			}else if(!StringUtils.isEmpty(websocketToken)){
				jwtToken = websocketToken;
			}


			try {
//				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//				String body = gson.toJson(Response.fail("Test"));
//				response.getOutputStream().write(body.getBytes());
				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
			} catch (IllegalArgumentException e) {
				log.error("Unable to get JWT Token");
//				throw new RuntimeException("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				log.error("JWT Token has expired");
//				throw new RuntimeException("Unable to get JWT Token");
			}
		} else {
			logger.warn("JWT Token does not begin with Bearer String");
			SecurityContextHolder.getContext().setAuthentication(null);
		}

		// Once we get the token validate it.
		if (username != null) {
			UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
			// if token is valid configure Spring Security to manually set
			if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				// After setting the Authentication in the context, we specify
				// that the current user is authenticated. So it passes the
				// Spring Security Configurations successfully.
				request.setAttribute(Constant.USERNAME, username);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} else {
				log.error("Invalid token");
				SecurityContextHolder.getContext().setAuthentication(null);
			}
		}
		chain.doFilter(request, response);
	}

}