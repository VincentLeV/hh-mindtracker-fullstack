package haagahelia.sp.app.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import haagahelia.sp.app.domain.Entry;
import haagahelia.sp.app.domain.SignUpForm;
import haagahelia.sp.app.domain.User;
import haagahelia.sp.app.domain.UserRepository;

@Controller
public class UserController {
	@Autowired
    private UserRepository repository; 
	
	@Autowired
	private MongoTemplate mongoTemplate	;
	
    @RequestMapping(value = "/signup")
    public String addUser(Model model) {
    	model.addAttribute("signupform", new SignUpForm());
        return "signup";
    }	
    
    @RequestMapping(value = "/users")
    public String users(Model model) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = repository.findByUsername(auth.getName());
		String userRole = user.getRole();
		
		// Only show users whose roles are USER
		Query query = new Query();
		query.addCriteria(Criteria.where("role").is("USER"));
		List<User> users = mongoTemplate.find(query, User.class);
    	model.addAttribute("users", users);
    	model.addAttribute("userRole", userRole);
        return "users";
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
    
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/edit/users/{id}", method = RequestMethod.GET)
    public String editUser(@PathVariable("id") String id, Model model) {
		model.addAttribute("user", repository.findById(id));
        return "editUser";
    }  
    
    @RequestMapping(value = "/saveedituser", method = RequestMethod.POST)
    public String saveEditUser(User user) {
		repository.save(user);
        return "redirect:users";
    }
    
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/delete/users/{id}")
    public String deleteUser(@PathVariable("id") String id, Model model) {	
    	// Delete user as well as its entries
    	Query query = new Query();
    	query.addCriteria(Criteria.where("userId").is(id));
    	mongoTemplate.remove(query, Entry.class);
    	repository.deleteById(id);
        return "redirect:/users";
    } 
}
