package com.programmers.vouchermanagement.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

public class JSONFileManager<K, T> {
    private static final Logger logger = LoggerFactory.getLogger(JSONFileManager.class);

    //messages
    private static final String FILE_EXCEPTION = "Error raised while opening the file.";
    private static final String IO_EXCEPTION_LOG_MESSAGE = "Error raised while reading json file.";
    private static final String NO_DATA_STORED = "No %s is stored yet!";

    private final ObjectMapper objectMapper;
    private final Class<T> fileType;

    public JSONFileManager(ObjectMapper objectMapper, Class<T> fileType) {
        this.objectMapper = objectMapper;
        this.fileType = fileType;
    }

    public void saveFile(String filePath, Map<K, T> objects, Function<T, HashMap<String, Object>> mapDomainToObject) {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            List<HashMap<String, Object>> targetObjects = new ArrayList<>();

            objects.values().forEach(object -> {
                HashMap<String, Object> targetObject = mapDomainToObject.apply(object);
                targetObjects.add(targetObject);
            });

            String jsonStr = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(targetObjects);
            fileWriter.write(jsonStr);
            fileWriter.flush();
        } catch (Exception e) {
            throw new RuntimeException(FILE_EXCEPTION);
        }
    }

    public List<T> loadFile(String filePath, Function<Map, T> mapObjectToDomain) {
        try {
            File file = new File(filePath);
            Map[] voucherObjects = objectMapper.readValue(file, Map[].class);
            return loadTargets(voucherObjects, mapObjectToDomain);
        } catch (MismatchedInputException e) {
            logger.debug(NO_DATA_STORED.formatted(fileType.getCanonicalName()));
            return new ArrayList<>();
        } catch (IOException e) {
            logger.error(IO_EXCEPTION_LOG_MESSAGE);
            throw new UncheckedIOException(e);
        }
    }

    private List<T> loadTargets(Map[] targetObjects, Function<Map, T> mapObjectToDomain) {
        return Arrays.stream(targetObjects)
                .map(mapObjectToDomain)
                .toList();
    }
}
