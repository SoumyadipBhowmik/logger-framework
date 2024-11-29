package com.snappyerp.system.logging_framework.aspect;

import com.snappyerp.system.logging_framework.annotation.LogAll;
import com.snappyerp.system.logging_framework.annotation.LogMethod;
import com.snappyerp.system.logging_framework.model.LogEvent;
import com.snappyerp.system.logging_framework.service.LoggingService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Aspect
@Component
public class LoggingAspect {

    private String extractPackageName(ProceedingJoinPoint joinPoint) {
        String fullClassName = joinPoint.getTarget().getClass().getName();
        String[] parts = fullClassName.split("\\.");
        return String.join(".", parts[3]);
    }

    @Around("@within(logAll)")
    public Object logClass(ProceedingJoinPoint joinPoint, LogAll logAll) throws Throwable {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        String traceId = UUID.randomUUID().toString();

        LogEvent classEvent = LogEvent.builder()
                .traceId(traceId)
                .className(className)
                .methodName(methodName)
                .level("INFO")
                .message("Class invocation: " + logAll.serviceName())
                .build();

        try {
            LoggingService.logInfo(classEvent);
            return joinPoint.proceed();
        } catch (Exception e) {
            LogEvent errorEvent = LogEvent.builder()
                    .traceId(traceId)
                    .className(className)
                    .methodName(methodName)
                    .level("ERROR")
                    .message("Class execution failed: " + e.getMessage())
                    .build();
            LoggingService.logError(errorEvent);
            throw e;
        }
    }

    @Around("@annotation(logMethod)")
    public Object logMethod(ProceedingJoinPoint joinPoint, LogMethod logMethod) throws Throwable {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        String traceId = UUID.randomUUID().toString();
        String serviceName = extractPackageName(joinPoint);
        long startTime = System.currentTimeMillis();
        System.out.println("serviceName: " + serviceName);
        try {
            LogEvent startEvent = LogEvent.builder()
                    .traceId(traceId)
                    .serviceName(serviceName)
                    .className(className)
                    .methodName(methodName)
                    .level("INFO")
                    .message("Method execution started")
                    .build();
            LoggingService.logInfo(startEvent);

            Object result = joinPoint.proceed();

            long executionTime = System.currentTimeMillis() - startTime;
            LogEvent endEvent = LogEvent.builder()
                    .traceId(traceId)
                    .serviceName(serviceName)
                    .className(className)
                    .methodName(methodName)
                    .level("INFO")
                    .message("Method execution completed")
                    .executionTime(executionTime)
                    .build();
            LoggingService.logInfo(endEvent);

            return result;
        } catch (Exception e) {
            LogEvent errorEvent = LogEvent.builder()
                    .traceId(traceId)
                    .serviceName(serviceName)
                    .className(className)
                    .methodName(methodName)
                    .level("ERROR")
                    .message("Method execution failed: " + e.getMessage())
                    .build();
            LoggingService.logError(errorEvent);
            throw e;
        }
    }

}