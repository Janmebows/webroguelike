package com.fdm.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fdm.dal.*;
import com.fdm.model.*;

/**
 * @author Andrew
 * @author Ian
 *
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class LoginController {

	@Autowired
	AccountRepository accountRepository;
	protected transient static Logger logger = Logger.getLogger("LoginLogger");

	/**
	 * @param account: Placeholder account from form.
	 * @return Account: Matched account.
	 */
	@PostMapping("/login")
	public Account postLogin(@RequestBody Account account) {
		if (account == null) {
			logger.warn("Failed login");
            return null;
        }
        // EMPTY FIELDS
        if (account.getUsername() == null || account.getUsername().equals("")) {
			logger.warn("Failed login - due to empty username");
            return null;

        } else if (account.getPassword() == null || account.getPassword().equals("")) {
			logger.warn("Failed login - due to empty password");
            return null;
        }
		
		List<Account> _account = accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
		logger.trace("/login called with account:" + _account);
		if (_account.isEmpty()) {
			logger.warn("Account did not exist");
			return null;
		} else {
			logger.warn("Login was successful");
			return _account.get(0);
		}
	}
}
