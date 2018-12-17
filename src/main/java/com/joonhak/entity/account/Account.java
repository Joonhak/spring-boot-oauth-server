package com.joonhak.entity.account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Account implements Serializable {
	
	@Id
	private String username;
	private String password;
	private String name;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private List<Role> roles;
	
	// Must be deleted after test
	public Account(String username, String password, Role role) {
		this.username = username;
		this.password = password;
		this.roles = new ArrayList<>();
		this.roles.add(role);
	}
	
}
