package com.programmers.vouchermanagement.customer.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.programmers.vouchermanagement.configuration.properties.file.FileProperties;
import com.programmers.vouchermanagement.customer.domain.Customer;

@Repository
@Profile("jdbc")
public class JdbcCustomerRepository implements CustomerRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final String blacklistFilePath;

    public JdbcCustomerRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, FileProperties fileProperties) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.blacklistFilePath = fileProperties.getCSVCustomerFilePath();
        loadBlacklistToStorage();
    }

    @Override
    public Customer save(Customer customer) {
        return null;
    }

    @Override
    public List<Customer> findAll() {
        return null;
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        return Optional.empty();
    }

    @Override
    public List<Customer> findBlackCustomers() {
        return null;
    }

    @Override
    public void deleteById(UUID customerId) {

    }

    @Override
    public void deleteAll() {
    }

    @Override
    public void loadBlacklistToStorage() {
        List<Customer> blacklist = loadBlacklist(blacklistFilePath);
    }
}
