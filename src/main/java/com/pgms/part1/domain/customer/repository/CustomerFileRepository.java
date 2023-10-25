package com.pgms.part1.domain.customer.repository;

import com.pgms.part1.domain.customer.entity.Customer;
import com.pgms.part1.domain.customer.entity.CustomerBuilder;
import com.pgms.part1.domain.wallet.entity.Wallet;
import com.pgms.part1.util.file.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
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
    public List<Customer> listBlockedCustomers() {
        return customerMap.values().stream().filter(Customer::getBlocked)
                .map(customer -> new CustomerBuilder().id(customer.getId())
                        .name(customer.getName()).email(customer.getEmail())
                        .isBlocked(customer.getBlocked()).build())
                .collect(Collectors.toList());
    }

    @Override
    public List<Customer> listCustomers() {
        return null;
    }

    @Override
    public void addCustomer(Customer customer) {

    }

    @Override
    public void updateCustomerName(Long id, String name) {

    }

    @Override
    public void deleteCustomer(Long id) {

    }

    @Override
    public List<Customer> listCustomersByWallets(List<Wallet> wallets) {
        return null;
    }
}
