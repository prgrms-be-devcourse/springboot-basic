package org.prgrms.kdt.repository.customer;

import org.prgrms.kdt.domain.customer.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Primary
public class JdbcCustomerRepository implements CustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository .class);

    private final DataSource dataSource;

    private final JdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        UUID customerId = convertUUID(resultSet.getBytes("customer_id"));
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");
        return new Customer(customerId, name, email);
    };

    @Override
    public Customer insert(Customer customer) {
        int insert = jdbcTemplate.update("INSERT INTO customers(customer_id,name,email) VALUES (UUID_TO_BIN(?),?,?)",
                customer.getCustomerId().toString().getBytes(),
                customer.getName(),
                customer.getEmail());
        if (insert != 1) {
            throw new RuntimeException("추가된 데이터 내역이 없습니다.");
        }
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("SELECT * FROM customers",customerRowMapper);
    }


    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM customers WHERE customer_id = UUID_TO_BIN(?)",
                    customerRowMapper,
                    customerId.toString().getBytes()));
        } catch (EmptyResultDataAccessException e) {
            logger.error("결과값이 없습니다!");
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM customers WHERE email = ?",
                    customerRowMapper,
                    email));
        } catch (EmptyResultDataAccessException e) {
            logger.error("해당 Email에 맞는 고객이 없습니다.");
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByName(String name) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM customers WHERE name = ?",
                    customerRowMapper,
                    name));
        } catch (EmptyResultDataAccessException e) {
            logger.error("해당 이름에 맞는 고객이 없습니다.");
            return Optional.empty();
        }
    }

    @Override
    public Customer update(Customer customer) {
        int update = jdbcTemplate.update("UPDATE customers SET name = ?, email = ? WHERE customer_id = UUID_TO_BIN(?)",
                customer.getName(),
                customer.getEmail(),
                customer.getCustomerId().toString().getBytes());
        if (update != 1) {
            throw new RuntimeException("업데이트 된 내역이 없습니다.");
        }
        return customer;
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM customers");
    }

    @Override
    public List<List<String>> findAllBlackList() {
        return null;
    }

    public static UUID convertUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
