package com.epam.spring.core;

import com.epam.spring.core.beans.Client;
import com.epam.spring.core.beans.Event;
import com.epam.spring.core.beans.EventType;
import com.epam.spring.core.loggers.EventLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static com.epam.spring.core.beans.EventType.DEBUG;
import static com.epam.spring.core.beans.EventType.INFO;

public class App {

    private Client client;

    @Qualifier("fileEventLogger")
    private EventLogger defaultEventLogger;

    private Map<EventType, EventLogger> loggers;

    @Autowired
    public App(Client client, EventLogger defaultEventLogger, Map<EventType, EventLogger> loggers) {
        this.client = client;
        this.defaultEventLogger = defaultEventLogger;
        this.loggers = loggers;
    }

    void logEvent(String massage, Event event, EventType type) throws IOException {
        event.setMsg(massage.replaceAll(client.getId(), client.getName()));
        Optional.ofNullable(loggers.get(type))
                .or(()-> Optional.of(defaultEventLogger))
                .get()
                .logEvent(event);

    }

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext ctx =
                new ClassPathXmlApplicationContext("spring_config.xml");//it's possible to give several configs, Spring union them into one

        //App app = (App) ctx.getBean("app") - by bean name
        App app = ctx.getBean(App.class);

        app.logEvent("Some event for user 2", ctx.getBean(Event.class), DEBUG);
        app.logEvent("Some event for user 1", (Event) ctx.getBean("event"), INFO);

        ctx.close();
    }
}
