package com.joonhak.entity.account;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AccountDetails extends User {
	
	private static final String ROLE_PREFIX = "ROLE_";
	
	public AccountDetails(Account account) {
		super(account.getUsername(), account.getPassword(), getAuthorities(account.getRoles()));
	}
	
	private static Collection<? extends GrantedAuthority> getAuthorities(List<Role> roles) {
		var authorities = new ArrayList<GrantedAuthority>();
		roles.forEach( r -> authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + r.getName())) );
		return authorities;
	}
}
