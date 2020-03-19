package com.fdm.dal;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fdm.model.User;


@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
	
	List<User> findByUsername(String username);
}
