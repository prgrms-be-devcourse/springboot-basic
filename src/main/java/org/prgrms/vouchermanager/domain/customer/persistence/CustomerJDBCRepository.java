package org.prgrms.vouchermanager.domain.customer.persistence;

import org.prgrms.vouchermanager.domain.customer.domain.Customer;
import org.prgrms.vouchermanager.domain.customer.domain.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CustomerJDBCRepository implements CustomerRepository {

    private final Logger log = LoggerFactory.getLogger(CustomerJDBCRepository.class);
    private final JdbcTemplate jdbcTemplate;
    /**
     * 데이터베이스에서 읽어온 ResultSet을 Customer로 매핑하기 위한 Mapper입니다.
     */
    private final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        UUID customer_id = UUIDBytesToUUID(resultSet.getBytes("customer_id"));
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return new Customer(customer_id, name, email, createdAt);
    };

    public CustomerJDBCRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private UUID UUIDBytesToUUID(byte[] customer_id) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(customer_id);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    @Override
    public Customer insert(Customer customer) {
        int theNumberOfRowAffected = jdbcTemplate.update("INSERT INTO customers(customer_id, name, email, created_at) VALUES (UUID_TO_BIN(?), ?, ?, ?)",
                customer.getCustomerId().toString().getBytes(),
                customer.getName(),
                customer.getEmail(),
                Timestamp.valueOf(customer.getCreateAt()));
        if (theNumberOfRowAffected != 1)
            throw new IllegalArgumentException("잘못된 삽입입니다.");
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        int theNumberOfRowsAffected = jdbcTemplate.update("UPDATE customers SET name = ?, email =? where customer_id = UUID_TO_BIN(?)",
                customer.getName(),
                customer.getEmail(),
                customer.getCustomerId().toString().getBytes()
        );

        if (theNumberOfRowsAffected != 1)
            throw new IllegalArgumentException(MessageFormat.format("Customer: {0} 업데이트 실패", customer));
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("SELECT * FROM customers", customerRowMapper);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM customers WHERE customer_id = UUID_TO_BIN(?)",
                    customerRowMapper,
                    customerId.toString().getBytes()));
        } catch (EmptyResultDataAccessException e) {
            log.error(MessageFormat.format("findById: {0} 반환 결과가 1개 행이 아닙니다.", customerId));
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
            log.error(MessageFormat.format("findByName: {0} 반환 결과가 1개 행이 아닙니다.", name));
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
            log.error(MessageFormat.format("findByEmail: {0} 반환 결과가 1개 행이 아닙니다.", email));
            return Optional.empty();
        }
    }

    @Override
    public void delete(UUID customerId) {
        int theNumberOfRowsDeleted = jdbcTemplate.update("DELETE FROM customers WHERE customer_id = UUID_TO_BIN(?)",
                customerId.toString().getBytes());

        if (theNumberOfRowsDeleted != 1) {
            log.error(MessageFormat.format("customerId : {0} 반환 결과가 1개 행이 아닙니다.", customerId));
            throw new IllegalArgumentException(MessageFormat.format("customerId : {0} 반환 결과가 1개 행이 아닙니다.", customerId));
        }
    }

    @Override
    public void deleteAll() {
        int theNumberOfRowsDeleted = jdbcTemplate.update("DELETE FROM customers");
        log.info("전부 {} 행이 삭제되었습니다", theNumberOfRowsDeleted);
    }
}
