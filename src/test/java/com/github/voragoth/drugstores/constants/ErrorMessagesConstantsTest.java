package com.github.voragoth.drugstores.constants;

import com.github.voragoth.drugstores.handler.ErrorMessagesConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Clase de tests para ErrorMessagesConstants.
 */
class ErrorMessagesConstantsTest {

    /**
     * Test unitario para instanciar ErrorMessagesConstants dando error.
     *
     * @throws Exception
     */
    @Test
    @DisplayName("Test unitario para instanciar ErrorMessagesConstants dando error")
    void instanceErrorMessagesThrowError() throws Exception {
        // objetos necesarios
        Constructor<ErrorMessagesConstants> constructor =
                ErrorMessagesConstants.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        // test y assert
        assertThrows(InvocationTargetException.class, () -> constructor.newInstance());
    }
}
