package com.devcourse.springbootbasic.application.io;

import com.devcourse.springbootbasic.application.constant.ErrorMessage;
import com.devcourse.springbootbasic.application.exception.InvalidDataException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Component
public class CsvReader {

    public List<String> readFile(String filepath) {
        try {
            return Files.readAllLines(Path.of(filepath));
        } catch (IOException e) {
            throw new InvalidDataException(ErrorMessage.INVALID_FILE_ACCESS.getMessageText(), e.getCause());
        }
    }

}
