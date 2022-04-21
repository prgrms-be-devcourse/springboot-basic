package com.prgms.management.customer.repository;

import com.prgms.management.common.exception.EmptyListException;
import com.prgms.management.customer.model.Customer;
import com.prgms.management.customer.model.CustomerType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@Profile({"dev"})
public class FileCustomerRepository implements CustomerRepository {
    private final Resource resource;
    
    public FileCustomerRepository(@Value("${database.file.black-list}") String filename) {
        DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
        this.resource = defaultResourceLoader.getResource(filename);
    }
    
    @Override
    public Customer save(Customer customer) {
        // 구현 보류
        return null;
    }
    
    @Override
    public Customer update(Customer customer) {
        // 구현 보류
        return null;
    }
    
    @Override
    public Customer findById(UUID id) {
        // 구현 보류
        return null;
    }
    
    @Override
    public Customer findByEmail(String email) {
        // 구현 보류
        return null;
    }
    
    @Override
    public List<Customer> findByType(CustomerType type) {
        // 구현 보류
        List<Customer> customers = new ArrayList<>();
        
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(resource.getFile()))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] array = line.split(",");
                customers.add(new Customer(CustomerType.BLACK, UUID.fromString(array[0]), array[1]));
            }
        } catch (IOException e) {
            throw new EmptyListException(e.getMessage());
        }
        
        return customers;
    }
    
    @Override
    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(resource.getFile()))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] array = line.split(",");
                customers.add(new Customer(CustomerType.BLACK, UUID.fromString(array[0]), array[1]));
            }
        } catch (IOException e) {
            throw new EmptyListException(e.getMessage());
        }
        
        return customers;
    }
    
    @Override
    public void removeById(UUID id) {
        // 구현 보류
    }
    
    @Override
    public void removeAll() {
        // 구현 보류
    }
}
