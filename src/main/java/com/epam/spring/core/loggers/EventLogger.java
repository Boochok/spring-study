package com.epam.spring.core.loggers;

import com.epam.spring.core.beans.Event;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public interface EventLogger {
    void logEvent(Event event) throws IOException;
}
