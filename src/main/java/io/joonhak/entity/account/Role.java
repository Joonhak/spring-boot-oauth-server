package io.joonhak.entity.account;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor // Must be deleted after test
public class Role implements Serializable {
	
	@Id @GeneratedValue
	private Long id;
	private String name;
	
}
