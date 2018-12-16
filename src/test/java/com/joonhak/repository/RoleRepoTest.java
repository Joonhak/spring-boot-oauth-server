package com.joonhak.repository;

import com.joonhak.entity.account.Role;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleRepoTest {
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Test
	public void roleSaveTest() {
		final var roleGuest = new Role("BASIC");
		final var roleAdmin = new Role("ADMIN");
		final var result = roleRepository.saveAll( Arrays.asList(roleGuest, roleAdmin) );
		log.info("Saved roles : {}", result);
	}
	
	@Test
	public void roleFindTest() {
		final var roles = roleRepository.findAll();
		log.info("Found roles : {}", roles);
	}
	
}
