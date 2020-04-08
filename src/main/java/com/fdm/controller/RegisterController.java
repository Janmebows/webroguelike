package com.fdm.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fdm.dal.AccountRepository;
import com.fdm.dal.PlayerCharacterRepository;
import com.fdm.model.Account;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class RegisterController {
	static final String ERROR_MESSAGE = "errorMessage";
	public static final String PASSWORDS_MUST_MATCH = "Passwords must match!";
	public static final String USERNAME_FIELD_CANNOT_BE_EMPTY = "Username field cannot be empty";
	public static final String CONFIRM_PASSWORD_REQUIRED = "Confirm password field cannot be empty";
	public static final String PASSWORD_REQUIRED = "Password field cannot be empty";
	public static final String USERNAME_TAKEN = "Username is taken";
	public static final String USER_WAS_NULL = "User was null";

	@Autowired
	AccountRepository accountRepository;
	@Autowired
	PlayerCharacterRepository pCharRepo;
	protected transient static Logger logger = Logger.getLogger("RegisterLogger");
	@GetMapping("/register")
	public String getRegister() {
		logger.trace("Someone hit register page");
		return "register";
	}

	@PostMapping("/account")
    public Account postAccount(@RequestBody Account account) {
        if (account == null) {
    		logger.warn("Failed registration");
            return null;
        }
        // EMPTY FIELDS
        if (account.getUsername() == null || account.getUsername().equals("")) {
    		logger.warn("Failed registration due to username");
    		return null;

        } else if (account.getPassword() == null || account.getPassword().equals("")) {
    		logger.warn("Failed registration due to password");
            return null;

        }

        // DUPLICATES
        else if (!accountRepository.findByUsername(account.getUsername()).isEmpty()) {
    		logger.warn("Failed registration as username is already in use");
            return null;
        } else {
            Account _account = new Account(account.getUsername(), account.getPassword());
            logger.info("New account was made for user "+ _account.getUsername());
            accountRepository.save(_account);
            return _account;
        }

    }
}
