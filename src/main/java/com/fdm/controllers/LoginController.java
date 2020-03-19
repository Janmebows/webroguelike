package com.fdm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	
	@GetMapping("/login")
	public String getLogin() {
		return "login";
	}
	
	
	@GetMapping("/login")
	public String processLogin() {
		return "home";
	}
}

