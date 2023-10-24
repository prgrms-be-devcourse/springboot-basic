package com.pgms.part1.domain.customer.repository;

import com.pgms.part1.domain.customer.dto.CustomerFileResponseDto;
import com.pgms.part1.domain.customer.entity.Customer;
import com.pgms.part1.domain.customer.entity.CustomerBuilder;
import com.pgms.part1.util.file.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@Deprecated
public class CustomerFileRepository implements CustomerRepository{
    private final Logger log = LoggerFactory.getLogger(CustomerFileRepository.class);
    private final static Map<Long, Customer> customerMap = new HashMap<>();
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
                Long id = Long.parseLong(data[0]);
                Boolean isBlocked = Boolean.parseBoolean(data[1]);
                customerMap.put(id, new CustomerBuilder().id(id).isBlocked(isBlocked).build());
            });
        }
        catch (ArrayIndexOutOfBoundsException e){
            throw new RuntimeException("file format is not correct");
        }
    }

    @Override
    public List<CustomerFileResponseDto> listBlockedCustomers() {
        return customerMap.values().stream().filter(Customer::getBlocked)
                .map(customer -> new CustomerFileResponseDto(customer.getId(), customer.getBlocked()))
                .collect(Collectors.toList());
    }
}
