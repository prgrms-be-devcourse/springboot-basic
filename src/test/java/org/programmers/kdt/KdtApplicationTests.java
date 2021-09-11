package org.programmers.kdt;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("local")
@SpringBootTest
class KdtApplicationTests {

	@Test
	void contextLoads() {
	}

}
