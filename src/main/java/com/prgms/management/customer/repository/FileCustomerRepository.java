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
        // TODO 고객 정보를 CSV 파일에 저장
        throw new UnsupportedOperationException();
    }
    
    @Override
    public Customer update(Customer customer) {
        // TODO 고객 정보를 CSV 파일에서 수정
        throw new UnsupportedOperationException();
    }
    
    @Override
    public Customer findById(UUID id) {
        // TODO ID에 따른 고객 정보를 CSV 파일에서 찾아서 반환
        throw new UnsupportedOperationException();
    }
    
    @Override
    public Customer findByEmail(String email) {
        // TODO Email에 따른 고객 정보를 CSV 파일에서 찾아서 반환
        throw new UnsupportedOperationException();
    }
    
    @Override
    public List<Customer> findByType(CustomerType type) {
        // TODO Type에 따른 고객 목록을 CSV 파일에서 찾아서 반환
        throw new UnsupportedOperationException();
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
        // TODO ID에 따른 고객 정보를 CSV 파일에서 삭제
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void removeAll() {
        // TODO 전체 고객 정보를 CSV 파일에서 삭제
        throw new UnsupportedOperationException();
    }
}
