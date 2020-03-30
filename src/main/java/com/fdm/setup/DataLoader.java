package com.fdm.setup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.fdm.dal.AccountRepository;


@Component
public class DataLoader implements ApplicationRunner {

	@Autowired
	AccountRepository accountRepo;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		

	}
}
