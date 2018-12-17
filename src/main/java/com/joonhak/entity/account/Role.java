package com.joonhak.entity.account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Role implements Serializable {
	
	@Id @GeneratedValue
	private Long id;
	private String name;
	
	public Role(String name) {
		this.name = name;
	}
	
}
