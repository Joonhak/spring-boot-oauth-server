package io.joonhak.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

/**
 * Configuration Authorization Server.
 * For in production, Need more.
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	
	@Autowired
	private DataSource dataSource;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security
				.tokenKeyAccess("permitAll()")
				.checkTokenAccess("isAuthenticated()");
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients /*.jdbc(dataSource)*/
				.inMemory()
				.withClient("client")
				.secret( passwordEncoder.encode("passwd"))
				.authorizedGrantTypes(
						"password", "authorization_code", "refresh_token", "implicit"
				)
				.scopes("read", "write")
				.accessTokenValiditySeconds(10*60)
				.refreshTokenValiditySeconds(60*60);
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(new JdbcTokenStore(dataSource))
				.authenticationManager(authenticationManager);
	}
	
}
