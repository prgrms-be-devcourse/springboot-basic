package com.program.commandLine.service;

import com.program.commandLine.customer.BlackListCustomer;
import com.program.commandLine.customer.Customer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {

    private final ApplicationContext applicationContext;

    private final List<Customer> blackListConsumers = new ArrayList<>();

    @Value("${customer.black_list.path}")
    private String blackListFilePath;

    public CustomerService(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    private void readBlackListFile() {
        Resource resource = applicationContext.getResource(blackListFilePath);
        if (!resource.exists()) return;
        try {
            Files.readAllLines(resource.getFile().toPath()).forEach(name -> {
                blackListConsumers.add(new BlackListCustomer(UUID.randomUUID(), name));
            });
        } catch (IOException error) {
            throw new RuntimeException("! Failed to open Blacklist file");
        }

    }

    public List<Customer> getBlackListCustomers() throws IOException {
        return List.copyOf(blackListConsumers);
    }
}
