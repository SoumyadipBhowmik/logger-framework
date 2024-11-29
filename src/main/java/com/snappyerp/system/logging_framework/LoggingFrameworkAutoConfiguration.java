package com.snappyerp.system.logging_framework;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@AutoConfiguration
@ComponentScan(basePackages = "com.snappyerp.system.logging_framework")
@EnableAspectJAutoProxy
public class LoggingFrameworkAutoConfiguration {
}