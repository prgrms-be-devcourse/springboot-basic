package com.programmers.vouchermanagement.customer.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.programmers.vouchermanagement.configuration.properties.file.FileProperties;
import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.customer.domain.CustomerType;
import com.programmers.vouchermanagement.util.UUIDConverter;

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
        int savedVoucher = findById(customer.getCustomerId()).isEmpty() ? insert(customer) : update(customer);
        if (savedVoucher != 1) {
            throw new NoSuchElementException("Exception is raised while saving the customer %s".formatted(customer.getCustomerId()));
        }
        return customer;
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
        blacklist.forEach(this::save);
    }

    private int insert(Customer customer) {
        final String saveSQL = "INSERT INTO customers(customer_id, name, customer_type) VALUES (UUID_TO_BIN(:customerId), :name, :customerType)";
        Map<String, Object> parameterMap = toParameterMap(customer);
        return namedParameterJdbcTemplate.update(saveSQL, parameterMap);
    }

    private int update(Customer customer) {
        return 0;
    }

    private Map<String, Object> toParameterMap(Customer customer) {
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("customerId", customer.getCustomerId().toString().getBytes());
        parameterMap.put("name", customer.getName());
        parameterMap.put("customerType", customer.getCustomerType().name());

        return Collections.unmodifiableMap(parameterMap);
    }

    private static Customer mapToCustomer(ResultSet resultSet) throws SQLException {
        final UUID customer_id = UUIDConverter.toUUID(resultSet.getBytes("customer_id"));
        final String name = resultSet.getString("name");
        final String customerTypeName = resultSet.getString("customer_type");
        final CustomerType customerType = CustomerType.findCustomerType(customerTypeName);
        return new Customer(customer_id, name, customerType);
    }
}
