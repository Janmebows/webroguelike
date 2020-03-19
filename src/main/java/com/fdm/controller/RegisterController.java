package com.fdm.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.fdm.dal.UserRepository;
import com.fdm.model.User;

@Controller
public class RegisterController {

	@Autowired
	UserRepository userRepository;
	@GetMapping("/register")
	public String getRegister(HttpSession session, Model model) {
		if (session.getAttribute("activeUser") != null)
			return "home";
		else {
			model.addAttribute("user",new User());
			return "register";
		}
	}

	@PostMapping("/register")
	public String processRegister(HttpSession session, Model model, User user) {
		if(!userRepository.findByUsername(user.getUsername()).isEmpty()) {
			model.addAttribute("errorMessage","Username is taken");
			return "register";
		}
		else {
			user = userRepository.save(user);
			session.setAttribute("activeUser", user);
			return "home";
		}
	}
}
