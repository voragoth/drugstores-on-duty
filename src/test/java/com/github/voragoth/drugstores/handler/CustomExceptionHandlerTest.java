package com.github.voragoth.drugstores.handler;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

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
     * Test unitario para handleHystrixRuntimeException con resultado OK y causa ResponseStatusException
     */
    @Test
    @DisplayName("Test unitario para handleHystrixRuntimeException con resultado OK y causa ResponseStatusException")
    void handleHystrixRuntimeExceptionShouldBeOK1() {
        // Objetos necesarios
        ServletWebRequest wreq = mock(ServletWebRequest.class);
        ResponseStatusException rse = new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "TEST");
        Exception ex = mock(Exception.class);
        AssertionError ae = mock(AssertionError.class);
        HystrixRuntimeException hre = mock(HystrixRuntimeException.class);

        // stubbing
        when(wreq.getRequest()).thenReturn(mock(HttpServletRequest.class));
        doReturn(HystrixRuntimeException.FailureType.TIMEOUT).when(hre).getFailureType();

        doReturn(ex).when(hre).getFallbackException();
        doReturn(ae).when(ex).getCause();
        doReturn(rse).when(ae).getCause();

        // test y asserts
        assertNotNull(handler.handleHystrixRuntimeException(hre, wreq));
    }

    @Test
    @DisplayName("Test unitario para handleHystrixRuntimeException con resultado OK y causa runtime anidada")
    void handleHystrixRuntimeExceptionShouldBeOK2() {
        // Objetos necesarios
        ServletWebRequest wreq = mock(ServletWebRequest.class);
        Exception ex = mock(Exception.class);
        AssertionError ae = mock(AssertionError.class);
        HystrixRuntimeException hre = mock(HystrixRuntimeException.class);

        // stubbing
        when(wreq.getRequest()).thenReturn(mock(HttpServletRequest.class));
        doReturn(HystrixRuntimeException.FailureType.TIMEOUT).when(hre).getFailureType();
        doReturn(ex).when(hre).getFallbackException();
        doReturn(ae).when(ex).getCause();
        doReturn(new RuntimeException()).when(ae).getCause();

        // test y asserts
        assertNotNull(handler.handleHystrixRuntimeException(hre, wreq));
    }

    @Test
    @DisplayName("Test unitario para handleHystrixRuntimeException con resultado OK y causa runtime base")
    void handleHystrixRuntimeExceptionShouldBeOK3() {
        // Objetos necesarios
        ServletWebRequest wreq = mock(ServletWebRequest.class);
        HystrixRuntimeException hre = mock(HystrixRuntimeException.class);

        // stubbing
        when(wreq.getRequest()).thenReturn(mock(HttpServletRequest.class));
        doReturn(HystrixRuntimeException.FailureType.TIMEOUT).when(hre).getFailureType();
        doReturn(new RuntimeException()).when(hre).getFallbackException();

        // test y asserts
        assertNotNull(handler.handleHystrixRuntimeException(hre, wreq));
    }

    @Test
    @DisplayName("Test unitario para handleHystrixRuntimeException con resultado OK sin causas")
    void handleHystrixRuntimeExceptionShouldBeOK4() {
        // Objetos necesarios
        ServletWebRequest wreq = mock(ServletWebRequest.class);
        HystrixRuntimeException hre = mock(HystrixRuntimeException.class);

        // stubbing
        when(wreq.getRequest()).thenReturn(mock(HttpServletRequest.class));
        doReturn(HystrixRuntimeException.FailureType.TIMEOUT).when(hre).getFailureType();

        // test y asserts
        assertNotNull(handler.handleHystrixRuntimeException(hre, wreq));
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
