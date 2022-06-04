package org.devcourse.voucher.application.customer.repository;

import org.devcourse.voucher.application.customer.model.Customer;
import org.devcourse.voucher.application.customer.model.Email;
import org.devcourse.voucher.core.exception.DataInsertFailException;
import org.devcourse.voucher.core.exception.DataUpdateFailException;
import org.devcourse.voucher.core.exception.ErrorType;
import org.devcourse.voucher.core.exception.NotFoundException;
import org.devcourse.voucher.core.utils.JdbcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);

    public JdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        UUID customerId = JdbcUtils.toUUID(resultSet.getBytes("customer_id"));
        String name = resultSet.getString("name");
        Email email = new Email(resultSet.getString("email"));
        return new Customer(customerId, name, email);
    };

    private static Map<String, Object> toIdMap(UUID customerId) {
        return Collections.singletonMap("customerId", customerId.toString().getBytes(StandardCharsets.UTF_8));
    }

    private static Map<String, Object> toParamMap(Customer customer) {
        return Map.of(
                "customerId", customer.getCustomerId().toString().getBytes(),
                "name", customer.getName(),
                "email", customer.getEmail().getAddress()
        );
    }

    @Override
    public Customer insert(Customer customer) {
        logger.info("Repository : Record a voucher insert");
        int inserted = jdbcTemplate.update("INSERT INTO customers(customer_id, name, email) VALUES(UUID_TO_BIN(:customerId), :name, :email)",
                toParamMap(customer));
        if (inserted != 1) {
            throw new DataInsertFailException("Nothing was inserted");
        }
        return customer;
    }

    @Override
    public List<Customer> findAll(Pageable pageable) {
        logger.info("Repository : Record a voucher read");
        List<Customer> customers = jdbcTemplate.query("select * from customers", customerRowMapper);
        int st = (int) pageable.getOffset();
        int ed = Math.min((st + pageable.getPageSize()), customers.size());
        return customers.subList(st, ed);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            "select * from customers where customer_id = UUID_TO_BIN(:customerId)",
                            toIdMap(customerId),
                            customerRowMapper
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(ErrorType.NOT_FOUND_CUSTOMER, customerId);
        }
    }

    @Override
    public Customer update(Customer customer) {
        logger.info("Repository : Record a voucher update");
        int updated = jdbcTemplate.update("UPDATE customers SET name = :name, email = :email " +
                "WHERE customer_id = UUID_TO_BIN(:customerId)", toParamMap(customer));
        if (updated != 1) {
            throw new DataUpdateFailException("Nothing was updated");
        }
        return customer;
    }

    @Override
    public void deleteAll() {
        logger.info("Repository : Record a voucher delete");
        jdbcTemplate.update("DELETE FROM customers", Collections.emptyMap());
    }

    @Override
    public void deleteById(UUID customerId) {
        int delete = jdbcTemplate.update(
                "DELETE FROM customers WHERE customer_id = UUID_TO_BIN(:customerId)",
                toIdMap(customerId)
        );
        if (delete != 1) {
            throw new NotFoundException(ErrorType.NOT_FOUND_VOUCHER, customerId);
        }
    }
}
