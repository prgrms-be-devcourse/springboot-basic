package org.prgrms.vouchermanager.repository.customer;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.vouchermanager.domain.customer.Customer;
import org.prgrms.vouchermanager.util.UuidUtil;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.util.*;

@Repository
@Profile("jdbc")
@Slf4j
public class JdbcCustomerRepository implements CustomerRepositroy{
    private final String INSERT_CUSTOMER = "INSERT INTO customer(id, name, email, isBlack) VALUES(UUID_TO_BIN(?), ?, ?, ?)";
    private final String SELECT_BY_ID = "select * from customer where id = UUID_TO_BIN(?)";
    private final String SELECT_ALL = "select * from customer";
    private final String DELETE_BY_ID = "delete from customer where id = UUID_TO_BIN(?)";
    private final String SELECT_BY_EMAIL = "select * from customer where email = ?";
    private final JdbcTemplate jdbcTemplate;
    public JdbcCustomerRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        UUID customerId = UuidUtil.toUUID(resultSet.getBytes("id"));
        String customerName = resultSet.getString("name");
        String email = resultSet.getString("email");
        boolean isBlack = resultSet.getBoolean("isBlack");
        return new Customer(customerId, customerName, email, isBlack);
    };
    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(SELECT_ALL, customerRowMapper);
    }

    @Override
    public Customer save(Customer customer) {
        jdbcTemplate.update(INSERT_CUSTOMER, customer.getCustomerId().toString(),
                                            customer.getName(),
                                            customer.getEmail(),
                                            customer.getIsBlack());
        return customer;
    }
    @Override
    public Optional<Customer> findById(UUID customerId) {
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_ID, customerRowMapper, customerId.toString()));
        }catch (EmptyResultDataAccessException e){
            log.error("Not exists");
            return Optional.empty();
        }
    }
    @Override
    public Optional<Customer> deleteById(UUID customerId) {
        jdbcTemplate.update(DELETE_BY_ID, customerId.toString());
        Optional<Customer> customer = findById(customerId);
        return customer;
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_EMAIL, customerRowMapper, email));
        }catch (EmptyResultDataAccessException e){
            log.error("Not Exists");
            return Optional.empty();
        }
    }
}
