package com.pgms.part1.domain.customer.repository;

import com.pgms.part1.domain.customer.dto.CustomerResponseDto;
import com.pgms.part1.domain.customer.entity.Customer;
import com.pgms.part1.util.file.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class CustomerFileRepository implements CustomerRepository{
    private final Logger log = LoggerFactory.getLogger(CustomerFileRepository.class);
    private final static Map<UUID, Customer> customerMap = new HashMap<>();
    private final String filePath;
    private File file;
    private final FileService fileService;

    public CustomerFileRepository(@Value("${file.path.customer}") String filePath, FileService fileService) {
        this.filePath = filePath;
        file = new File(filePath);
        this.fileService = fileService;
        if(file.exists()) {
            customerMapper(fileService.loadFile(filePath));
        }
    }

    private void customerMapper(List<String[]> customerInfolist){
        try {
            customerInfolist.stream().forEach(data -> {
                UUID id = UUID.fromString(data[0]);
                Boolean isBlocked = Boolean.parseBoolean(data[1]);
                customerMap.put(id, new Customer(id, isBlocked));
            });
        }
        catch (ArrayIndexOutOfBoundsException e){
            throw new RuntimeException("file format is not correct");
        }
    }


    @Override
    public List<CustomerResponseDto> listBlockedCustomers() {
        return customerMap.values().stream().filter(Customer::getBlocked)
                .map(customer -> new CustomerResponseDto(customer.getId(), customer.getBlocked()))
                .collect(Collectors.toList());
    }
}
