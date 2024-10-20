package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.ArrayList;
import java.util.List;


@Controller
public class HelloController {
	@GetMapping(value = "/")
	public String printWelcome(ModelMap model) {
		List<String> messages = new ArrayList<>();
		messages.add("I'm a Spring Boot Starter 3.1.5 version by 19th of oct'23");
		messages.add("And I can represent you a Spring Boot Security application with optional user roles!");
		messages.add("Please, register users before login with the buttons up here...");
		messages.add("The first user will be assigned the DB administrator -)");
		messages.add("All the rest being registered from Welcome page persons will be assigned users");
		messages.add("And only DB administrator is authorized to assign new admins on the admin page");
		messages.add("All admins are authorized to assign users and admins on their admin page");
		model.addAttribute("messages", messages);
		return "index";
	}
}