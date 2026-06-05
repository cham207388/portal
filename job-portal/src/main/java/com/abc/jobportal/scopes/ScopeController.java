package com.abc.jobportal.scopes;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scope")
@RequiredArgsConstructor
public class ScopeController {

    private final RequestScopedBean requestScopedBean;
    private final SessionScopedBean sessionScopedBean;
    private final ApplicationScopedBean applicationScopedBean;

    @GetMapping("/request")
    public ResponseEntity<String> requestScope() {
        requestScopedBean.setUsername("John Doe");
        return ResponseEntity.ok().body(requestScopedBean.getUsername());
    }

    @GetMapping("/session")
    public ResponseEntity<String> sessionScope() {
        sessionScopedBean.setUsername("John Doe");
        return ResponseEntity.ok().body(sessionScopedBean.getUsername());
    }

    @GetMapping("/application")
    public ResponseEntity<Integer> applicationScope() {
        applicationScopedBean.incrementVistorsCount();
        return ResponseEntity.ok().body(applicationScopedBean.getVistorsCount());
    }

    @GetMapping("/request-test")
    public ResponseEntity<String> testRequestScope() {
        return ResponseEntity.ok().body(requestScopedBean.getUsername());
    }
    @GetMapping("/session-test")
    public ResponseEntity<String> testSessionScope() {
        return ResponseEntity.ok().body(sessionScopedBean.getUsername());
    }
    @GetMapping("/application-test")
    public ResponseEntity<Integer> testApplicationScope() {
        return ResponseEntity.ok().body(applicationScopedBean.getVistorsCount());
    }

}
