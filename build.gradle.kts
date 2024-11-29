plugins {
	id("org.springframework.boot") version "3.2.3"
	id("io.spring.dependency-management") version "1.1.4"
	java
}

group = "com.snappyerp.system"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(21))
	}
}

repositories {
	mavenCentral()
}

dependencies{
	implementation("org.springframework.boot:spring-boot-starter-web") {
		exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
	}
	implementation("org.springframework.boot:spring-boot-starter") {
		exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
	}
	implementation("org.springframework:spring-context")
	implementation("org.springframework:spring-aop")
	implementation("org.aspectj:aspectjrt:1.9.22.1")
	implementation("org.aspectj:aspectjweaver:1.9.22.1")

	// Logging
	implementation("org.springframework.boot:spring-boot-starter-log4j2")
	implementation("net.logstash.logback:logstash-logback-encoder:7.4")

	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
}

tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
	enabled = false
}

tasks.getByName<Jar>("jar") {
	enabled = true
}

