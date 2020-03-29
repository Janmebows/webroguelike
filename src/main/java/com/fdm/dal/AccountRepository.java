package com.fdm.dal;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fdm.model.Account;


@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {
	
	List<Account> findByUsername(String username);

	List<Account> findByUsernameAndPassword(String username, String password);
}
