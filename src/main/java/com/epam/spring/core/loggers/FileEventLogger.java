package com.epam.spring.core.loggers;

import com.epam.spring.core.beans.Event;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class FileEventLogger implements EventLogger {

    private File file;
    private String fileName;

    public FileEventLogger(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void logEvent(Event event) throws IOException {
        FileUtils.writeStringToFile(file, event.getMsg(), true);
    }

    public void init() throws Exception {
        this.file = new File(fileName);
        boolean newFile = file.createNewFile();
        if(!file.canWrite()) {
            throw new IOException();
        }
    }
}
