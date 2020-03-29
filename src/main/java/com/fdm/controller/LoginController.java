package com.fdm.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fdm.dal.AccountRepository;
import com.fdm.model.Account;

@Controller
public class LoginController {

	@Autowired
	AccountRepository accountRepository;

	@GetMapping("/login")
	public String getLogin(HttpSession session, Model model) {
		if (session.getAttribute("user") != null)
			return "home";
		model.addAttribute("account", new Account());
		return "login";
	}

	@PostMapping("/login")
	public String processLogin(HttpSession session, @ModelAttribute Account account) {
		System.out.println("account=" + account);
		List<Account> users = accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
		if (users.isEmpty())
			return "loginFailed";
		else {
			if (users.size() > 1)
				return "loginFailed";
			else {
				Account currentAccount = users.get(0);

				session.setAttribute("account", currentAccount);
				return "home";
			}
		}
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String getLogout(HttpSession session) {

		session.invalidate();
		return "index";
	}
}
