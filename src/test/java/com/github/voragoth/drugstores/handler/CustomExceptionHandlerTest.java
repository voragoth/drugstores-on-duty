package com.github.voragoth.drugstores.handler;

import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
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

class CustomExceptionHandlerTest {

    private CustomExceptionHandler handler = new CustomExceptionHandler();

    @Test
    void handleConstraintViolationShouldBeOK1() throws Exception {
        Set<? extends ConstraintViolation<?>> constraintViolations = new HashSet<>();
        ServletWebRequest wreq = mock(ServletWebRequest.class);
        when(wreq.getRequest()).thenReturn(mock(HttpServletRequest.class));
        assertNotNull(handler.handleConstraintViolation(new ConstraintViolationException(constraintViolations), wreq));
    }

    @Test
    @SuppressWarnings("unchecked")
    void handleConstraintViolationShouldBeOK2() throws Exception {
        Set<ConstraintViolation<String>> constraintViolations = new HashSet<>();
        ConstraintViolation<String> cv = mock(ConstraintViolationImpl.class);
        constraintViolations.add(cv);
        ServletWebRequest wreq = mock(ServletWebRequest.class);
        when(wreq.getRequest()).thenReturn(mock(HttpServletRequest.class));
        assertNotNull(handler.handleConstraintViolation(new ConstraintViolationException(constraintViolations), wreq));
    }

    @Test
    void handleMethodArgumentTypeMismatchExceptionShouldBeOK1() throws Exception {
        ServletWebRequest wreq = mock(ServletWebRequest.class);
        when(wreq.getRequest()).thenReturn(mock(HttpServletRequest.class));
        NumberFormatException nfe = new NumberFormatException("TEST");
        MethodArgumentTypeMismatchException matme = mock(MethodArgumentTypeMismatchException.class);
        doReturn(nfe).when(matme).getCause();
        assertNotNull(handler.handleMethodArgumentTypeMismatchException(matme, wreq));
    }

    @Test
    void handleMethodArgumentTypeMismatchExceptionShouldBeOK2() throws Exception {
        ServletWebRequest wreq = mock(ServletWebRequest.class);
        when(wreq.getRequest()).thenReturn(mock(HttpServletRequest.class));
        IllegalArgumentException iae = new IllegalArgumentException("TEST");
        MethodArgumentTypeMismatchException matme = mock(MethodArgumentTypeMismatchException.class);
        doReturn(iae).when(matme).getCause();
        Mockito.<Class<?>> when(matme.getRequiredType()).thenReturn(LocalDate.class);
        assertNotNull(handler.handleMethodArgumentTypeMismatchException(matme, wreq));
    }

    @Test
    void handleMethodArgumentTypeMismatchExceptionShouldBeOK3() throws Exception {
        ServletWebRequest wreq = mock(ServletWebRequest.class);
        when(wreq.getRequest()).thenReturn(mock(HttpServletRequest.class));
        IllegalArgumentException iae = new IllegalArgumentException("TEST");
        MethodArgumentTypeMismatchException matme = mock(MethodArgumentTypeMismatchException.class);
        doReturn(iae).when(matme).getCause();
        assertNotNull(handler.handleMethodArgumentTypeMismatchException(matme, wreq));
    }
}
