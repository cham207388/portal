package com.abc.jobportal.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class ExceptionAuditAspect {

    @AfterThrowing(
            pointcut = "execution(* com.abc.jobportal..*.*(..))",
            throwing = "ex"
    )
    public void logAfterException(JoinPoint joinPoint, Exception ex) {

        String methodName = joinPoint.getSignature().toShortString();
        Object[] methodArgs = joinPoint.getArgs();

        log.error("❌ Exception occurred in method: {}", methodName);
        log.error("📥 Arguments: {}", Arrays.toString(methodArgs));
        log.error("💥 Exception type: {}", ex.getClass().getSimpleName());
        log.error("🧾 Exception message: {}", ex.getMessage());

        // Here you could also:
        // - Send metrics
        // - Push audit events
        // - Trigger alerts
    }

}