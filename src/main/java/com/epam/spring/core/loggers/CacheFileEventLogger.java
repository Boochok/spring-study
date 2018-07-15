package com.epam.spring.core.loggers;

import com.epam.spring.core.beans.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CacheFileEventLogger extends FileEventLogger {

    private int cacheSize;

    private List<Event> cache = new ArrayList<>();
    @Value("${fileName}")
    private String fileName;

    public CacheFileEventLogger(int cacheSize) {
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

    @PreDestroy
    public void destroy() {
        if(!cache.isEmpty()){
            writeEventFromCache();
        }
    }

}
