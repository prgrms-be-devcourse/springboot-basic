package com.devcourse.springbootbasic.application.repository;

import com.devcourse.springbootbasic.application.constant.YamlProperties;
import com.devcourse.springbootbasic.application.exception.InvalidDataException;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Repository
public class CustomerRepository {

    private final ConfigurableApplicationContext applicationContext;

    public CustomerRepository(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public List<String> findAllBlackCustomers() {
        try {
            return Files.readAllLines(getBlackCustomerFile().toPath());
        } catch (IOException e) {
            throw new InvalidDataException(e.getMessage(), e.getCause());
        }
    }

    private File getBlackCustomerFile() throws IOException {
        return applicationContext.getResource(
                String.format("file:%s", getBlackCustomerFilePath())
        ).getFile();
    }

    private String getBlackCustomerFilePath() {
        return applicationContext.getBean(YamlProperties.class)
                .getBlackCustomerPath();
    }

}
