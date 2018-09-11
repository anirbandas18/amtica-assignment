package com.toab.app.signup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping("/")
	public String getIndex() {
		//return "index.html";
		return "index";
	}
	
	/*@GetMapping("/home")
	public String getDashboard() {
		return "views/dashboard";
		//return "signup_form.html";
	}*/

}
