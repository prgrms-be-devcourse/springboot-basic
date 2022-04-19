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
        int savedRows = jdbcTemplate.update(
                "INSERT INTO customer(customer_id, customer_type, name, email, created_date, modified_date) " +
                        "VALUES (UNHEX(REPLACE(:customerId, '-', '')), :customerType, :name, :email, :createdDate, :modifiedDate)",
                toParamMap(customer));
        if(savedRows != 1) {
            throw new RuntimeException("저장된 컬럼이 없습니다.");
        }
        return customer.getCustomerId();
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            Customer customer = jdbcTemplate.queryForObject("SELECT * FROM customer WHERE customer_id = UNHEX(REPLACE(:customerId, '-', ''))",
                    Collections.singletonMap("customerId", UuidUtils.UuidToByte(customerId)),
                    customerRowMapper());
            return Optional.of(customer);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Find by id method got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByVoucherId(UUID voucherId) {
        try{
            Customer customer = jdbcTemplate.queryForObject("SELECT * FROM customer c JOIN voucher v on c.customer_id = v.customer_id WHERE v.voucher_id = UNHEX(REPLACE(:voucherId, '-', ''))",
                    Collections.singletonMap("voucherId", UuidUtils.UuidToByte(voucherId)),
                    customerRowMapper());
            return Optional.of(customer);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Find By Voucher id got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public List<Customer> findByCustomerType(CustomerType customerType) {
        return jdbcTemplate.query("SELECT * FROM customer WHERE customer_type = :customerType",
                Collections.singletonMap("customerType", CustomerType.getValue(customerType)),
                customerRowMapper());
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try{
            Customer customer = jdbcTemplate.queryForObject("SELECT * FROM customer WHERE email = :email",
                    Collections.singletonMap("email", email),
                    customerRowMapper());
            return Optional.of(customer);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Find By Voucher id got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public int updateById(Customer customer) {
        int updatedRows = jdbcTemplate.update("UPDATE customer " +
                        "SET customer_type = :customerType, name = :name, email = :email, modified_date = :modifiedDate " +
                        "WHERE customer_id = UNHEX(REPLACE(:customerId, '-', ''))",
                toParamMap(customer));
        if(updatedRows == 0) {
            throw new RuntimeException("업데이트된 컬럼이 없습니다.");
        }
        return updatedRows;
    }

    @Override
    public int deleteById(UUID customerId) {
        int deletedRows = jdbcTemplate.update("DELETE FROM customer WHERE customer_id = UNHEX(REPLACE(:customerId, '-', ''))",
                Collections.singletonMap("customerId", UuidUtils.UuidToByte(customerId)));
        if(deletedRows == 0) {
            throw new RuntimeException("삭제된 컬럼이 없습니다.");
        }
        return deletedRows;
    }

    @Override
    public int deleteAll() {
        return jdbcTemplate.update("DELETE FROM customer", Collections.emptyMap());
    }

    private Map<String, Object> toParamMap(Customer customer) {
        Map<String, Object> paramMap = new HashMap<>() {{
            put("customerId", UuidUtils.UuidToByte(customer.getCustomerId()));
            put("customerType", CustomerType.getValue(customer.getCustomerType()));
            put("name", customer.getName());
            put("email", customer.getEmail());
            put("createdDate", Timestamp.valueOf(customer.getCreatedDate()));
            put("modifiedDate", customer.getModifiedDate() != null ?
                    Timestamp.valueOf(customer.getModifiedDate()) : null);
        }};
        return paramMap;
    }

    private RowMapper<Customer> customerRowMapper() {
        RowMapper<Customer> rowMapper = (rs, rowNum) -> {
            UUID customerId = UuidUtils.byteToUUID(rs.getBytes("customer_id"));
            CustomerType customerType = CustomerType.findCustomerType(rs.getString("customer_type"));
            String name = rs.getString("name");
            String email = rs.getString("email");
            LocalDateTime createdDate = rs.getTimestamp("created_date").toLocalDateTime();
            LocalDateTime modifiedDate = rs.getTimestamp("modified_date") != null ?
                    rs.getTimestamp("modified_date").toLocalDateTime() : null;

            return new Customer(customerId, name, email, customerType, createdDate, modifiedDate);
        };

        return rowMapper;
    }
}
