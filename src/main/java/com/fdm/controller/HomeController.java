package com.fdm.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@GetMapping({ "/", "/home" })
	public String getIndex(HttpSession session) {
		if (session.getAttribute("user") != null) {
			return "home";
		}
		return "index";
	}
}
