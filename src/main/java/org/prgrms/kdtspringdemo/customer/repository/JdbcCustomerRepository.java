package org.prgrms.kdtspringdemo.customer.repository;

import org.prgrms.kdtspringdemo.customer.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("DB")
public class JdbcCustomerRepository implements CustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        var customerName = resultSet.getString("name");
        var isBlack = Boolean.parseBoolean(resultSet.getString("is_black"));
        var customerId = toUUID(resultSet.getBytes("customer_id"));
        return new Customer(customerId, customerName, isBlack);
    };

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    @Autowired
    public JdbcCustomerRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Customer insert(Customer customer) {
        var update = jdbcTemplate.update("INSERT INTO customers(customer_id, name, is_black) VALUES (UUID_TO_BIN(?), ?, ?)",
                customer.getCustomerId().toString().getBytes(),
                customer.getName(),
                customer.isBlack());
        if(update != 1) {
            logger.info("Nothing was inserted");
            throw new RuntimeException("Nothing was inserted");
        }
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("select * from customers", customerRowMapper);
    }

    @Override
    public List<Customer> findNotHaveWalletCustomers() {
        return jdbcTemplate.query("SELECT c.customer_id, c.name, c.is_black FROM customers c LEFT JOIN wallet w ON c.customer_id = w.customer_id WHERE w.customer_id IS NULL",
                customerRowMapper);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM customers");
    }

    @Override
    public void deleteById(UUID customerId) {
        jdbcTemplate.update("DELETE FROM customers where customer_id = UUID_TO_BIN(?)",
                customerId.toString());
        jdbcTemplate.update("DELETE FROM wallet where customer_id = UUID_TO_BIN(?)",
                customerId.toString());
    }

    @Override
    public List<Customer> getAllBlackList() {
        return jdbcTemplate.query("select * from customers where is_black = ?", customerRowMapper,true);
    }
}
