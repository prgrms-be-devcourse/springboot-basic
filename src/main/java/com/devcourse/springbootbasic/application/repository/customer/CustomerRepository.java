package com.devcourse.springbootbasic.application.repository.customer;

import com.devcourse.springbootbasic.application.constant.ErrorMessage;
import com.devcourse.springbootbasic.application.converter.CustomerConverter;
import com.devcourse.springbootbasic.application.domain.customer.Customer;
import com.devcourse.springbootbasic.application.exception.InvalidDataException;
import com.devcourse.springbootbasic.application.io.CsvReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.*;

@Repository
public class CustomerRepository {

    @Value("${settings.blackCustomerPath}")
    private String filepath;

    private static final RowMapper<Customer> customerRowMapper = (resultSet, rowNum) -> {
        var customerId = toUUID(resultSet.getBytes("customer_id"));
        var name = resultSet.getString("name");
        var email = resultSet.getString("email");
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return new Customer(customerId, name, email, createdAt);
    };

    private final CsvReader csvReader;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CustomerRepository(CsvReader csvReader, NamedParameterJdbcTemplate jdbcTemplate) {
        this.csvReader = csvReader;
        this.jdbcTemplate = jdbcTemplate;
    }

    private Map<String, Object> toParamMap(Customer customer) {
        return new HashMap<>() {{
            put("customerId", customer.getCustomerId().toString().getBytes());
            put("name", customer.getName());
            put("email", customer.getEmail());
            put("createdAt", Timestamp.valueOf(customer.getCreatedAt()));
        }};
    }

    private static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    public List<Customer> findAllBlackCustomers() {
        return csvReader.readFile(filepath)
                .stream()
                .map(CustomerConverter::convertCsvToCustomer)
                .toList();
    }

    public Customer insert(Customer customer) {
        try {
            var updateResult = jdbcTemplate.update(
                    "INSERT INTO customers(customer_id, name, email, created_at) VALUES (UUID_TO_BIN(:customerId), :name, :email, :createdAt)",
                    toParamMap(customer)
            );
            if (updateResult != 1) {
                throw new InvalidDataException(ErrorMessage.INVALID_CUSTOMER_CREATION.getMessageText());
            }
            return customer;
        } catch (DataAccessException exception) {
            throw new InvalidDataException(ErrorMessage.INVALID_SQL_QUERY.getMessageText());
        }
    }

    public Customer update(Customer customer) {
        try {
            var updateResult = jdbcTemplate.update(
                    "UPDATE customers SET name = :name, email = :email, created_at = :createdAt WHERE customer_id = UUID_TO_BIN(:customerId)",
                    toParamMap(customer)
            );
            if (updateResult != 1) {
                throw new InvalidDataException(ErrorMessage.INVALID_CUSTOMER_INFO.getMessageText());
            }
            return customer;
        } catch (DataAccessException exception) {
            throw new InvalidDataException(ErrorMessage.INVALID_SQL_QUERY.getMessageText());
        }
    }

    public List<Customer> findAll() {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM customers",
                    customerRowMapper
            );
        } catch (DataAccessException exception) {
            throw new InvalidDataException(ErrorMessage.INVALID_SQL_QUERY.getMessageText());
        }
    }

    public Optional<Customer> findById(UUID customerId) {
        return Optional.ofNullable(
                jdbcTemplate.queryForObject(
                        "SELECT * FROM customers WHERE customer_id = UUID_TO_BIN(:customerId)",
                        Collections.singletonMap("customerId", customerId.toString().getBytes()),
                        customerRowMapper
                )
        );
    }

    public Optional<Customer> findByName(String name) {
        return Optional.ofNullable(
                jdbcTemplate.queryForObject(
                        "SELECT * FROM customers WHERE name = :name",
                        Collections.singletonMap("name", name),
                        customerRowMapper
                )
        );
    }

    public Optional<Customer> findByEmail(String email) {
        return Optional.ofNullable(
                jdbcTemplate.queryForObject(
                        "SELECT * FROM customers WHERE email = :email",
                        Collections.singletonMap("email", email),
                        customerRowMapper
                )
        );
    }

    public void deleteAll() {
        try {
            jdbcTemplate.update(
                    "DELETE FROM customers",
                    Collections.emptyMap()
            );
        } catch (DataAccessException exception) {
            throw new InvalidDataException(ErrorMessage.INVALID_SQL_QUERY.getMessageText());
        }
    }

    public void deleteById(Customer customer) {
        try {
            jdbcTemplate.update(
                    "DELETE FROM customers WHERE customer_id = UUID_TO_BIN(:customerId)",
                    toParamMap(customer)
            );
        } catch (DataAccessException e) {
            throw new InvalidDataException(ErrorMessage.INVALID_SQL_QUERY.getMessageText(), e.getCause());
        }
    }

    public void deleteByName(Customer customer) {
        try {
            jdbcTemplate.update(
                    "DELETE FROM customers WHERE name = :name",
                    toParamMap(customer)
            );
        } catch (DataAccessException e) {
            throw new InvalidDataException(ErrorMessage.INVALID_SQL_QUERY.getMessageText(), e.getCause());
        }
    }

    public void deleteByEmail(Customer customer) {
        try {
            jdbcTemplate.update(
                    "DELETE FROM customers WHERE email = :email",
                    toParamMap(customer)
            );
        } catch (DataAccessException e) {
            throw new InvalidDataException(ErrorMessage.INVALID_SQL_QUERY.getMessageText(), e.getCause());
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

}
