package com.github.voragoth.drugstores.constants;

import com.github.voragoth.drugstores.config.JsonResponseFeignConfig;
import com.github.voragoth.drugstores.handler.ErrorMessagesConstants;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ErrorMessagesConstantsTest {

    @Test
    void instanceErrorMessagesThrowError() throws Exception {
        Constructor<ErrorMessagesConstants> constructor =
                ErrorMessagesConstants.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        assertThrows(InvocationTargetException.class, () -> constructor.newInstance());
    }
}
