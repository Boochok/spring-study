package com.epam.spring.core;

import com.epam.spring.core.aspects.StatisticsAspect;
import com.epam.spring.core.beans.Client;
import com.epam.spring.core.beans.Event;
import com.epam.spring.core.beans.EventType;
import com.epam.spring.core.loggers.EventLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static com.epam.spring.core.beans.EventType.DEBUG;
import static com.epam.spring.core.beans.EventType.ERROR;
import static com.epam.spring.core.beans.EventType.INFO;

@Component
public class App {

    @Autowired
    @Qualifier("fileEventLogger")
    private EventLogger defaultEventLogger;

    private Client client;

    private Map<EventType, EventLogger> loggers;

    @Autowired
    private StatisticsAspect statisticsAspect;

    @Autowired
    public App(Client client, Map<EventType, EventLogger> loggers) {
        this.client = client;
        this.loggers = loggers;
    }

    void logEvent(String massage, Event event, EventType type) throws IOException {
        event.setMsg(massage.replaceAll(client.getId(), client.getName()));
        Optional.ofNullable(loggers.get(type))
                .or(()-> Optional.of(defaultEventLogger))
                .get()
                .logEvent(event);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        ConfigurableApplicationContext ctx =
                new AnnotationConfigApplicationContext(AppConfig.class);//it's possible to give several configs, Spring union them into one

        //App app = (App) ctx.getBean("app") - by bean name
        App app = ctx.getBean(App.class);

        app.logEvent("Some event for user 2", ctx.getBean(Event.class), ERROR);
        app.logEvent("Some event for user 1", (Event) ctx.getBean("event"), INFO);
        app.logEvent("Some event for user 34", (Event) ctx.getBean("event"), null);
        app.logEvent("Some event for user 35", (Event) ctx.getBean("event"), INFO);
        app.logEvent("Some event for user 36", (Event) ctx.getBean("event"), INFO);
        app.logEvent("Some event for user 37", (Event) ctx.getBean("event"), null);
        app.logEvent("Some event for user 215", (Event) ctx.getBean("event"), DEBUG);

        app.statisticsAspect.getCounter().forEach((log, count) -> System.out.println(log.getSimpleName() + " - "
                + count));
        ctx.close();
    }
}
