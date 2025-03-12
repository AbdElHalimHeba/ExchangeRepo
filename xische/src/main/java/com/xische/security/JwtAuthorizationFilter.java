package com.xische.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.xische.constant.SecurityConstant;
import com.xische.util.JwtUtil;

import io.netty.handler.codec.http.HttpMethod;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
		
	@Autowired
    private UserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader = request.getHeader(SecurityConstant.AUTH);

		if(authHeader != null && authHeader.startsWith(SecurityConstant.BEARER)) {
			String token = authHeader.substring(7);
			String username = JwtUtil.parseJwtSubject(token);

			if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);

				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());

				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				
			}
		}
		filterChain.doFilter(request, response);
	}
	
	@Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return request.getServletPath().equals(SecurityConstant.LOGIN) && request.getMethod().equals(HttpMethod.POST.name());
    }
	
}