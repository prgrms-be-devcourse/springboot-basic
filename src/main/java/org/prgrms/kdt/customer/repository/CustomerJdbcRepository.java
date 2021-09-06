package org.prgrms.kdt.customer.repository;

import org.prgrms.kdt.customer.domain.*;
import org.prgrms.kdt.customer.domain.vo.*;
import org.prgrms.kdt.customer.dto.CustomerSignDto;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CustomerJdbcRepository implements CustomerRepository {
    private static final int SUCCESS_NUMBER = 1;
    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    public CustomerJdbcRepository(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
        Email email = new Email(resultSet.getString("customer_email"));
        Password password = new Password(resultSet.getString("customer_password"));
        Name name = new Name(resultSet.getString("customer_name"));
        PhoneNumber phoneNumber = new PhoneNumber(resultSet.getString("customer_phone_number"));
        Role role = Role.findByRoleType(resultSet.getString("customer_role"));
        LocalDateTime createdDate = resultSet.getTimestamp("customer_created_date").toLocalDateTime();
        LocalDateTime modifiedDate = resultSet.getTimestamp("customer_modified_date") != null ? resultSet.getTimestamp("customer_modified_date").toLocalDateTime() : null;

        return new Customer(modifiedDate, createdDate, customerId, email, password, name, phoneNumber, role);
    };

    private static UUID toUUID(byte[] bytes) {
        ByteBuffer wrap = ByteBuffer.wrap(bytes);
        return new UUID(wrap.getLong(), wrap.getLong());
    }

    @Override
    public Customer insert(CustomerSignDto customerSignDto) {
        Customer customer = customerSignDto.of();
        int insert = jdbcTemplate.update("insert into customer(customer_id, customer_email, customer_password, customer_name, customer_phone_number, customer_role, customer_created_date, customer_modified_date) values (UUID_TO_BIN(?), ?, ?, ?, ?, ?, ?, ?)",
                customer.getCustomerId().toString().getBytes(),
                customer.getEmail().toString(),
                customer.getPassword().toString(),
                customer.getName().toString(),
                customer.getPhoneNumber().toString(),
                customer.getRole().toString(),
                Timestamp.valueOf(customer.getCreatedDate()),
                Timestamp.valueOf(customer.getModifiedDate())
        );
        if (insert != SUCCESS_NUMBER) {
            throw new RuntimeException("Nothing was inserted");
        }
        return customer;
    }

    @Override
    public Optional<Customer> findByEmail(Email email) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from customer where customer_email = ?", customerRowMapper, email.toString()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean updateRoleByEmail(Customer customer) {
        int update = jdbcTemplate.update("update customer set customer_role = ?, customer_modified_date = ? where customer_email = ?",
                customer.getRole().toString(),
                LocalDateTime.now(),
                customer.getEmail().toString()
        );

        if (update != 1) {
            return false;
        }
        return true;
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("select * from customer", customerRowMapper);
    }
}
