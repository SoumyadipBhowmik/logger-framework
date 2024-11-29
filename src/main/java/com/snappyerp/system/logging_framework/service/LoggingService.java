package com.snappyerp.system.logging_framework.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snappyerp.system.logging_framework.model.LogEvent;
import java.io.IOException;

public class LoggingService {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void logInfo(LogEvent event) {
        printLog(event);
    }

    public static void logError(LogEvent event) {
        printLog(event);
    }

    private static void printLog(LogEvent event) {
        try {
            String json = mapper.writeValueAsString(event);
            System.out.println(json);
        } catch (IOException e) {
            System.err.println("Error logging event: " + e.getMessage());
        }
    }
}