package com.gns4.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manager_agent")
public class ManagerAgentController {
	@GetMapping("/bruv")
	public String test() {
		return "Yup, this works!";
	}
}
