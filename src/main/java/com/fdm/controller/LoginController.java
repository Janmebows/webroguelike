package com.fdm.controller;

import java.util.List;
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

	/**
	 * @param account: Placeholder account from form.
	 * @return Account: Matched account.
	 */
	@PostMapping("/login")
	public Account postLogin(@RequestBody Account account) {
		if (account == null) {
            return null;
        }
        // EMPTY FIELDS
        if (account.getUsername() == null || account.getUsername().equals("")) {
            return null;

        } else if (account.getPassword() == null || account.getPassword().equals("")) {
            return null;
        }
		
		System.out.println("Testing");
		List<Account> _account = accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
		System.out.println("/login called with account:" + _account);
		if (_account.isEmpty()) {
			return null;
		} else {
			return _account.get(0);
		}
	}
}
