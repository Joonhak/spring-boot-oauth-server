package io.joonhak.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor // Must be deleted after test
public class Account implements Serializable {
	
	@Id
	private String username;
	private String password;
	private String name;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private List<Role> roles;
	
}
