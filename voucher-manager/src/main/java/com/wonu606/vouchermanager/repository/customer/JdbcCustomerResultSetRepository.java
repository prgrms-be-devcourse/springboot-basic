package com.wonu606.vouchermanager.repository.customer;

import com.wonu606.vouchermanager.domain.customer.Customer;
import com.wonu606.vouchermanager.domain.customer.CustomerResultSet;
import com.wonu606.vouchermanager.domain.customer.emailAddress.EmailAddress;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JdbcCustomerResultSetRepository implements CustomerResultSetRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcCustomerResultSetRepository(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
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
    public Optional<CustomerResultSet> findByEmailAddress(EmailAddress emailAddress) {
        String selectSql = "SELECT * FROM customer WHERE email_address = :email_address";
        Map<String, Object> params = new HashMap<>();
        params.put("email_address", emailAddress.getAddress());
        try {
            CustomerResultSet customerResultSet = namedParameterJdbcTemplate.
                    queryForObject(selectSql, params, customerResultSetRowMapper());
            return Optional.ofNullable(customerResultSet);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<CustomerResultSet> findAll() {
        String selectSql = "SELECT * FROM customer";
        return namedParameterJdbcTemplate.query(selectSql, customerResultSetRowMapper());
    }

    @Override
    public void deleteByEmailAddress(EmailAddress emailAddress) {
        String deleteSql = "DELETE FROM customer WHERE email_address = :email_address";
        Map<String, Object> params = new HashMap<>();
        params.put("email_address", emailAddress.getAddress());
        namedParameterJdbcTemplate.update(deleteSql, params);
    }

    @Override
    public void deleteAll() {
        String deleteSql = "DELETE FROM customer";
        namedParameterJdbcTemplate.update(deleteSql, new HashMap<>());
    }

    @Override
    public List<CustomerResultSet> findAllByEmailAddresses(List<EmailAddress> emailAddresses) {
        if (emailAddresses.isEmpty()) {
            return Collections.emptyList();
        }

        String selectSql = "SELECT * FROM customer WHERE email_address IN (:email_address)";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("email_address", emailAddresses);
        return namedParameterJdbcTemplate.query(
                selectSql, parameters, customerResultSetRowMapper());
    }

    private boolean isCustomerEmailAddressPresent(Customer customer) {
        String selectSql = "SELECT count(*) FROM customer WHERE email_address = :email_address";
        Map<String, Object> params = new HashMap<>();
        params.put("email_address", customer.getEmailAddress());
        Integer count = namedParameterJdbcTemplate.queryForObject(selectSql, params, Integer.class);
        return count != null && count >= 0;
    }

    private void updateCustomer(Customer customer) {
        String updateSql = "UPDATE customer SET nickname = :nickname WHERE email_address = :email_address";
        Map<String, Object> params = new HashMap<>();
        params.put("email_address", customer.getEmailAddress());
        params.put("nickname", customer.getNickname());
        namedParameterJdbcTemplate.update(updateSql, params);
    }

    private void insertCustomer(Customer customer) {
        String insertSql = "INSERT INTO customer (email_address, nickname) VALUES (:email_address, :nickname)";
        Map<String, Object> params = new HashMap<>();
        params.put("email_address", customer.getEmailAddress());
        params.put("nickname", customer.getNickname());
        namedParameterJdbcTemplate.update(insertSql, params);
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
