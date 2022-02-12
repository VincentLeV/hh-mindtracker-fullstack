package haagahelia.sp.app;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import haagahelia.sp.app.domain.Entry;
import haagahelia.sp.app.domain.EntryRepository;
import haagahelia.sp.app.domain.Influencer;
import haagahelia.sp.app.domain.InfluencerRepository;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MindTrackerApplication.class)
@SpringBootTest
public class EntryRepositoryTest {

	@Autowired
	private EntryRepository repository;
	
	@Autowired
	private InfluencerRepository irepository;
	
	@Test
	public void findEntryByHeadline() {
		List<Entry> entries = repository.findByHeadline("Entry 1");
		
		assertThat(entries).hasSize(1);
	    assertThat(entries.get(0).getHeadline()).isEqualTo("Entry 1");
	    assertThat(entries.get(0).getSymptom()).isEqualTo("Happy");
	    assertThat(entries.get(0).getInfluencer().getName()).isEqualTo("Work");
	    assertThat(entries.get(0).getTime()).isEqualTo("10:00");
	}
	
	@Test
    public void editEntry() {
    	List<Entry> entries = repository.findByHeadline("Entry 1");
    	entries.get(0).setHeadline("Entry 2");
    	entries.get(0).setSymptom("Sad");
    	
    	assertThat(entries).hasSize(1);
    	assertThat(entries.get(0).getHeadline()).isEqualTo("Entry 2");
    	assertThat(entries.get(0).getSymptom()).isEqualTo("Sad");
    }
	
	@Test
	public void createEntry() {
		List<Influencer> influencers = irepository.findByName("Others");
		Entry entry = new Entry("New Entry", 3, "Good", new Date(), "23:00", influencers.get(0), "620788e793ebca31aca5fecf", "", "");
		repository.save(entry);
		
		List<Entry> entries = repository.findByHeadline("New Entry");
		assertThat(entries).hasSize(1);
		assertThat(entries.get(0).getHeadline()).isEqualTo("New Entry");
    	assertThat(entries.get(0).getSymptom()).isEqualTo("Good");
    	assertThat(entries.get(0).getTime()).isEqualTo("23:00");
    	assertThat(entries.get(0).getInfluencer().getName()).isEqualTo("Others");
	}
	
	@Test
    public void deleteEntry() {
		List<Entry> entries = repository.findByHeadline("To Be Deleted");
		Entry entry = entries.get(0);
		repository.delete(entry);
		List<Entry> newEntries = repository.findByHeadline("To Be Deleted");
		assertThat(newEntries).hasSize(0);
     }
}
