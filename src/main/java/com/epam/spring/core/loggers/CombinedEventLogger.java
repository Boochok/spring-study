package com.epam.spring.core.loggers;

import com.epam.spring.core.beans.Event;

import java.io.IOException;
import java.util.Collection;

public class CombinedEventLogger implements EventLogger {

    private Collection<EventLogger> loggers;

    public CombinedEventLogger(Collection<EventLogger> loggers) {
        this.loggers = loggers;
    }
    @Override
    public void logEvent(Event event) throws IOException {
        loggers.forEach(eventLogger -> {
            try {
                eventLogger.logEvent(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
