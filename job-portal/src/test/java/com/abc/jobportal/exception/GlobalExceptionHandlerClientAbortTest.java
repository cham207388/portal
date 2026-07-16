package com.abc.jobportal.exception;

import org.apache.catalina.connector.ClientAbortException;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerClientAbortTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();
    private final ServletWebRequest webRequest = new ServletWebRequest(new MockHttpServletRequest());

    @Test
    void handleMessageNotWritable_returnsNullForBrokenPipe() {
        HttpMessageNotWritableException exception = new HttpMessageNotWritableException(
                "Could not write JSON",
                new IOException("Broken pipe"));

        ResponseEntity<?> response = handler.handleMessageNotWritable(exception, webRequest);

        assertThat(response).isNull();
    }

    @Test
    void handleException_returnsNullForClientAbort() {
        ResponseEntity<?> response = handler.handleException(new ClientAbortException("Broken pipe"), webRequest);

        assertThat(response).isNull();
    }

    @Test
    void handleClientAbort_doesNotThrow() {
        handler.handleClientAbort(new ClientAbortException("client gone"));
    }
}
