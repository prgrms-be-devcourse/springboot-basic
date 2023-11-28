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
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

public class JSONFileManager<K, T> {
    private static final Logger logger = LoggerFactory.getLogger(JSONFileManager.class);

    //messages
    private static final String FILE_EXCEPTION = "Application in illegal state. Error raised while opening the file.";
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
            saveTargetObject(objects, targetObjects, mapDomainToObject);
            String jsonStr = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(targetObjects);
            fileWriter.write(jsonStr);
            fileWriter.flush();
        } catch (Exception e) {
            logger.error(FILE_EXCEPTION);
            throw new IllegalStateException(FILE_EXCEPTION);
        }
    }

    private void saveTargetObject(
            Map<K, T> objects,
            List<HashMap<String, Object>> targetObjects,
            Function<T, HashMap<String, Object>> mapDomainToObject
    ) {
        objects.values().forEach(object -> {
                    HashMap<String, Object> targetObject = mapDomainToObject.apply(object);
                    targetObjects.add(targetObject);
                });
    }

    public Map<K, T> loadFile(String filePath, Function<Map, T> mapObjectToDomain, Function<T, K> keyMapper) {
        try {
            File file = new File(filePath);
            Map[] targetObjects = objectMapper.readValue(file, Map[].class);
            return loadTargets(targetObjects, mapObjectToDomain, keyMapper);
        } catch (MismatchedInputException e) {
            logger.debug(NO_DATA_STORED.formatted(fileType.getCanonicalName()));
            return new HashMap<>();
        } catch (IOException e) {
            logger.error(IO_EXCEPTION_LOG_MESSAGE);
            throw new UncheckedIOException(e);
        }
    }

    private Map<K, T> loadTargets(Map[] targetObjects, Function<Map, T> mapObjectToDomain, Function<T, K> keyMapper) {
        return Arrays.stream(targetObjects)
                .map(mapObjectToDomain)
                .collect(Collectors.toMap(keyMapper, domain -> domain));
    }
}
