package haagahelia.sp.app;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import haagahelia.sp.app.domain.User;
import haagahelia.sp.app.domain.UserRepository;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MindTrackerApplication.class)
@SpringBootTest
public class UserRepositoryTest {
	
	@Autowired 
	private UserRepository repository;

	@Test
	public void findUserByUsername() {
		User admin = repository.findByUsername("admin");
		
	    assertThat(admin).isNotNull();
	    assertThat(admin.getRole()).isEqualTo("ADMIN");
	}
	
	@Test
    public void createNewUser() {
    	User user = new User("testuser", "Test User", "$2a$10$LKWE6oNegVzMTihFDFQ1f.PxIRIJOzQQimvyd4iPSKq.N0LyWfv4G", "USER");
    	repository.save(user);
    	assertThat(user.getId()).isNotNull();
    }
}
