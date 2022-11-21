package org.prgms.springbootbasic.repository.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.prgms.springbootbasic.domain.customer.Customer;
import org.prgms.springbootbasic.exception.NoAffectedRowException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.*;

@Repository
public class JdbcTemplateCustomerRepository implements CustomerRepository {

    private static final String[] CUSTOMERS_COLUMNS = {"customer_id", "name", "email", "created_at", "last_login_at"};
    private static final RowMapper<Customer> customerRowMapper =
            (rs, rowNum) -> {
                Customer customer = new Customer(UUID.fromString(rs.getString(CUSTOMERS_COLUMNS[0])),
                        rs.getString(CUSTOMERS_COLUMNS[1]),
                        rs.getString(CUSTOMERS_COLUMNS[2]));
                customer.setCreatedAt(rs.getTimestamp(CUSTOMERS_COLUMNS[3]).toLocalDateTime());
                if (rs.getTimestamp(CUSTOMERS_COLUMNS[4]) != null) {
                    customer.setLastLoginAt(rs.getTimestamp(CUSTOMERS_COLUMNS[4]).toLocalDateTime());
                }
                return customer;
            };

    private Map<String, Object> toParamMap(Customer customer) {
        return new HashMap<>() {{
            put("customerId", customer.getCustomerId().toString());
            put("lastLoginAt", customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null);
        }};
    }
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;
    private final SimpleJdbcInsert jdbcInsert;
    private final Logger logger = LoggerFactory.getLogger(JdbcTemplateCustomerRepository.class);

    public JdbcTemplateCustomerRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getJdbcTemplate())
                .usingColumns(Arrays.copyOfRange(CUSTOMERS_COLUMNS, 0, 3))
                .withTableName("CUSTOMERS");
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public Customer save(Customer customer) {
        try {
            jdbcInsert.execute(new BeanPropertySqlParameterSource(customer));
        } catch (DuplicateKeyException e) {
            logger.error("Duplicated key exception occurred");
        }

        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(customer);
        int update = jdbcTemplate.update("UPDATE CUSTOMERS SET name = :name, email = :email, last_login_at = :lastLoginAt WHERE customer_id = :customerId",
                namedParameters);
        if (update != 1) {
            throw new NoAffectedRowException("Noting was updated");
        }
        return customer;
    }


    @Override
    public Customer updateLastLoginAt(Customer customer) {
        int update = jdbcTemplate.update("UPDATE CUSTOMERS SET last_login_at = :lastLoginAt WHERE customer_id = :customerId",
                toParamMap(customer));
        if (update != 1) {
            throw new NoAffectedRowException("Noting was updated");
        }
        return customer;
    }

    @Override
    public int count() {
        return jdbcTemplate.queryForObject("SELECT count(*) from CUSTOMERS", Collections.emptyMap(), Integer.class);
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("SELECT * FROM CUSTOMERS", customerRowMapper);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject
                    ("SELECT * FROM CUSTOMERS WHERE CUSTOMER_ID = :customerId"
                            , Collections.singletonMap("customerId", customerId.toString())
                            , customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject
                    ("SELECT * FROM CUSTOMERS WHERE EMAIL = :email"
                            , Collections.singletonMap("email", email)
                            , customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }

    }


    @Override
    public List<Customer> findByVoucherId(UUID voucherId) {
        try {
            return jdbcTemplate.query
                    ("""
                    SELECT
                        C.CUSTOMER_ID, C.NAME, C.EMAIL, C.LAST_LOGIN_AT, C.CREATED_AT
                    FROM voucher.VOUCHERS V
                        LEFT JOIN voucher.CUSTOMERS C
                        ON C.CUSTOMER_ID = V.CUSTOMER_ID
                    WHERE V.VOUCHER_ID = :voucherId;
                    """
                    , Collections.singletonMap("voucherId", voucherId.toString())
                    , customerRowMapper);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Collections.emptyList();
        }
    }

    @Override
    public UUID deleteById(UUID customerId) {
        jdbcTemplate.update("DELETE FROM CUSTOMERS WHERE CUSTOMER_ID =:customerId",
                Collections.singletonMap("customerId", customerId));
        return customerId;
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM CUSTOMERS", Collections.emptyMap());
    }

}
