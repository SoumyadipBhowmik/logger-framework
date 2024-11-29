package com.snappyerp.system.logging_framework.util;

import org.slf4j.MDC;
import java.util.Map;
import java.util.function.Supplier;

public class LoggingUtils {

    public static <T> T withMDC(Map<String, String> context, Supplier<T> operation) {
        Map<String, String> oldContext = MDC.getCopyOfContextMap();
        try {
            if (context != null) {
                context.forEach(MDC::put);
            }
            return operation.get();
        } finally {
            if (oldContext != null) {
                MDC.setContextMap(oldContext);
            } else {
                MDC.clear();
            }
        }
    }

    public static void withMDC(Map<String, String> context, Runnable operation) {
        Map<String, String> oldContext = MDC.getCopyOfContextMap();
        try {
            if (context != null) {
                context.forEach(MDC::put);
            }
            operation.run();
        } finally {
            if (oldContext != null) {
                MDC.setContextMap(oldContext);
            } else {
                MDC.clear();
            }
        }
    }
}