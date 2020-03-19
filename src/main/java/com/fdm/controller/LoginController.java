package com.fdm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.fdm.dal.UserRepository;
import com.fdm.model.User;

@Controller
public class LoginController {
	
	@Autowired
	UserRepository userRepository;
	
	
	@GetMapping("/login")
	public String getLogin(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}
	
	
	@PostMapping("/login")
	public String processLogin(User user) {
		System.out.println("user=" + user);
		//userRepository.save(user);
		List<User> users = userRepository.findByUsername(user.getUsername());
		for (User u : users) {
			System.out.println(u);
		}
		return "home";
	}
}

