package org.prgrms.prgrmsspring.repository.user;

import org.prgrms.prgrmsspring.entity.user.Customer;
import org.prgrms.prgrmsspring.exception.DataAccessException;
import org.prgrms.prgrmsspring.exception.ExceptionMessage;
import org.prgrms.prgrmsspring.utils.BinaryToUUIDConverter;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.prgrms.prgrmsspring.repository.Field.*;

@Profile("prod")
@Primary
@Repository
public class DBCustomerRepository implements CustomerRepository {

    private final JdbcTemplate jdbcTemplate;

    public DBCustomerRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Customer> findAll() {
        String sql = "SELECT * FROM CUSTOMERS";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Customer(
                        new BinaryToUUIDConverter().run(rs.getBytes(CUSTOMER_ID.getFieldName())),
                        rs.getString(NAME.getFieldName()),
                        rs.getString(EMAIL.getFieldName()),
                        rs.getBoolean(IS_BLACK.getFieldName())));
    }

    @Override
    public List<Customer> findBlackAll() {
        String sql = "SELECT * FROM CUSTOMERS WHERE IS_BLACK = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                        new Customer(
                                new BinaryToUUIDConverter().run(rs.getBytes(CUSTOMER_ID.getFieldName())),
                                rs.getString(NAME.getFieldName()),
                                rs.getString(EMAIL.getFieldName()),
                                rs.getBoolean(IS_BLACK.getFieldName())),
                true);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        String sql = "SELECT * FROM CUSTOMERS WHERE CUSTOMER_ID = UUID_TO_BIN(?)";
        try {
            Customer customer = jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                            new Customer(
                                    new BinaryToUUIDConverter().run(rs.getBytes(CUSTOMER_ID.getFieldName())),
                                    rs.getString(NAME.getFieldName()),
                                    rs.getString(EMAIL.getFieldName()),
                                    rs.getBoolean(IS_BLACK.getFieldName())),
                    customerId.toString());
            return Optional.ofNullable(customer);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Customer insert(Customer customer) {
        String sql = "INSERT INTO CUSTOMERS VALUES(UUID_TO_BIN(?), ?, ?, ?)";
        int insert = jdbcTemplate.update(sql, customer.getCustomerId().toString(), customer.getName(), customer.getIsBlack(), customer.getEmail());
        if (insert != 1) {
            throw new DataAccessException(this.getClass() + " " + ExceptionMessage.INSERT_QUERY_FAILED.getMessage());
        }
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        String customerId = customer.getCustomerId().toString();
        String name = customer.getName();
        Boolean isBlack = customer.getIsBlack();
        String email = customer.getEmail();
        String sql = "UPDATE CUSTOMERS SET NAME = ?, EMAIL = ?, IS_BLACK = ? WHERE CUSTOMER_ID = UUID_TO_BIN(?)";
        int update = jdbcTemplate.update(sql, name, email, isBlack, customerId);
        if (update != 1) {
            throw new DataAccessException(this.getClass() + " " + ExceptionMessage.UPDATE_QUERY_FAILED.getMessage());
        }
        return customer;
    }

    @Override
    public void delete(UUID customerId) {
        String sql = "DELETE FROM CUSTOMERS WHERE CUSTOMER_ID = UUID_TO_BIN(?)";
        int delete = jdbcTemplate.update(sql, customerId.toString());
        if (delete != 1) {
            throw new DataAccessException(this.getClass() + " " + ExceptionMessage.DELETE_QUERY_FAILED.getMessage());
        }
    }

    @Override
    public void clear() {
        String sql = "DELETE FROM CUSTOMERS";
        jdbcTemplate.update(sql);
    }
}
