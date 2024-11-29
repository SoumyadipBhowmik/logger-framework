package com.snappyerp.system.logging_framework.model;

import lombok.Builder;
import lombok.Data;
import java.time.Instant;

@Data
@Builder
public class LogEvent {
    private String traceId;
    private String level;
    private String serviceName;
    private String className;
    private String methodName;
    private String message;
    private Instant timestamp;
    private String exceptionMessage;
    private String stackTrace;
    private long executionTime;
}