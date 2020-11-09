package com.github.voragoth.drugstores;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Clase de tests de prueba de contexto de spring.
 */
@SpringBootTest
@ActiveProfiles("test")
class DrugstoresOnDutyApplicationTests {

    /**
     * La region por defecto
     */
    @Value("${drugstores.defaultRegion:7}")
    private String defaultRegion;

    /**
     * Test para verificar el contexto de spring.
     */
    @Test
    @DisplayName("Test unitario que verifica que el contexto levante y cubre a DrugstoresOnDutyApplication.main")
    void contextLoads() {
        try (MockedStatic<SpringApplication> sa = mockStatic(SpringApplication.class)) {
            //stubbing
            sa.when(() -> SpringApplication.run(eq(DrugstoresOnDutyApplication.class), eq(new String[0])))
                    .thenReturn(mock(ConfigurableApplicationContext.class));
            //test
            DrugstoresOnDutyApplication.main(new String[0]);
        }

        //asserts
        assertNotNull(defaultRegion);
    }

}
