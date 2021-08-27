package org.prgrms.kdt.devcourse.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileLoader {
    private static final Logger logger = LoggerFactory.getLogger(FileLoader.class);
    public static List<String> loadFile(String fileName){
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(Path.of(fileName));
        } catch (IOException e) {
            logger.warn("loadFile - IOException");
        }
        return lines;
    }
}
