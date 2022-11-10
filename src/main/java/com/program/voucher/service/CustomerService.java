package com.program.voucher.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    private final ApplicationContext applicationContext;

    @Value("${customer.black_list.path}")
    private String blackListFilePath;

    public CustomerService(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public List<String> getBlackListCustomers() throws IOException {
        var resource = applicationContext.getResource(blackListFilePath);
        if (resource.exists()) {
            return Files.readAllLines(resource.getFile().toPath());
        }
        return new ArrayList<>();
    }
}
