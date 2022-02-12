package haagahelia.sp.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import haagahelia.sp.app.domain.Entry;
import haagahelia.sp.app.domain.EntryRepository;
import haagahelia.sp.app.domain.Influencer;
import haagahelia.sp.app.domain.InfluencerRepository;
import haagahelia.sp.app.domain.UserRepository;
import haagahelia.sp.app.domain.User;

@SpringBootApplication
public class MindTrackerApplication {
	private static final Logger log = LoggerFactory.getLogger(MindTrackerApplication.class);
	
	@Autowired
	InfluencerRepository irepository;

	public static void main(String[] args) {
		SpringApplication.run(MindTrackerApplication.class, args);
	}

	@Bean
	public CommandLineRunner initialize(
		EntryRepository erepository,
		UserRepository urepository
	) {
		return (args) -> {
			log.info("Initialized influencers");
			
			User user = urepository.findByUsername("testuser");
			if (user != null) {
				urepository.delete(user);
			}
			erepository.deleteAll();

			if (irepository.findAll().isEmpty()) {
				List<Influencer> influencers = new ArrayList<>();
				influencers.add(new Influencer("Work"));
				influencers.add(new Influencer("Study"));
				influencers.add(new Influencer("Family"));
				influencers.add(new Influencer("Friends"));
				influencers.add(new Influencer("Relationship"));
				influencers.add(new Influencer("Others"));
				influencers.add(new Influencer("Nothing"));
				for (int i = 0; i < influencers.size(); i++) {
					Influencer influencer = new Influencer(influencers.get(i).getName());
					irepository.save(influencer);
				}
			}
			
			if (urepository.findByUsername("admin") == null) {
				User admin = new User("admin", "Admin", "$2a$12$dN3Bl1QOaxmXNq25UjN55enZk.q01UiWImKxe47rLKta/9Rd7R/vG", "ADMIN");
				urepository.save(admin);
			}
			
			if (erepository.findByHeadline("Entry 1").size() == 0) {
				List<Influencer> influencers = irepository.findByName("Work");
				Entry entry = new Entry("Entry 1", 4, "Happy", new Date(), "10:00", influencers.get(0), "620788e793ebca31aca5fecf", "", "");
				erepository.save(entry);
			} 
			
			if (erepository.findByHeadline("To Be Deleted").size() == 0) {
				List<Influencer> influencers = irepository.findByName("Study");
				Entry entry = new Entry("To Be Deleted", 2, "Anxious", new Date(), "10:30", influencers.get(0), "620788e793ebca31aca5fecf", "", "");
				erepository.save(entry);
			}
			
			log.info("fetch all entries");
			for (Entry entry : erepository.findAll()) {
				log.info(entry.toString());
			}
		};
	}
}
