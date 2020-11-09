package com.github.voragoth.drugstores.handler;

import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Clase para tests de CustomExceptionHandler.
 */
class CustomExceptionHandlerTest {

    /**
     * Objeto a testear.
     */
    private CustomExceptionHandler handler = new CustomExceptionHandler();

    /**
     * Test unitario para handleConstraintViolation, con lista vacia de violaciones
     */
    @Test
    @DisplayName("Test unitario para handleConstraintViolation, con lista vacia de violaciones.")
    void handleConstraintViolationShouldBeOK1() {
        // objetos necesarios
        Set<? extends ConstraintViolation<?>> constraintViolations = new HashSet<>();
        ServletWebRequest wreq = mock(ServletWebRequest.class);

        // stubbing
        when(wreq.getRequest()).thenReturn(mock(HttpServletRequest.class));

        // test y assert
        assertNotNull(handler.handleConstraintViolation(new ConstraintViolationException(constraintViolations), wreq));
    }

    /**
     * Test unitario para handleConstraintViolation, con lista no vacia de violaciones.
     */
    @Test
    @DisplayName("Test unitario para handleConstraintViolation, con lista no vacia de violaciones.")
    void handleConstraintViolationShouldBeOK2() {
        // objetos necesarios
        Set<ConstraintViolation<String>> constraintViolations = new HashSet<>();
        ConstraintViolation<String> cv = mockConstraintViolation();
        constraintViolations.add(cv);
        ServletWebRequest wreq = mock(ServletWebRequest.class);

        // stubbing
        when(wreq.getRequest()).thenReturn(mock(HttpServletRequest.class));

        // test y assert
        assertNotNull(handler.handleConstraintViolation(new ConstraintViolationException(constraintViolations), wreq));
    }

    /**
     * Test unitario para handleMethodArgumentTypeMismatchException, con causa de NumberFormatException
     */
    @Test
    @DisplayName("Test unitario para handleMethodArgumentTypeMismatchException, con causa de NumberFormatException")
    void handleMethodArgumentTypeMismatchExceptionShouldBeOK1() {
        // Objetos necesarios
        ServletWebRequest wreq = mock(ServletWebRequest.class);
        NumberFormatException nfe = new NumberFormatException("TEST");
        MethodArgumentTypeMismatchException matme = mock(MethodArgumentTypeMismatchException.class);

        // stubbing
        when(wreq.getRequest()).thenReturn(mock(HttpServletRequest.class));
        doReturn(nfe).when(matme).getCause();

        // test y asserts
        assertNotNull(handler.handleMethodArgumentTypeMismatchException(matme, wreq));
    }

    /**
     * Test unitario para handleMethodArgumentTypeMismatchException, con causa de IllegalArgumentException.
     */
    @Test
    @DisplayName("Test unitario para handleMethodArgumentTypeMismatchException, con causa de IllegalArgumentException")
    void handleMethodArgumentTypeMismatchExceptionShouldBeOK2() {
        // Objectos necesarios
        ServletWebRequest wreq = mock(ServletWebRequest.class);
        IllegalArgumentException iae = new IllegalArgumentException("TEST");
        MethodArgumentTypeMismatchException matme = mock(MethodArgumentTypeMismatchException.class);

        // Stubbing
        when(wreq.getRequest()).thenReturn(mock(HttpServletRequest.class));
        doReturn(iae).when(matme).getCause();
        Mockito.<Class<?>>when(matme.getRequiredType()).thenReturn(LocalDate.class);

        // Tests y asserts
        assertNotNull(handler.handleMethodArgumentTypeMismatchException(matme, wreq));
    }

    /**
     * Test unitario para handleMethodArgumentTypeMismatchException, sin requiredType.
     */
    @Test
    @DisplayName("Test unitario para handleMethodArgumentTypeMismatchException, sin requiredType")
    void handleMethodArgumentTypeMismatchExceptionShouldBeOK3() {
        // Objetos necesarios
        ServletWebRequest wreq = mock(ServletWebRequest.class);
        IllegalArgumentException iae = new IllegalArgumentException("TEST");
        MethodArgumentTypeMismatchException matme = mock(MethodArgumentTypeMismatchException.class);

        // stubbing
        when(wreq.getRequest()).thenReturn(mock(HttpServletRequest.class));
        doReturn(iae).when(matme).getCause();

        // asserts y tests
        assertNotNull(handler.handleMethodArgumentTypeMismatchException(matme, wreq));
    }

    /**
     * Genera un mock de ConstraintViolation y encapsula waring de unchecked.
     *
     * @return el mock ConstraintViolation<String>
     */
    private ConstraintViolation<String> mockConstraintViolation() {
        @SuppressWarnings("unchecked")
        ConstraintViolation<String> cv = mock(ConstraintViolationImpl.class);
        return cv;
    }
}
