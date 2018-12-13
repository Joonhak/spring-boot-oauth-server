package com.joonhak.config;

import com.joonhak.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * Configuration Authorization Server.
 * For in production, Need more.
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	
	/**
	 * {@link WebSecurityConfig#tokenStore()}
	 */
	@Autowired
	private TokenStore tokenStore;
	
	/**
	 * This Object is at WebSecurityConfigurerAdapter,
	 * But it is not @Bean, so have to overriding
	 * {@link WebSecurityConfig#authenticationManager()}
	 */
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AccountService accountService;
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		super.configure(security);
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients
				.inMemory()
				.withClient("client")
				.secret( passwordEncoder.encode("passwd"))
				.authorizedGrantTypes(
						"password", "authorization_code", "refresh_token", "implicit"
				)
				.scopes("read")
				.accessTokenValiditySeconds(10*60)
				.refreshTokenValiditySeconds(60*60);
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore)
				.authenticationManager(authenticationManager)
				.userDetailsService(accountService);
	}
	
}
