package com.epam.spring.core.loggers;

import com.epam.spring.core.beans.Event;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CacheFileEventLogger extends FileEventLogger {

    private int cacheSize;

    private List<Event> cache = new ArrayList<>();

    public CacheFileEventLogger(int cacheSize, String fileName) {
        super(fileName);
        this.cacheSize = cacheSize;
    }

    @Override
    public void logEvent(Event event) throws IOException {
        cache.add(event);

        if (cache.size() == cacheSize) {
            writeEventFromCache();
            cache.clear();
        }
    }

    private void writeEventFromCache() {
        cache.forEach(event -> {
            try {
                super.logEvent(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void destroy() {
        if(!cache.isEmpty()){
            writeEventFromCache();
        }
    }

}
