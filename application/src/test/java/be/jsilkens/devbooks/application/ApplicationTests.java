package be.jsilkens.devbooks.application;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles(profiles = {"local"})
class ApplicationTests {

	@Test
	void contextLoads() {
	}

}
