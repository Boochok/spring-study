package com.epam.spring.core.loggers;

import com.epam.spring.core.beans.Event;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Component
@PropertySource("classpath:loggers.properties")
public class FileEventLogger implements EventLogger {

    private File file;
    @Value("${fileName}")
    private String fileName;

    @Override
    public void logEvent(Event event) throws IOException {
        FileUtils.writeStringToFile(file, event.toString()+"\n", true);
    }

    @PostConstruct
    public void init() throws Exception {
        file = new File(fileName);
        boolean newFile = file.createNewFile();
        if(!file.canWrite()) {
            throw new IOException();
        }
    }
}
