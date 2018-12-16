package com.joonhak.service;

import com.joonhak.entity.account.Account;
import com.joonhak.entity.account.AccountDetails;
import com.joonhak.entity.account.Role;
import com.joonhak.repository.AccountRepository;
import com.joonhak.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

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
		return Optional.ofNullable(accountRepo.findByUsername(username))
				.map(AccountDetails::new)
				.orElseThrow( () -> new UsernameNotFoundException("Can not find username : " + username) );
	}
	
	public List<Account> findAll() {
		return accountRepo.findAll();
	}
	
	public Account save(Account account) {
		account.setPassword( passwordEncoder.encode(account.getPassword()) );
		return accountRepo.save(account);
	}
	
	public void delete(long id) {
		accountRepo.deleteById(id);
		// or
		// accountRepo.deleteByUsername(String username); <- Need Add method in AccountRepository
	}
	
	// Must be deleted after test
	@PostConstruct
	public void init() {
		var account = accountRepo.findByUsername("user");
		if ( account == null ) {
			var roleGuest = roleRepo.save( new Role("ADMIN") );
			var saved = this.save(new Account("user", "pass", roleGuest));
			log.info("Saved account : {}", saved);
		}
	}
	
}
