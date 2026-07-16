package com.abc.jobportal.exception;

import com.abc.jobportal.dto.ErrorResponseDto;
import io.micrometer.tracing.Tracer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerClientAbortTest {

    private GlobalExceptionHandler handler;
    private Tracer tracer;
    private ServletWebRequest webRequest;

    @BeforeEach
    void setUp() {
        tracer = mock(Tracer.class);
        when(tracer.currentTraceContext()).thenReturn(mock());
        when(tracer.currentTraceContext().context()).thenReturn(null);

        handler = new GlobalExceptionHandler(tracer);
        webRequest = new ServletWebRequest(new MockHttpServletRequest());
    }

    @Test
    void handleException_returnsErrorResponseForGeneralException() {
        Exception exception = new RuntimeException("Test error");

        ResponseEntity<ErrorResponseDto> response = handler.handleException(exception, webRequest);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().errorMessage()).isEqualTo("Test error");
    }

    @Test
    void handleException_includesTraceIdWhenAvailable() {
        Exception exception = new NullPointerException("NPE test");

        ResponseEntity<ErrorResponseDto> response = handler.handleNullException(exception, webRequest);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().errorMessage()).contains("NullPointerException");
    }
}
