package org.prgrms.kdt.repository;

import org.prgrms.kdt.domain.Customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Optional;

@Profile("prod")
@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static final Logger logger = LoggerFactory.getLogger(MapVoucherRepository.class);

    public JdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Customer> saveCustomer(Customer customer) {
        GeneratedKeyHolder customerIdHolder = new GeneratedKeyHolder();
        try {
            String sql = "insert into customer (email) values (:email)";
            jdbcTemplate.update(sql, toParamSource(customer), customerIdHolder);
        } catch (DataAccessException e) {
            logger.error("[Repository] <saveCustomer> : ", e);
            return Optional.empty();
        }
        Customer savedCustomer = new Customer(customerIdHolder.getKey().longValue(), customer.getEmail());
        logger.info("[Repository] save {}", savedCustomer);
        return Optional.of(savedCustomer);
    }

    @Override
    public Optional<Customer> getCustomerById(long customerId) {
        Customer returnedCustomer = null;
        try {
            String sql = "select id, email from customer where id = :id";
            returnedCustomer = jdbcTemplate.queryForObject(sql, Collections.singletonMap("id", customerId), new CustomerMapper());
        } catch (DataAccessException e) {
            logger.error("[Repository] <getCustomerById> : ", e);
            return Optional.empty();
        }
        logger.info("[Repository] get customer {}", returnedCustomer);
        return Optional.ofNullable(returnedCustomer);
    }

    @Override
    public Optional<Customer> getCustomerByEmail(String email) {
        Customer returnedCustomer = null;
        try {
            String sql = "select id, email from customer where email = :email";
            returnedCustomer = jdbcTemplate.queryForObject(sql, Collections.singletonMap("email", email), new CustomerMapper());
        } catch (DataAccessException e) {
            logger.error("[Repository] <getCustomerByEmail> : ", e);
            return Optional.empty();
        }
        logger.info("[Repository] get customer {}", returnedCustomer);
        return Optional.ofNullable(returnedCustomer);
    }

    private SqlParameterSource toParamSource(Customer customer) {
        return new MapSqlParameterSource()
                .addValue("id", customer.getId())
                .addValue("email", customer.getEmail());
    }

    class CustomerMapper implements RowMapper<Customer> {
        public Customer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Customer customer = new Customer();
            customer.setId(resultSet.getLong("id"));
            customer.setEmail(resultSet.getString("email"));
            return customer;
        }
    }
}
