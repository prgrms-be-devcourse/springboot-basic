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
        return jdbcTemplate.query("select * from customer", customerRowMapper);
    }

    @Override
    public Customer save(Customer customer) {
        jdbcTemplate.update("INSERT INTO customer(id, name, email, isBlack) VALUES(UUID_TO_BIN(?), ?, ?, ?)", customer.getCustomerId().toString(),
                                            customer.getName(),
                                            customer.getEmail(),
                                            customer.getIsBlack());
        return customer;
    }
    @Override
    public Optional<Customer> findById(UUID customerId) {
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from customer where id = UUID_TO_BIN(?)", customerRowMapper, customerId.toString()));
        }catch (EmptyResultDataAccessException e){
            log.error("Not exists");
            return Optional.empty();
        }
    }
    @Override
    public Optional<Customer> deleteById(UUID customerId) {
        jdbcTemplate.update("delete from customer where id = UUID_TO_BIN(?)", customerId.toString());
        Optional<Customer> customer = findById(customerId);
        return customer;
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from customer where email = ?", customerRowMapper, email));
        }catch (EmptyResultDataAccessException e){
            log.error("Not Exists");
            return Optional.empty();
        }
    }
}
