package com.fdm.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.fdm.dal.AccountRepository;
import com.fdm.model.Account;

@Controller
public class ResetPasswordController {
	public static final String PASSWORD_REQUIRED = "Password field cannot be empty";
	static final String PASSWORDS_SHOULD_NOT_MATCH = "Passwords should not match";
	static final String OLD_PASSWORD_IS_WRONG = "Old password is wrong!";
	static final String NO_USER_LOGGED_IN = "No account logged in";
	static final String ERROR_MESSAGE = "errorMessage";
	@Autowired
	AccountRepository accountRepository;

	@GetMapping("/resetPassword")
	public String getResetPassword(HttpSession session, Model model) {

		if (session.getAttribute("account") == null) {
			session.invalidate();
			return "login";

		} else {
			model.addAttribute("oldpassword", new String());
			model.addAttribute("newpassword", new String());

			return "resetPassword";
		}
	}

	@PostMapping("/resetPassword")
	public String processResetPassword(HttpSession session, Model model,
			@ModelAttribute("oldpassword") String oldpassword, @ModelAttribute("newpassword") String newpassword) {

		Account account = (Account) session.getAttribute("account");

		if (account != null) {
			if (oldpassword == null || oldpassword.trim().equals("")) {
				session.setAttribute(ERROR_MESSAGE, PASSWORD_REQUIRED);
				return "resetPasswordFailed";

			} else if (newpassword == null || newpassword.trim().equals("")) {
				session.setAttribute(ERROR_MESSAGE, PASSWORD_REQUIRED);
				return "resetPasswordFailed"; 
			}
			if (!account.getPassword().equals(oldpassword)) {
				// wrong old password
				session.setAttribute(ERROR_MESSAGE, OLD_PASSWORD_IS_WRONG);
				return "resetPasswordFailed";

			} else if (oldpassword.equals(newpassword)) {
				// they shouldn't be the same

				session.setAttribute(ERROR_MESSAGE, PASSWORDS_SHOULD_NOT_MATCH);
				return "resetPasswordFailed";
			} else {
				account.setPassword(newpassword);
				account = accountRepository.save(account);
				session.setAttribute("account", account);
				return "resetPasswordSuccess";
			}
		} else {

			session.setAttribute(ERROR_MESSAGE, NO_USER_LOGGED_IN);
			return "loginFailed";

		}
	}

}
