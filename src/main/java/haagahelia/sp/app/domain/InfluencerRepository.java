package haagahelia.sp.app.domain;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface InfluencerRepository extends MongoRepository<Influencer, String> {
	List<Influencer> findByName(String name);
}