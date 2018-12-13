package com.joonhak.repository;

import com.joonhak.entity.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
	Account findByUsername(String username);
}
