package com.epam.spring.core;

import com.epam.spring.core.beans.Client;
import com.epam.spring.core.beans.Event;
import com.epam.spring.core.beans.EventType;
import com.epam.spring.core.loggers.EventLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Import(LoggersConfig.class)
public class AppConfig {
    @Autowired
    LoggersConfig loggersConfig;

    @Bean
    public App app() throws Exception {
        return new App(client(), new HashMap<EventType, EventLogger>(){{
            put(EventType.ERROR, loggersConfig.combinedEventLogger());
            put(EventType.INFO, loggersConfig.consoleEventLogger());
            put(EventType.DEBUG, loggersConfig.dbLogger());

        }});
    }

    @Bean
    public Client client(){
        return new Client();
    }

    @Bean
    @Scope("prototype")
    public Event event(){
        return new Event(new Date(), DateFormat.getDateInstance());
    }

    @Bean
    @Qualifier("info")
    public EventType eventTypeInfo(){
        return EventType.INFO;
    }

    @Bean
    @Qualifier("error")
    public EventType eventTypeError(){
        return EventType.ERROR;
    }

    @Bean
    @Qualifier("debug")
    public EventType eventTypeDebug(){
        return EventType.DEBUG;
    }
}
