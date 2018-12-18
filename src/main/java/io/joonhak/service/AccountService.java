package io.joonhak.service;

import io.joonhak.entity.Account;
import io.joonhak.entity.AccountDetails;
import io.joonhak.entity.Role;
import io.joonhak.repository.AccountRepository;
import io.joonhak.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Slf4j
@Service
public class AccountService implements UserDetailsService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AccountRepository accountRepo;
	@Autowired
	private RoleRepository roleRepo;
	
	/*
	// Since Servlet 5.0, can autowired like below ( And Intellij && Spring team recommend this way )
	private final AccountRepository repo;
	private final PasswordEncoder passwordEncoder;
	
	public AccountService(AccountRepository repo, PasswordEncoder passwordEncoder) {
		this.repo = repo;
		this.passwordEncode = passwordEncoder;
	}
    */
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return accountRepo.findById(username)
				.map(AccountDetails::new)
				.orElseThrow( () -> new UsernameNotFoundException("CAN NOT FIND USER, USERNAME : " + username) );
	}
	
	private Account save(Account account) {
		account.setPassword( passwordEncoder.encode(account.getPassword()) );
		return accountRepo.save(account);
	}
	
	// Must be deleted after test
	@PostConstruct
	public void init() {
		accountRepo.findById("user")
				.ifPresentOrElse( a -> log.info("ACCOUNT : {}", a),
						() -> {
							final var role = roleRepo.save( new Role(null, "ADMIN") );
							final var account = this.save( new Account("user", "pass", "admin", Arrays.asList(role)) );
							log.info("SAVE DEFAULT ACCOUNT.. {}", account);
						});
	}
	
}