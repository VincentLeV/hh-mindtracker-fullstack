package haagahelia.sp.app.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import haagahelia.sp.app.domain.SignUpForm;
import haagahelia.sp.app.domain.User;
import haagahelia.sp.app.domain.UserRepository;

@Controller
public class UserController {
	@Autowired
    private UserRepository repository; 
	
    @RequestMapping(value = "/signup")
    public String addStudent(Model model) {
    	model.addAttribute("signupform", new SignUpForm());
        return "signup";
    }	
    
    @RequestMapping(value = "/saveuser", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute("signupform") SignUpForm signupForm, BindingResult bindingResult) {
    	
    	// validation errors
    	if (!bindingResult.hasErrors()) {
    		if (signupForm.getPassword().equals(signupForm.getPasswordCheck())) { // check password match		
	    		String pwd = signupForm.getPassword();
		    	BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
		    	String hashPwd = bc.encode(pwd);
	
		    	User newUser = new User();
		    	newUser.setPasswordHash(hashPwd);
		    	newUser.setFullname(signupForm.getFullname());
		    	newUser.setUsername(signupForm.getUsername());
		    	newUser.setRole("USER");
		    	if (repository.findByUsername(signupForm.getUsername()) == null) { // Check if user exists
		    		repository.save(newUser);
		    	}
		    	else {
	    			bindingResult.rejectValue("username", "err.username", "Username already exists");    	
	    			return "signup";		    		
		    	}
    		}
    		else {
    			bindingResult.rejectValue("passwordCheck", "err.passCheck", "Passwords does not match");    	
    			return "signup";
    		}
    	}
    	else {
    		return "signup";
    	}
    	return "redirect:/login";    	
    }		
}
