package com.fdm.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fdm.dal.*;
import com.fdm.model.*;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class LoginController {

	@Autowired
	AccountRepository accountRepository;

	@PostMapping("/login")
	public Account postLogin(@RequestBody Account account) {
		System.out.println("/login called with account:" +account);
		Account _account = accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword()).get(0);
		return _account;
	}
	/*@GetMapping("/login")
	 * public String getLogin(HttpSession session, Model model) { if
	 * (session.getAttribute("account") != null) return "home";
	 * model.addAttribute("account", new Account()); return "login"; }
	 * 
	 * @PostMapping("/login") public String processLogin(HttpSession
	 * session, @ModelAttribute Account account) { System.out.println("account=" +
	 * account); List<Account> accounts =
	 * accountRepository.findByUsernameAndPassword(account.getUsername(),
	 * account.getPassword()); if (accounts.isEmpty()) return "loginFailed"; else {
	 * if (accounts.size() > 1) return "loginFailed"; else { Account currentAccount
	 * = accounts.get(0);
	 * 
	 * session.setAttribute("account", currentAccount); return "home"; } } }
	 * 
	 */
	
	
	/*
	 * @RequestMapping(value = "/logout", method = RequestMethod.GET) public String
	 * getLogout(HttpSession session) {
	 * 
	 * session.invalidate(); return "index"; }
	 */
}
