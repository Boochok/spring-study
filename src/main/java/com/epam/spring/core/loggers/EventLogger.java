package com.epam.spring.core.loggers;

import com.epam.spring.core.beans.Event;

import java.io.IOException;

public interface EventLogger {
    void logEvent(Event event) throws IOException;
}
