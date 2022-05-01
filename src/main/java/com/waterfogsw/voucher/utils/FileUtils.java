package com.waterfogsw.voucher.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class FileUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    private static final String FILE_FORMAT = ".json";
    private static final String FILE_EXIST = "File already exist";
    private static final String DIRECTORY_ERROR = "Error Occurred While Initialize Directory";
    private static final String PATH_VALIDATION_REGEX = "^(((\\.|\\.\\.).)|\\/)(((\\w*|(\\.\\.\\/))+\\/)*)(\\w+|\\/)$";

    static {
        mapper.registerModule(new JavaTimeModule());
    }

    private FileUtils() {
    }

    public static boolean validatePath(String path) {
        return Pattern.matches(PATH_VALIDATION_REGEX, path);
    }

    public static void write(String path, String name, Object object) {
        final File file = new File(path + "/" + name + FILE_FORMAT);
        try {
            if (!file.createNewFile()) {
                throw new IllegalStateException(FILE_EXIST);
            }
            mapper.writeValue(file, object);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public static <T> List<T> readAll(String path, Class<T> valueType) {
        final File folder = new File(path);

        return Arrays.stream(Objects.requireNonNull(folder.listFiles()))
                .map((f) -> wrapperReadValue(f, valueType))
                .toList();
    }

    public static void initFilePath(String path) {
        final File repositoryFolder = new File(path);
        if (repositoryFolder.exists()) {
            deleteDirectory(repositoryFolder);
        }

        if (!repositoryFolder.mkdirs()) {
            throw new IllegalStateException(DIRECTORY_ERROR);
        }
    }

    private static void deleteDirectory(File root) {
        final File[] allFiles = root.listFiles();
        if (allFiles != null) {
            Arrays.stream(allFiles)
                    .forEach(FileUtils::deleteDirectory);
        }

        if (!root.delete()) {
            throw new IllegalStateException(DIRECTORY_ERROR);
        }
    }

    private static <T> T wrapperReadValue(File file, Class<T> valueType) {
        try {
            return mapper.readValue(file, valueType);
        } catch (IOException e) {
            throw new IllegalStateException();
        }
    }
}
