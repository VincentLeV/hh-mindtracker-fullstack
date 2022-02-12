package haagahelia.sp.app;

import java.util.ArrayList;
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

@SpringBootApplication
public class MindTrackerApplication {
	private static final Logger log = LoggerFactory.getLogger(MindTrackerApplication.class);
	
	@Autowired
	InfluencerRepository irepository;

	public static void main(String[] args) {
		SpringApplication.run(MindTrackerApplication.class, args);
	}

	@Bean
	public CommandLineRunner initializeBooks(
		EntryRepository erepository
//		UserRepository urepository,
//		InfluencerRepository irepository
	) {
		return (args) -> {
			log.info("Initialized influencers");

			if (irepository.findAll().isEmpty()) {
				List<Influencer> influencers = new ArrayList<>();
				influencers.add(new Influencer("Work"));
				influencers.add(new Influencer("Study"));
				influencers.add(new Influencer("Family"));
				influencers.add(new Influencer("Friends"));
				influencers.add(new Influencer("Relationship"));
				for (int i = 0; i < influencers.size(); i++) {
					Influencer influencer = new Influencer(influencers.get(i).getName());
					irepository.save(influencer);
				}
			}
			
//			erepository.save(new Entry("Good Day", 4, "Happy", irepository.findByName("Family").get(0), "01/02/2021 10:00 AM", "A good day with family");
//			erepository.save(new Entry("A Bit Down", 3, "Anxious", irepository.findByName("Study").get(0), "01/02/2021 10:00 AM", "Too many assignments");	
			
			log.info("fetch all entries");
			for (Entry entry : erepository.findAll()) {
				log.info(entry.toString());
			}
		};
	}
}
