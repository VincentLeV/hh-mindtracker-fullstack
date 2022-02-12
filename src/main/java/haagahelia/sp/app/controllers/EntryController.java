package haagahelia.sp.app.controllers;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
//	@RequestMapping(value="/api/entries", method=RequestMethod.GET, produces="application/json")
//	@ResponseBody
//	public List<Entry> getEntries() {
//	    return (List<Entry>) repository.findAll();
//	}
	
	@RequestMapping(value= {"/", "/entries"}, method=RequestMethod.GET, produces="text/html")
	public String entries(Model model) {
		// Find current user's id
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = urepository.findByUsername(auth.getName());
		
		// Find current user's entries and sort them by date
		List<Entry> entries = repository.findByUserId(user.getId());
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
		  	minutes = minutes < 10 ? '0'+ minutes : minutes;
		  	String timeStr = hours + ":" + minutes + " " + ampm;
		  	entries.get(i).setTime(timeStr);
		}
		model.addAttribute("entries", entries);
		return "entries";
	}
	
//	@RequestMapping(value="/entries", method = RequestMethod.GET)
//    public @ResponseBody List<Entry> entriesRest() {	
//        return (List<Entry>) repository.findAll();
//    }  
		
//	@RequestMapping(value="/entries", method = RequestMethod.POST)
//	public String addEntry(@RequestBody Entry entry) {
//		repository.save(entry);
//		return "Added entry	with headline: " + entry.getHeadline();
//	}
	
	@RequestMapping(value = "/add")
    public String addEntry(Model model) {
    	model.addAttribute("entry", new Entry());
    	model.addAttribute("influencers", irepository.findAll());
        return "addEntry";
    }
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Entry entry){
		// Find current user's name
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = urepository.findByUsername(auth.getName());
		
		// Add current user's id to the entry for reference
		entry.setUserId(user.getId());
		repository.save(entry);
        return "redirect:entries";
    }
	
	@RequestMapping(value = "/saveedit", method = RequestMethod.POST)
    public String saveEdit(Entry entry){
        repository.save(entry);
        return "redirect:entries";
    }
	
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editBook(@PathVariable("id") String id, Model model) {
		model.addAttribute("entry", repository.findById(id));
		model.addAttribute("influencers", irepository.findAll());
        return "editEntry";
    }  
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteEntry(@PathVariable("id") String id, Model model) {
    	repository.deleteById(id);
        return "redirect:../allEntries";
    } 
}
