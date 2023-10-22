package org.prgrms.prgrmsspring.repository.user;

import org.prgrms.prgrmsspring.entity.user.Customer;
import org.prgrms.prgrmsspring.exception.DataAccessException;
import org.prgrms.prgrmsspring.exception.ExceptionMessage;
import org.prgrms.prgrmsspring.utils.BinaryToUUIDConverter;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Profile("prod")
@Repository
public class DBCustomerRepository implements CustomerRepository {

    private final JdbcTemplate jdbcTemplate;

    public DBCustomerRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Customer> findAll() {
        String sql = "SELECT * FROM CUSTOMERS";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Customer(new BinaryToUUIDConverter().run(rs.getBytes("CUSTOMER_ID")), rs.getString("NAME"), rs.getBoolean("IS_BLACK")));
    }

    @Override
    public List<Customer> findBlackAll() {
        String sql = "SELECT * FROM CUSTOMERS WHERE IS_BLACK = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Customer(new BinaryToUUIDConverter().run(rs.getBytes("CUSTOMER_ID")), rs.getString("NAME"), rs.getBoolean("IS_BLACK")),
                true);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        return Optional.empty();
    }

    @Override
    public Customer insert(Customer customer) {
        String sql = "INSERT INTO CUSTOMERS VALUES(UUID_TO_BIN(?), ?, ?)";
        int insert = jdbcTemplate.update(sql, customer.getCustomerId().toString(), customer.getName(), customer.getIsBlack());
        if (insert != 1) {
            throw new DataAccessException(this.getClass() + " " + ExceptionMessage.INSERT_QUERY_FAILED.getMessage());
        }
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        return null;
    }

    @Override
    public void delete(UUID customerId) {

    }
}
