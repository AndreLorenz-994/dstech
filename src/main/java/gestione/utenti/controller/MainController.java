package gestione.utenti.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import gestione.utenti.model.User;
import gestione.utenti.service.UserService;

@Controller
public class MainController {
	
	@Autowired
	UserService userService;

	@GetMapping("/registration")
	public String goToRegister() {
		return "registration";
	}		
	
	@PostMapping(path = "/registration") 
	public String addUser(@RequestParam String username, 
			@RequestParam String password, 
			@RequestParam String email, 
			@RequestParam String dateOfBirth) {
		LocalDate date = LocalDate.parse(dateOfBirth);
		User user = new User(username, email, password, date);
		userService.add(user);
		return "registration";
	}
}
