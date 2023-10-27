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
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.programmers.vouchermanagement.configuration.properties.file.FileProperties;
import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.customer.domain.CustomerType;
import com.programmers.vouchermanagement.util.UUIDConverter;

@Repository
@Profile("jdbc")
public class JdbcCustomerRepository implements CustomerRepository {
    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> mapToCustomer(resultSet);
    public static final int SINGLE_DATA_FLAG = 1;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final String blacklistFilePath;

    public JdbcCustomerRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, FileProperties fileProperties) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.blacklistFilePath = fileProperties.getCSVCustomerFilePath();
        loadBlacklistToStorage();
    }

    private static Customer mapToCustomer(ResultSet resultSet) throws SQLException {
        final UUID customer_id = UUIDConverter.toUUID(resultSet.getBytes("customer_id"));
        final String name = resultSet.getString("name");
        final String customerTypeName = resultSet.getString("customer_type");
        final CustomerType customerType = CustomerType.findCustomerType(customerTypeName);
        return new Customer(customer_id, name, customerType);
    }

    @Override
    public Customer save(Customer customer) {
        int savedCustomer = findById(customer.getCustomerId()).isEmpty() ? insert(customer) : update(customer);
        if (savedCustomer != SINGLE_DATA_FLAG) {
            throw new NoSuchElementException("Exception is raised while saving the customer %s".formatted(customer.getCustomerId()));
        }
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        String findAllSQL = "SELECT * FROM customers";
        return Collections.unmodifiableList(namedParameterJdbcTemplate.query(findAllSQL, customerRowMapper));
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        String findByIdSQL = "SELECT * FROM customers WHERE customer_id=UUID_TO_BIN(:customerId)";
        Map<String, Object> parameterMap = Collections.singletonMap("customerId", customerId.toString().getBytes());
        try {
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(findByIdSQL, parameterMap, customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Customer> findBlackCustomers() {
        String blacklistSQL = "SELECT * FROM customers WHERE customer_type='BLACK'";
        return namedParameterJdbcTemplate.query(blacklistSQL, customerRowMapper);
    }

    @Override
    public void deleteById(UUID customerId) {
        String deleteSQL = "DELETE FROM customers WHERE customer_id=UUID_TO_BIN(:customerId)";
        Map<String, Object> parameteMap = Collections.singletonMap("customerId", customerId.toString().getBytes());
        namedParameterJdbcTemplate.update(deleteSQL, parameteMap);
    }

    @Override
    @Profile("test")// for the use of clearing in the test only
    public void deleteAll() {
        String deleteAllSQL = "DELETE FROM customers";
        namedParameterJdbcTemplate.update(deleteAllSQL, Collections.emptyMap());
    }

    @Override
    public void loadBlacklistToStorage() {
        List<Customer> blacklist = loadBlacklist(blacklistFilePath);
        blacklist.forEach(this::save);
    }

    private int insert(Customer customer) {
        String saveSQL = "INSERT INTO customers(customer_id, name, customer_type) VALUES (UUID_TO_BIN(:customerId), :name, :customerType)";
        Map<String, Object> parameterMap = toParameterMap(customer);
        return namedParameterJdbcTemplate.update(saveSQL, parameterMap);
    }

    private int update(Customer customer) {
        String updateSQL = "UPDATE customers SET name = :name, customer_type = :customerType WHERE customer_id = UUID_TO_BIN(:customerId)";
        Map<String, Object> parameterMap = toParameterMap(customer);
        return namedParameterJdbcTemplate.update(updateSQL, parameterMap);
    }

    private Map<String, Object> toParameterMap(Customer customer) {
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("customerId", customer.getCustomerId().toString().getBytes());
        parameterMap.put("name", customer.getName());
        parameterMap.put("customerType", customer.getCustomerType().name());

        return Collections.unmodifiableMap(parameterMap);
    }
}
