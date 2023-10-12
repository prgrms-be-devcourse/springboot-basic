package com.programmers.springbootbasic.domain.customer.repository;

import com.programmers.springbootbasic.common.utils.CsvFileManager;
import com.programmers.springbootbasic.domain.customer.entity.Customer;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class BlacklistCustomerRepository implements CustomerRepository {
    private final CsvFileManager csvFileManager;
    private Map<UUID, Customer> blacklistCustomerMemory;

    public BlacklistCustomerRepository(CsvFileManager csvFileManager) {
        this.csvFileManager = csvFileManager;
    }

    @PostConstruct
    private void init() {
        blacklistCustomerMemory = csvFileManager.loadDataFromCsv();
    }

    @Override
    public List<Customer> findAll() {
        return blacklistCustomerMemory.values()
                .stream()
                .toList();
    }
}
