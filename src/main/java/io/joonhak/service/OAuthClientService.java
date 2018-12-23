package io.joonhak.service;

import io.joonhak.entity.OAuthClient;
import io.joonhak.repository.OAuthClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class OAuthClientService implements ClientDetailsService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private OAuthClientRepository clientRepo;
	
	@Override
	public BaseClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		return clientRepo.findById(clientId)
				.map(BaseClientDetails::new)
				.orElseThrow( () -> new NoSuchClientException("CAN NOT FIND CLIENT, CLIENT ID : " + clientId) );
	}
	
	public OAuthClient save(OAuthClient oAuthClient) {
		if ( oAuthClient.getClientSecret() != null ) {
			oAuthClient.setClientSecret( passwordEncoder.encode( oAuthClient.getClientSecret() ) );
		}
		return clientRepo.save(oAuthClient);
	}
	
	@PostConstruct
	public void init() {
		clientRepo.findById("client").ifPresentOrElse(
				c -> log.info("CLIENT : {}", c),
				() -> {
					var client = new OAuthClient(
							"client"
							, null
							, "passwd"
							, null
							, "authorization_code,password,client_credentials,implicit,refresh_token"
							, null
							, "ROLE_ADMIN"
							, 36000
							, 2592000
							, null
							, null
					);
					
					log.info("SAVE DEFAULT CLIENT.. {}", this.save(client));
				}
		);
	}
	
}
