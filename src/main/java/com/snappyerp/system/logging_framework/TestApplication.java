package com.snappyerp.system.logging_framework;

import com.snappyerp.system.logging_framework.annotation.LogAll;
import com.snappyerp.system.logging_framework.annotation.LogMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@LogAll
@SpringBootApplication
@RestController
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

    @LogMethod
    @GetMapping("/test")
    public String test() {
        return "Testing logging framework";
    }

    @LogMethod
    @PostMapping("/post")
    public String testPost(){
        return "post is working";
    }

    @LogMethod
    @GetMapping("/error-test")
    public String errorTest() {
        throw new RuntimeException("Intentional error for testing");
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Customer {
        private Long id;
        private String name;
        private String email;
    }

    @PostMapping("/customer")
    @LogMethod
    public ResponseEntity<String> createCustomer(@RequestBody Customer customer) {

        return ResponseEntity.ok("Customer created successfully");
    }


}