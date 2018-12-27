package io.joonhak.config;

import io.joonhak.service.AccountService;
import io.joonhak.service.OAuthClientService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.authserver.AuthorizationServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.authserver.OAuth2AuthorizationServerConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * Configuration Authorization Server.
 * For in production, Need more.
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends OAuth2AuthorizationServerConfiguration {
	
	@Autowired
	private AccountService accountService;
	@Autowired
	private OAuthClientService oAuthClientService;
	
	public AuthorizationServerConfig(BaseClientDetails details, AuthenticationConfiguration authenticationConfiguration, ObjectProvider<TokenStore> tokenStore, ObjectProvider<AccessTokenConverter> tokenConverter, AuthorizationServerProperties properties) throws Exception {
		super(details, authenticationConfiguration, tokenStore, tokenConverter, properties);
	}
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) {
		security
				.tokenKeyAccess("permitAll()")
				.checkTokenAccess("isAuthenticated()");
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.withClientDetails(oAuthClientService); // Manage client use database.
				/*
				.inMemory()
				.withClient("client")
				.secret( "{noop}passwd" )
				.authorizedGrantTypes(
						"password", "client_credentials", "authorization_code", "refresh_token", "implicit"
				)
				.scopes("read", "write")
				.accessTokenValiditySeconds(10*60)
				.refreshTokenValiditySeconds(60*60);
				*/
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		super.configure(endpoints);
		endpoints.userDetailsService(accountService);
	}
	
}
