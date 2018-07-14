package com.epam.spring.core.beans;

public class Client {

    private String id;

    private String name;

    public String getGreeting() {
        return greeting;
    }

    private String greeting;

    public Client(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }
}
