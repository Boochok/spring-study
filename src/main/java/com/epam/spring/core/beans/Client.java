package com.epam.spring.core.beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Client {

    @Value("${id}")
    private String id;

    @Value("${name}")
    private String name;

    private String greeting;

    @Value("${greeting}")
    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    public String getGreeting() {
        return greeting;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
