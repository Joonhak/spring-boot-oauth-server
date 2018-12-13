package com.joonhak.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		super.configure(resources);
		// TODO: Find for 'What is resourceId in OAuth2?'
		// resources.resourceId("resource_id").stateless(true);
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			// Because of H2-console
			.headers().frameOptions().disable()
				.and()
			.authorizeRequests()
				// For Register user
				.antMatchers(HttpMethod.POST, "/users/").permitAll()
				.antMatchers("/users/**").authenticated()
				.and()
			.exceptionHandling()
				.accessDeniedHandler(new OAuth2AccessDeniedHandler());
	}
}
