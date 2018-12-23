package io.joonhak.entity;

import lombok.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor // Must be deleted after test
public class OAuthClient implements ClientDetails, Serializable {
	
	private static final ObjectMapper mapper = new ObjectMapper();
	
	@Id @Column(nullable = false)
	private String clientId;
	private String resourceIds;
	private String clientSecret;
	private String scope;
	@Column(nullable = false)
	private String authorizedGrantTypes;
	private String registeredRedirectUri;
	private String authorities;
	@Column(nullable = false)
	private Integer accessTokenValiditySeconds;
	@Column(nullable = false)
	private Integer refreshTokenValiditySeconds;
	private String autoApproveScope;
	private String additionalInformation;
	
	@Override
	public String getClientId() {
		return this.clientId;
	}
	
	@Override
	public Set<String> getResourceIds() {
		if ( StringUtils.isEmpty(this.resourceIds) ) return new HashSet<>();
		return StringUtils.commaDelimitedListToSet(this.resourceIds);
	}
	
	@Override
	public boolean isSecretRequired() {
		return !StringUtils.isEmpty(this.clientSecret);
	}
	
	@Override
	public String getClientSecret() {
		return this.clientSecret;
	}
	
	@Override
	public boolean isScoped() {
		return this.getScope().size() > 0;
	}
	
	@Override
	public Set<String> getScope() {
		return StringUtils.commaDelimitedListToSet(this.scope);
	}
	
	@Override
	public Set<String> getAuthorizedGrantTypes() {
		return StringUtils.commaDelimitedListToSet(this.authorizedGrantTypes);
	}
	
	@Override
	public Set<String> getRegisteredRedirectUri() {
		return StringUtils.commaDelimitedListToSet(this.registeredRedirectUri);
	}
	
	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return StringUtils.commaDelimitedListToSet(this.authorities).stream()
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toSet());
	}
	
	@Override
	public Integer getAccessTokenValiditySeconds() {
		return this.accessTokenValiditySeconds;
	}
	
	@Override
	public Integer getRefreshTokenValiditySeconds() {
		return this.refreshTokenValiditySeconds;
	}
	
	public Set<String> getAutoApproveScope() {
		return StringUtils.commaDelimitedListToSet(this.autoApproveScope);
	}
	
	@Override
	public boolean isAutoApprove(String scope) {
		return this.getAutoApproveScope().contains(scope);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Object> getAdditionalInformation() {
		if ( this.additionalInformation != null) {
			try {
				return mapper.readValue(this.additionalInformation, Map.class);
			} catch (IOException e) {
				return new HashMap<>();
			}
		}
		return new HashMap<>();
	}
	
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	public void setResourceIds(Set<String> resourceIds) {
		this.resourceIds = StringUtils.collectionToCommaDelimitedString(resourceIds);
	}
	
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	
	public void setScope(Set<String> scope) {
		this.scope = StringUtils.collectionToCommaDelimitedString(scope);
	}
	
	public void setAuthorizedGrantTypes(Set<String> authorizedGrantTypes) {
		this.authorizedGrantTypes = StringUtils.collectionToCommaDelimitedString(authorizedGrantTypes);
	}
	
	public void setRegisteredRedirectUri(Set<String> registeredRedirectUri) {
		this.registeredRedirectUri = StringUtils.collectionToCommaDelimitedString(registeredRedirectUri);
	}
	
	public void setAuthorities(Set<? extends GrantedAuthority> authorities) {
		this.authorities = StringUtils.collectionToCommaDelimitedString(authorities);
	}
	
	public void setAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
		this.accessTokenValiditySeconds = accessTokenValiditySeconds;
	}
	
	public void setRefreshTokenValiditySeconds(Integer refreshTokenValiditySeconds) {
		this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
	}
	
	public void setAutoApproveScope(Set<String> autoApproveScope) {
		this.autoApproveScope = StringUtils.collectionToCommaDelimitedString(autoApproveScope);
	}
	
	public void setAdditionalInformation(Map<String, Object> additionalInformation) {
		try {
			this.additionalInformation = mapper.writeValueAsString(additionalInformation);
		} catch (IOException e) {
			this.additionalInformation = "";
		}
	}
	
}
