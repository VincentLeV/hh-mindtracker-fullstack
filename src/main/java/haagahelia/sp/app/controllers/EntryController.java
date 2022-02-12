package haagahelia.sp.app.controllers;

import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import haagahelia.sp.app.domain.AddEntryForm;
import haagahelia.sp.app.domain.Entry;
import haagahelia.sp.app.domain.EntryRepository;
import haagahelia.sp.app.domain.InfluencerRepository;
import haagahelia.sp.app.domain.User;
import haagahelia.sp.app.domain.UserRepository;
import services.EntryService;

@Controller
public class EntryController {
	
	@Autowired
	private EntryRepository repository;
	
	@Autowired
	private InfluencerRepository irepository; 
	
	@Autowired
	private UserRepository urepository;
	
	@RequestMapping(value="/login")
    public String login() {	
        return "login";
    }
	
	@RequestMapping(value= {"/", "/entries"}, method=RequestMethod.GET)
	public String entries(Model model) {
		// Find current user's id
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = urepository.findByUsername(auth.getName());
		String userRole = user.getRole();
		List<Entry> entries = null;
		
		if (userRole.equals("ADMIN")) {
			// admin user can see all entries
			entries = repository.findAll();
		} else {
			// only show that current user's entries
			entries = repository.findByUserId(user.getId());
		}
		
		// Find current user's entries and sort them by date
		Collections.sort(entries, new EntryService.sortByDate());

		// Format every entry time
		for (int i = 0; i < entries.size(); i++) {
			String time = entries.get(i).getTime();
			int hours = Integer.parseInt(time.substring(0, 2));
			int minutes = Integer.parseInt(time.substring(3));
			String ampm = "";
			if (hours >= 12) {
				ampm = "PM";
			} else {
				ampm = "AM";
			}
			
		 	hours = hours % 12;
		 	if (hours == 0) hours = 12;
		 	
		 	String minuteStr = String.valueOf(minutes);
		 	if (minutes < 10) {
		 		minuteStr = "0" + minuteStr;
		 	}
		 	
		  	String timeStr = hours + ":" + minuteStr + " " + ampm;
		  	entries.get(i).setTime(timeStr);
		}
		model.addAttribute("entries", entries);
		model.addAttribute("userRole", userRole);
		return "entries";
	}
	
	@RequestMapping(value = "/add")
    public String addEntry(Model model) {
//    	model.addAttribute("entry", new Entry());
		model.addAttribute("addentryform", new AddEntryForm());
    	model.addAttribute("influencers", irepository.findAll());
        return "addEntry";
    }
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@Valid  @ModelAttribute("addentryform") AddEntryForm addEntryForm, BindingResult bindingResult, Model model){
		model.addAttribute("influencers", irepository.findAll());
		
		// Find current user's name
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = urepository.findByUsername(auth.getName());
		
		if (!bindingResult.hasErrors()) {
			if (addEntryForm.getDate() == null) {
	    		bindingResult.rejectValue("date", "err.date", "Date can't be empty");    	
				return "addEntry";	
	    	}
	    	
        	Entry entry = new Entry();
        	entry.setHeadline(addEntryForm.getHeadline());
        	entry.setMoodRating(addEntryForm.getMoodRating());
        	entry.setDate(addEntryForm.getDate());
        	entry.setTime(addEntryForm.getTime());
        	entry.setSymptom(addEntryForm.getSymptom());
        	entry.setNotes(addEntryForm.getNotes());
        	entry.setGratitude(addEntryForm.getGratitude());
        	entry.setInfluencer(addEntryForm.getInfluencer());
        	
    		// Add current user's id to the entry for reference
    		entry.setUserId(user.getId());
    		repository.save(entry);
        } else if (addEntryForm.getHeadline() == null) {
			bindingResult.rejectValue("headline", "err.headline", "Headline can't be empty");    	
			return "addEntry";		    		
    	} else if (addEntryForm.getTime() == null) {
    		bindingResult.rejectValue("time", "err.time", "Time can't be empty");    	
			return "addEntry";	
    	} else {
        	return "addEntry";
        }
		
        return "redirect:entries";
    }
	
	@RequestMapping(value = "/saveeditentry", method = RequestMethod.POST)
    public String saveEditEntry(Entry entry){
        repository.save(entry);
        return "redirect:entries";
    }
	
	@RequestMapping(value = "/edit/entries/{id}", method = RequestMethod.GET)
    public String editEntry(@PathVariable("id") String id, Model model) {
		model.addAttribute("entry", repository.findById(id));
		model.addAttribute("influencers", irepository.findAll());
        return "editEntry";
    }  
	
	@RequestMapping(value = "/delete/entries/{id}", method = RequestMethod.GET)
    public String deleteEntry(@PathVariable("id") String id, Model model) {
    	repository.deleteById(id);
        return "redirect:../../entries";
    } 
}
