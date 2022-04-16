package org.prgrms.kdt.domain.customer.repository;

import org.prgrms.kdt.domain.customer.model.Customer;
import org.prgrms.kdt.domain.customer.model.CustomerType;
import org.prgrms.kdt.util.UuidUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class JdbcCustomerRepository implements CustomerRepository{
    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM customer",
                Collections.emptyMap(),
                customerRowMapper());
    }

    @Override
    public UUID save(Customer customer) {
        int update = jdbcTemplate.update(
                "INSERT INTO customer(customer_id, customer_type, name, email, created_date, modified_date) " +
                        "VALUES (UNHEX(REPLACE(:customerId, '-', '')), :customerType, :name, :email, :createdDate, :modifiedDate)",
                toParamMap(customer));
        logger.info("update row {}", update);
        if(update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return customer.getCustomerId();
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            Customer customer = jdbcTemplate.queryForObject("SELECT * FROM customer WHERE customer_id = :customerId",
                    Collections.singletonMap("customerId", UuidUtils.UuidToByte(customerId)),
                    customerRowMapper());
            return Optional.of(customer);
        } catch (EmptyResultDataAccessException e) {
            logger.info("Find by id method got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public int updateById(Customer customer) {
        int update = jdbcTemplate.update("UPDATE customer " +
                        "SET customer_type = :customerType, name = :name, email = :email, modified_date = :modifiedDate " +
                        "WHERE customer_id = UNHEX(REPLACE(:customerId, '-', ''))",
                toParamMap(customer));
        return update;
    }

    @Override
    public void deleteById(UUID customerId) {
        jdbcTemplate.update("DELETE FROM customer WHERE customer_id = :customerId",
                Collections.singletonMap("customerId", customerId));
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM customer", Collections.emptyMap());
    }

    private Map<String, Object> toParamMap(Customer customer) {
        Map<String, Object> paramMap = new HashMap<>() {{
            put("customerId", customer.getCustomerId().toString().getBytes());
            put("customerType", customer.getCustomerType().name());
            put("name", customer.getName());
            put("email", customer.getEmail());
            put("createdDate", Timestamp.valueOf(customer.getCreatedDate()));
            put("modifiedDate", customer.getModifiedDate() != null ?
                    Timestamp.valueOf(customer.getModifiedDate()) : null);
        }};
        return paramMap;
    }

    private RowMapper<Customer> customerRowMapper() {
        return (rs, rowNum) -> {
            UUID customerId = UuidUtils.byteToUUID(rs.getBytes("customer_id"));
            CustomerType customer_type = CustomerType.valueOf(rs.getString("customer_type"));
            String name = rs.getString("name");
            String email = rs.getString("email");
            LocalDateTime createdDate = rs.getTimestamp("created_date").toLocalDateTime();
            LocalDateTime modifiedDate = rs.getTimestamp("modified_date") != null ?
                    rs.getTimestamp("modified_date").toLocalDateTime() : null;

            return new Customer(customerId, name, email, customer_type, createdDate, modifiedDate);
        };
    }
}
