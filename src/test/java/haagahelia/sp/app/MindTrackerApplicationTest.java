package haagahelia.sp.app;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import haagahelia.sp.app.controllers.EntryController;
import haagahelia.sp.app.controllers.UserController;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MindTrackerApplication.class)
@SpringBootTest
class MindTrackerApplicationTest {

	@Autowired
	private EntryController controller;
	
	@Autowired
	private UserController ucontroller;
	
	@Test
	public void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
		assertThat(ucontroller).isNotNull();
	}
}
