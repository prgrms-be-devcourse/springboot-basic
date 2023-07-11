package com.devcourse.springbootbasic.application.customer.repository;

import com.devcourse.springbootbasic.application.global.exception.ErrorMessage;
import com.devcourse.springbootbasic.application.customer.model.Customer;
import com.devcourse.springbootbasic.application.global.exception.InvalidDataException;
import com.devcourse.springbootbasic.application.global.io.CsvReader;
import com.devcourse.springbootbasic.application.global.utils.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class CustomerRepository {

    @Value("${settings.blackCustomerPath}")
    private String filepath;

    private final CsvReader csvReader;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CustomerRepository(CsvReader csvReader, NamedParameterJdbcTemplate jdbcTemplate) {
        this.csvReader = csvReader;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Customer> findAllBlackCustomers() {
        return csvReader.readFile(filepath)
                .stream()
                .map(this::convertCsvToCustomer)
                .toList();
    }

    public Customer insert(Customer customer) {
        try {
            var updateResult = jdbcTemplate.update(
                    "INSERT INTO customers(customer_id, name) VALUES (UUID_TO_BIN(:customerId), :name)",
                    toParamMap(customer)
            );
            if (updateResult != 1) {
                throw new InvalidDataException(ErrorMessage.INVALID_CREATION.getMessageText());
            }
            return customer;
        } catch (DataAccessException exception) {
            throw new InvalidDataException(ErrorMessage.INVALID_SQL.getMessageText(), exception.getCause());
        }
    }

    public Customer update(Customer customer) {
        try {
            var updateResult = jdbcTemplate.update(
                    "UPDATE customers SET name = :name WHERE customer_id = UUID_TO_BIN(:customerId)",
                    toParamMap(customer)
            );
            if (updateResult != 1) {
                throw new InvalidDataException(ErrorMessage.INVALID_UPDATE.getMessageText());
            }
            return customer;
        } catch (DataAccessException exception) {
            throw new InvalidDataException(ErrorMessage.INVALID_SQL.getMessageText(), exception.getCause());
        }
    }

    public List<Customer> findAll() {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM customers",
                    customerRowMapper
            );
        } catch (DataAccessException exception) {
            throw new InvalidDataException(ErrorMessage.INVALID_SQL.getMessageText(), exception.getCause());
        }
    }

    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            "SELECT * FROM customers WHERE customer_id = UUID_TO_BIN(:customerId)",
                            Collections.singletonMap("customerId", customerId.toString().getBytes()),
                            customerRowMapper
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Customer> findByName(String name) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            "SELECT * FROM customers WHERE name = :name",
                            Collections.singletonMap("name", name),
                            customerRowMapper
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void deleteAll() {
        try {
            jdbcTemplate.update(
                    "DELETE FROM customers",
                    Collections.emptyMap()
            );
        } catch (DataAccessException exception) {
            throw new InvalidDataException(ErrorMessage.INVALID_SQL.getMessageText(), exception.getCause());
        }
    }

    public void deleteById(UUID customerId) {
        try {
            jdbcTemplate.update(
                    "DELETE FROM customers WHERE customer_id = UUID_TO_BIN(:customerId)",
                    Map.of("customerId", customerId.toString().getBytes())
            );
        } catch (DataAccessException e) {
            throw new InvalidDataException(ErrorMessage.INVALID_SQL.getMessageText(), e.getCause());
        }
    }

    public int count() {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM customers",
                Collections.emptyMap(),
                Integer.class
        );
    }

    public void setFilePath(String filePath) {
        this.filepath = filePath;
    }

    private static final RowMapper<Customer> customerRowMapper = (resultSet, rowNum) -> {
        var customerId = Utils.toUUID(resultSet.getBytes("customer_id"));
        var name = resultSet.getString("name");
        return new Customer(customerId, name);
    };

    private Map<String, Object> toParamMap(Customer customer) {
        var paramMap = new HashMap<String, Object>();
        paramMap.put("customerId", customer.getCustomerId().toString().getBytes());
        paramMap.put("name", customer.getName());
        return paramMap;
    }

    private Customer convertCsvToCustomer(String blackCustomerInfo) {
        String[] customerInfoArray = blackCustomerInfo.split(",");
        return new Customer(
                UUID.fromString(customerInfoArray[0]),
                customerInfoArray[1]
        );
    }

}
