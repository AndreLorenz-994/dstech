package gestione.utenti.controller;

import java.util.Date;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import gestione.utenti.model.Image;
import gestione.utenti.model.User;
import gestione.utenti.service.ImageService;
import gestione.utenti.service.MailService;
import gestione.utenti.service.UserService;

@Controller
public class MainController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MailService mailService;	
	
	@Autowired
	private ImageService imageService;		
	
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
	
	@RequestMapping(value="/registration", method = RequestMethod.POST)
	public ModelAndView createNewAdmin(@Valid User user, BindingResult bindingResult) throws MessagingException {
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
		    userService.saveAdmin(user);
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
	    modelAndView.addObject("userName", user.getUsername());
	    modelAndView.addObject("birthDay", user.getDateOfBirth());
		modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
		modelAndView.setViewName("admin/admin-home");
	    return modelAndView;
	}
	
	@RequestMapping(value="/admin/upload", method = RequestMethod.GET)
	public ModelAndView addProfilePicturePage(){
	    ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/admin-upload");
	    return modelAndView;
	}	
	
	@RequestMapping(value="/admin/upload", method = RequestMethod.POST)
	public ModelAndView updatePicture (@RequestParam("file") MultipartFile file) {
		ModelAndView modelAndView = new ModelAndView();
		Image image = imageService.storeFile(file);
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    User user = userService.findUserByUserName(auth.getName());
	    userService.uploadImage(user, image);
		modelAndView.setViewName("admin/admin-upload");
		return modelAndView;
	}	

	@RequestMapping(value="/admin/modify-username", method = RequestMethod.GET)
	public ModelAndView getUsernameParameter() {
	    ModelAndView modelAndView = new ModelAndView();
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    User user = userService.findUserByUserName(auth.getName());
	    modelAndView.addObject("user", user);

		modelAndView.setViewName("admin/modify-username");
	    return modelAndView;		
	}	
	
	@RequestMapping(value="/admin/modify-birthday", method = RequestMethod.GET)
	public ModelAndView getBirthdayParameter() {
	    ModelAndView modelAndView = new ModelAndView();
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    User user = userService.findUserByUserName(auth.getName());
	    modelAndView.addObject("user", user);

		modelAndView.setViewName("admin/modify-birthday");
	    return modelAndView;		
	}		

	@RequestMapping(value="/admin/modify-username", method = RequestMethod.POST)
	public ModelAndView updateUsername (@RequestParam String username) {
	    ModelAndView modelAndView = new ModelAndView();
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    User user = userService.findUserByUserName(auth.getName());
	    User updateUser = userService.updateUsername(user, username);
	    modelAndView.addObject("user", updateUser);    
	    modelAndView.addObject("successUpdate","The username has been update");
		modelAndView.setViewName("admin/modify-username");
	    return modelAndView;
	}  

	
	@RequestMapping(value="/admin/modify-birthday", method = RequestMethod.POST)
	public ModelAndView updateBirthday (@RequestParam Date dateOfBirth) {
	    ModelAndView modelAndView = new ModelAndView();
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    User user = userService.findUserByUserName(auth.getName());
	    User updateUser = userService.updateBirthday(user, dateOfBirth);
	    modelAndView.addObject("user", updateUser);    
	    modelAndView.addObject("successUpdate","The date of birthday has been update");    
	    
		modelAndView.setViewName("/admin/modify-birthday");
	    return modelAndView;
	} 
	

}
