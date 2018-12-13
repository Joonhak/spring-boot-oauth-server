package com.joonhak.controller;

import com.joonhak.entity.account.Account;
import com.joonhak.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private AccountService accountService;
	
	@GetMapping("/ALL")
	public List<Account> allAccounts() {
		return accountService.findAll();
	}
	
	@PostMapping("/")
	public Account create(@RequestBody Account account) {
		return accountService.save(account);
	}
	
	@DeleteMapping("/{id}")
	public String delete(@PathVariable long id) {
		accountService.delete(id);
		return "success";
	}
	
}
