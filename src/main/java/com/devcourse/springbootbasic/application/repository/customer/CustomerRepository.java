package com.devcourse.springbootbasic.application.repository.customer;

import com.devcourse.springbootbasic.application.constant.YamlProperties;
import com.devcourse.springbootbasic.application.exception.InvalidDataException;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Repository
public class CustomerRepository {

    private final String filepath;

    public CustomerRepository(String filepath) {
        this.filepath = filepath;
    }

    public List<String> findAllBlackCustomers() {
        try {
            return Files.readAllLines(Path.of(filepath));
        } catch (IOException e) {
            throw new InvalidDataException(e.getMessage(), e.getCause());
        }
    }

}
