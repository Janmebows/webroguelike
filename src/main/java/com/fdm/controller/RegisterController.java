package com.fdm.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fdm.dal.AccountRepository;
import com.fdm.dal.PlayerCharacterRepository;
import com.fdm.model.Account;
import com.fdm.model.PlayerCharacter;

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

	@GetMapping("/register")
	public String getRegister() {
		System.out.println("GET REGISTER");
		return "register";
	}

	@PostMapping("/account")
    public Account postAccount(@RequestBody Account account) {
        if (account == null) {
            return null;
        }
        // EMPTY FIELDS
        if (account.getUsername() == null || account.getUsername().equals("")) {
            return null;

        } else if (account.getPassword() == null || account.getPassword().equals("")) {
            return null;
//
//    } else if (account.getConfirmPassword() == null || account.getConfirmPassword().equals("")) {
//        return null;
//
//    } else if (!account.getPassword().equals(account.getConfirmPassword())) {
//        return null;

        }

        // DUPLICATES
        else if (!accountRepository.findByUsername(account.getUsername()).isEmpty()) {
            return null;
        } else {
            Account _account = new Account(account.getUsername(), account.getPassword());
//            PlayerCharacter plc = new PlayerCharacter(_account.getUsername());
//            _account.setPlayerCharacter(plc);
//            pCharRepo.save(plc);
            accountRepository.save(_account);
            return _account;
        }

    }

//	@PostMapping("/register")
//	public String processRegister(HttpSession session, Model model, Account account) {
//		if (account == null) {
//			System.out.println("Failed login - " + USER_WAS_NULL);
//			session.setAttribute(ERROR_MESSAGE, USER_WAS_NULL);
//			return "registerFailed";
//		}
//		// EMPTY FIELDS
//		if (account.getUsername() == null || account.getUsername().equals("")) {
//			System.out.println("Failed login - " + USERNAME_FIELD_CANNOT_BE_EMPTY);
//			session.setAttribute(ERROR_MESSAGE, USERNAME_FIELD_CANNOT_BE_EMPTY);
//			return "registerFailed";
//
//		} else if (account.getPassword() == null || account.getPassword().equals("")) {
//			System.out.println("Failed login - " + PASSWORD_REQUIRED);
//			session.setAttribute(ERROR_MESSAGE, PASSWORD_REQUIRED);
//			return "registerFailed";
//
//		} else if (account.getConfirmPassword() == null || account.getConfirmPassword().equals("")) {
//			System.out.println("Failed login - " + CONFIRM_PASSWORD_REQUIRED);
//			session.setAttribute(ERROR_MESSAGE, CONFIRM_PASSWORD_REQUIRED);
//			return "registerFailed";
//
//		} else if (!account.getPassword().equals(account.getConfirmPassword())) {
//			System.out.println("Failed login - " + PASSWORDS_MUST_MATCH);
//			session.setAttribute(ERROR_MESSAGE, PASSWORDS_MUST_MATCH);
//			return "registerFailed";
//
//		}
//
//		// DUPLICATES
//		else if (!accountRepository.findByUsername(account.getUsername()).isEmpty()) {
//			// if any were found
//			System.out.println("Failed login - " + USERNAME_TAKEN);
//			session.setAttribute(ERROR_MESSAGE, USERNAME_TAKEN);
//			return "registerFailed";
//		} else {
//			account = accountRepository.save(account);
//			session.setAttribute("user", account);
//			System.out.println(account);
//			return "home";
//		}
//	}
}
