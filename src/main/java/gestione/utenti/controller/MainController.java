package gestione.utenti.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping("/registration")
	public String goToRegister() {
		return "registration";
	}		
	
}
