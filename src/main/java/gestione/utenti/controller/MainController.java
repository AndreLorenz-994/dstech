package gestione.utenti.controller;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import gestione.utenti.model.User;
import gestione.utenti.service.MailService;
import gestione.utenti.service.UserService;

@Controller
public class MainController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MailService mailService;	
	
	@RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
	public ModelAndView login(){
	    ModelAndView modelAndView = new ModelAndView();
	    modelAndView.setViewName("login");
	    return modelAndView;
	}
	
	
	@RequestMapping(value="/registration", method = RequestMethod.GET)
	public ModelAndView registration(){
	    ModelAndView modelAndView = new ModelAndView();
	    User user = new User();
	    modelAndView.addObject("user", user);
	    modelAndView.setViewName("registration");
	    return modelAndView;
	}
	
	@PostMapping(path = "/registration")
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) throws MessagingException {
	    ModelAndView modelAndView = new ModelAndView();
	    User userExists = userService.findUserByUserName(user.getUsername());
	    if (userExists != null) {
	        bindingResult
	                .rejectValue("userName", "error.user",
	                    "There is already a user registered with the user name provided");
	}
	if (bindingResult.hasErrors()) {
	    modelAndView.setViewName("registration");
	} else {
	    userService.saveUser(user);
	    mailService.sendMail(user.getEmail(), "Confirm registration", "User has been registered successfully");
	    modelAndView.addObject("successMessage", "User has been registered successfully");
	    modelAndView.addObject("user", new User());
	    modelAndView.setViewName("registration");
	
	    }
	    return modelAndView;
	}
	
	@RequestMapping(value="/admin/home", method = RequestMethod.GET)
	public ModelAndView home(){
	    ModelAndView modelAndView = new ModelAndView();
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    User user = userService.findUserByUserName(auth.getName());
	    modelAndView.addObject("id", user.getId());
	    modelAndView.addObject("userName", user.getUsername());
	    modelAndView.addObject("birthDay", user.getDateOfBirth());
		modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
		modelAndView.setViewName("admin/admin-home");
	    return modelAndView;
	}
	
	@RequestMapping(value="/admin/modify-username", method = RequestMethod.POST)
	public ModelAndView getParameter() {
	    ModelAndView modelAndView = new ModelAndView();
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    User user = userService.findUserByUserName(auth.getName());
	    modelAndView.addObject("user", user);

		modelAndView.setViewName("admin/modify-username");
	    return modelAndView;		
		
/*
		model.addAttribute("id", user.getId());
		model.addAttribute("username", user.getUsername());
		
		return "admin/modify-username"; */
	}	
	
	@RequestMapping(value="/admin/home", method = RequestMethod.POST)
	public ModelAndView updateUsername () {
	    ModelAndView modelAndView = new ModelAndView();
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    User user = userService.findUserByUserName(auth.getName());
	    User updateUser = userService.updateUsername(user, user.getId());
	    
		modelAndView.setViewName("admin/admin-home");
	    return modelAndView;
		/*
		User updUser = userService.updateUsername(user, user.getId());		
		if(updUser == null) return "No such user";
		return "admin/modify-username"; */
	}  
	
}
