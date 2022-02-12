package haagahelia.sp.app.domain;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
	
public interface EntryRepository extends MongoRepository<Entry, String> {
	List<Entry> findByHeadline(String headline);
	List<Entry> findByUserId(String userId);
	Entry findFirstByOrderByIdDesc();
}
