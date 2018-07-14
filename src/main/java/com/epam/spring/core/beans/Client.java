package com.epam.spring.core.beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
//@PropertySource("classpath:client.properties")
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

    public Client(String id, String name) {
        this.id = id;
        this.name = name;
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
