package org.prgms.customer.repository;

import org.prgms.customer.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@Repository
public class CustomerRepository {
    private final static Logger logger = LoggerFactory.getLogger(CustomerRepository.class);
    private final JdbcTemplate jdbcTemplate;

    private final static String SELECT_ALL_QUERY = "SELECT * FROM customers;";
    private final static String SELECT_BY_NAME_QUERY = "SELECT * FROM customers WHERE name = ?;";
    private final static String SELECT_BY_ID = "SELECT * FROM customers WHERE customer_id = ?;";
    private final static String SELECT_BY_EMAIL = "SELECT * FROM customers WHERE email = ?;";
    private final static String INSERT_QUERY = "INSERT INTO customers(customer_id, name, email) values (?, ?, ?)";
    private final static String UPDATE_NAME_BY_ID_QUERY = "UPDATE customers SET name = ? WHERE customer_id = ?;";
    private final static String DELETE_QUERY = "DELETE FROM customers;";
    private final static String DELETE_QUERY_BY_ID = "DELETE FROM customers WHERE customer_id = ?;";

    public CustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Customer> findAll() {
        return jdbcTemplate.query(SELECT_ALL_QUERY, this::mapToCustomer);
    }

    public List<Customer> findByName(String name) {
        return jdbcTemplate.query(SELECT_BY_NAME_QUERY, this::mapToCustomer, name);
    }

    public List<Customer> findByEmail(String email) {
        return jdbcTemplate.query(SELECT_BY_EMAIL, this::mapToCustomer, email);
    }

    public List<Customer> findById(UUID customerId) {
        return jdbcTemplate.query(SELECT_BY_ID, this::mapToCustomer, uuidToByteArray(customerId));
    }

    private byte[] uuidToByteArray(UUID uuid) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]);
        byteBuffer.putLong(uuid.getMostSignificantBits());
        byteBuffer.putLong(uuid.getLeastSignificantBits());
        return byteBuffer.array();
    }

    public int insert(Customer customer) {
        var update = jdbcTemplate.update(INSERT_QUERY, uuidToByteArray(customer.customerId()), customer.name(), customer.email());
        if (update != 1) {
            throw new DataIntegrityViolationException(MessageFormat.format("데이터 삽입 실패, 유효 row 갯수 : {0}", update));
        }
        return update;
    }

    public int deleteAll() {
        return jdbcTemplate.update(DELETE_QUERY);
    }

    public void deleteById(UUID customerId) {
        jdbcTemplate.update(DELETE_QUERY_BY_ID, uuidToByteArray(customerId));
    }

    public void update(Customer targetCustomer) {
        var update = jdbcTemplate.update(
                UPDATE_NAME_BY_ID_QUERY, targetCustomer.name(), uuidToByteArray(targetCustomer.customerId()));
        if (update != 1) {
            throw new DataIntegrityViolationException(MessageFormat.format("데이터 업데이트 실패, 유효 row 갯수 {0}", update));
        }
    }

    private Customer mapToCustomer(ResultSet resultSet, int rowNum) throws SQLException {
        var byteBuffer = ByteBuffer.wrap(resultSet.getBytes("customer_id"));
        var customerId = new UUID(byteBuffer.getLong(), byteBuffer.getLong());
        var name = resultSet.getString("name");
        var email = resultSet.getString("email");
        return new Customer(customerId, name, email);
    }

}
