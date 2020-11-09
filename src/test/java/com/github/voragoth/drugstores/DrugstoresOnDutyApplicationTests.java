package com.github.voragoth.drugstores;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class DrugstoresOnDutyApplicationTests {

	@Value("${drugstores.defaultRegion:7}")
	private String defaultRegion;

	@Test
	void contextLoads() {
		try (MockedStatic<SpringApplication> sa = mockStatic(SpringApplication.class)) {
			sa.when(() -> SpringApplication.run(eq(DrugstoresOnDutyApplication.class), eq(new String[0])))
					.thenReturn(mock(ConfigurableApplicationContext.class));
			DrugstoresOnDutyApplication.main(new String[0]);
		}

		assertNotNull(defaultRegion);
	}

}
