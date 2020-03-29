package com.fdm.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.fdm.dal.AccountRepository;
import com.fdm.model.Account;


@Controller
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

	@GetMapping("/register")
	public String getRegister(HttpSession session, Model model) {

		model.addAttribute("account", new Account());
		return "register";
	}

	@PostMapping("/register")
	public String processRegister(HttpSession session, Model model, Account account) {
		if (account == null) {
			System.out.println("Failed login - " + USER_WAS_NULL);
			session.setAttribute(ERROR_MESSAGE, USER_WAS_NULL);
			return "registerFailed";
		}
		// EMPTY FIELDS
		if (account.getUsername() == null || account.getUsername().equals("")) {
			System.out.println("Failed login - " + USERNAME_FIELD_CANNOT_BE_EMPTY);
			session.setAttribute(ERROR_MESSAGE, USERNAME_FIELD_CANNOT_BE_EMPTY);
			return "registerFailed";

		} else if (account.getPassword() == null || account.getPassword().equals("")) {
			System.out.println("Failed login - " + PASSWORD_REQUIRED);
			session.setAttribute(ERROR_MESSAGE, PASSWORD_REQUIRED);
			return "registerFailed";

		} else if (account.getConfirmPassword() == null || account.getConfirmPassword().equals("")) {
			System.out.println("Failed login - " + CONFIRM_PASSWORD_REQUIRED);
			session.setAttribute(ERROR_MESSAGE, CONFIRM_PASSWORD_REQUIRED);
			return "registerFailed";

		} else if (!account.getPassword().equals(account.getConfirmPassword())) {
			System.out.println("Failed login - " + PASSWORDS_MUST_MATCH);
			session.setAttribute(ERROR_MESSAGE, PASSWORDS_MUST_MATCH);
			return "registerFailed";

		}

		// DUPLICATES
		else if (!accountRepository.findByUsername(account.getUsername()).isEmpty()) {
			// if any were found
			System.out.println("Failed login - " + USERNAME_TAKEN);
			session.setAttribute(ERROR_MESSAGE, USERNAME_TAKEN);
			return "registerFailed";
		} else {
			account = accountRepository.save(account);
			session.setAttribute("user", account);
			return "home";
		}
	}
}
