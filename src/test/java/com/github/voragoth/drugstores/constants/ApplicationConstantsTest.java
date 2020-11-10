package com.github.voragoth.drugstores.constants;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Clase de tests para ApplicationConstants.
 */
class ApplicationConstantsTest {

    /**
     * Test unitario para instanciar ApplicationConstants dando error.
     *
     * @throws Exception
     */
    @Test
    @DisplayName("Test unitario para instanciar ApplicationConstants dando error")
    void instanceErrorMessagesThrowError() throws Exception {
        // objetos necesarios
        Constructor<ApplicationConstants> constructor =
                ApplicationConstants.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        // test y assert
        assertThrows(InvocationTargetException.class, () -> constructor.newInstance());
    }
}
