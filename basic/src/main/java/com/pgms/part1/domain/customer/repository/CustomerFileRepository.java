package com.pgms.part1.domain.customer.repository;

import com.pgms.part1.domain.customer.dto.CustomerResponseDto;
import com.pgms.part1.domain.customer.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class CustomerFileRepository implements CustomerRepository{
    private final Logger log = LoggerFactory.getLogger(CustomerFileRepository.class);
    private final static Map<UUID, Customer> customerMap = new HashMap<>();
    private String FILE_PATH;
    private File file;

    public CustomerFileRepository(@Value("${file.path.customer}") String FILE_PATH) {
        this.FILE_PATH = FILE_PATH;
        file = new File(FILE_PATH);
        if(file.exists()) {
            loadfile();
        }
    }

    private void loadfile() {
        try(BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while((line = br.readLine()) != null){
                String[] data = line.split(",");

                UUID id = UUID.fromString(data[0]);
                Boolean isBlocked = Boolean.parseBoolean(data[1]);

                customerMap.put(id, new Customer(id, isBlocked));
            }
            log.info("file loaded");
        } catch (IOException e) {
            log.error("load file error");
            throw new RuntimeException("can not load file!!");
        }
    }

    @Override
    public List<CustomerResponseDto> listBlockedCustomers() {
        return customerMap.values().stream().filter(Customer::getBlocked)
                .map(customer -> new CustomerResponseDto(customer.getId(), customer.getBlocked()))
                .collect(Collectors.toList());
    }
}
