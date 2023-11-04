package com.programmers.springbootbasic.util;


import static com.programmers.springbootbasic.exception.ErrorCode.FILE_IO_ERROR;

import com.programmers.springbootbasic.exception.exceptionClass.FileIOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class CsvManager implements FileManager {

    private static final Set<String> EXTENSION = Set.of("csv");

    @Override
    public boolean supports(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        return EXTENSION.contains(extension);
    }

    @Override
    public <T> List<T> read(String fileName, Class<T> type) {
        File file = getFileResource(fileName);
        List<T> resultList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            // 헤더 스킵 (첫 줄 읽기)
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                T obj = parseToObject(values, type);
                resultList.add(obj);
            }
        } catch (IOException | ReflectiveOperationException e) {
            throw new FileIOException(FILE_IO_ERROR);
        }

        return resultList;
    }

    @Override
    public <T> void write(T entity, String fileName, boolean append) {
        File file = getFileResource(fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, append))) {
            if (file.length() == 0) {
                writeHeaders(writer, entity);
            }
            writeEntity(writer, entity);
        } catch (IOException e) {
            throw new FileIOException(FILE_IO_ERROR);
        }
    }

    private <T> T parseToObject(String[] values, Class<T> type)
        throws ReflectiveOperationException {
        T instance = type.getDeclaredConstructor().newInstance();

        Field[] fields = type.getDeclaredFields();
        for (int i = 0; i < values.length; i++) {
            if (i < fields.length) {
                Field field = fields[i];
                field.setAccessible(true);
                field.set(instance, values[i]);
            }
        }
        return instance;
    }

    private File getFileResource(String fileName) {
        try {
            Resource resource = new PathResource(fileName);
            if (!resource.exists()) {
                resource.getFile().createNewFile();
            }
            return resource.getFile();
        } catch (IOException e) {
            throw new FileIOException(FILE_IO_ERROR);
        }
    }

    private <T> void writeHeaders(BufferedWriter writer, T entity) throws IOException {
        Field[] fields = entity.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            writer.write(fields[i].getName());
            if (i < fields.length - 1) {
                writer.write(",");
            }
        }
        writer.newLine();
    }

    private <T> void writeEntity(BufferedWriter writer, T entity) throws IOException {
        Field[] fields = entity.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            try {
                Object value = fields[i].get(entity);
                writer.write(value != null ? value.toString() : "");
            } catch (IllegalAccessException e) {
                throw new FileIOException(FILE_IO_ERROR);
            }
            if (i < fields.length - 1) {
                writer.write(",");
            }
        }
        writer.newLine();
    }


}
