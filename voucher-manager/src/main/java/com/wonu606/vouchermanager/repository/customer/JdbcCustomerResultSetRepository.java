package com.wonu606.vouchermanager.repository.customer;

import com.wonu606.vouchermanager.domain.customer.Customer;
import com.wonu606.vouchermanager.domain.customer.CustomerResultSet;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class JdbcCustomerResultSetRepository implements CustomerResultSetRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcCustomerResultSetRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public Customer save(Customer customer) {
        if (isCustomerEmailAddressPresent(customer)) {
            updateCustomer(customer);
        }
        insertCustomer(customer);

        return customer;
    }

    @Override
    public Optional<CustomerResultSet> findByEmailAddress(String emailAddress) {
        String selectSql = "SELECT * FROM customer WHERE email_address = ?";
        try {
            CustomerResultSet customerResultSet = jdbcTemplate.queryForObject(selectSql,
                    customerResultSetRowMapper(), emailAddress);
            return Optional.ofNullable(customerResultSet);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<CustomerResultSet> findAll() {
        String selectSql = "SELECT * FROM customer";
        return jdbcTemplate.query(selectSql, customerResultSetRowMapper());
    }

    @Override
    public void deleteByEmailAddress(String emailAddress) {
        String deleteSql = "DELETE FROM customer WHERE email_address = ?";
        jdbcTemplate.update(deleteSql, emailAddress);
    }

    @Override
    public void deleteAll() {
        String deleteSql = "DELETE FROM customer";
        jdbcTemplate.update(deleteSql);
    }

    private boolean isCustomerEmailAddressPresent(Customer customer) {
        String selectSql = "SELECT count(*) FROM customer WHERE email_address = ?";
        Integer count = jdbcTemplate.queryForObject(selectSql, Integer.class,
                customer.getEmailAddress());
        return count != null && count >= 0;
    }

    private void updateCustomer(Customer customer) {
        String updateSql = "UPDATE customer SET nickname = ? WHERE email_address = ?";
        jdbcTemplate.update(updateSql, customer.getNickname(), customer.getEmailAddress());
    }

    private void insertCustomer(Customer customer) {
        String insertSql = "INSERT INTO customer (email_address, nickname) VALUES (?, ?)";
        jdbcTemplate.update(insertSql, customer.getEmailAddress(), customer.getNickname());
    }

    private RowMapper<CustomerResultSet> customerResultSetRowMapper() {
        return (resultSet, rowNum) -> {
            String emailAddress = resultSet.getString("email_address");
            String nickname = resultSet.getString("nickname");
            LocalDateTime lastUpdated = resultSet.getTimestamp("last_updated").toLocalDateTime();
            LocalDateTime createdDate = resultSet.getTimestamp("created_date").toLocalDateTime();

            return new CustomerResultSet(emailAddress, nickname, lastUpdated, createdDate);
        };
    }
}
