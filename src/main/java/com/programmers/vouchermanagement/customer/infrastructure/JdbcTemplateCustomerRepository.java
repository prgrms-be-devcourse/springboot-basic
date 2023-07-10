package com.programmers.vouchermanagement.customer.infrastructure;

import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.customer.domain.CustomerRepository;
import com.programmers.vouchermanagement.customer.domain.CustomerType;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Primary
@Repository
public class JdbcTemplateCustomerRepository implements CustomerRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateCustomerRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Customer save(Customer customer) {
        String sql = "insert into customer (id, name, type) values (?, ?, ?)";
        jdbcTemplate.update(sql,
                customer.getId().toString(),
                customer.getName(),
                customer.getType().toString());
        return customer;
    }

    @Override
    public Optional<Customer> findById(UUID id) {
        String sql = "select * from customer where id = ?";
        try {
            Customer customer = jdbcTemplate.queryForObject(sql, customerRowMapper(), id.toString());
            return Optional.of(customer);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Customer> findAll() {
        String sql = "select * from customer";
        return jdbcTemplate.query(sql, customerRowMapper());
    }

    @Override
    public void update(Customer customer) {
        String sql = "update customer set type = ? where id = ?";
        jdbcTemplate.update(sql,
                customer.getType().toString(),
                customer.getId().toString());
    }

    @Override
    public void deleteById(UUID id) {
        String sql = "delete from customer where id = ?";
        jdbcTemplate.update(sql, id.toString());
    }

    private RowMapper<Customer> customerRowMapper() {
        return (rs, rowNum) -> {
            UUID id = UUID.fromString(rs.getString("id"));
            String name = rs.getString("name");
            CustomerType type = CustomerType.valueOf(rs.getString("type"));
            return new Customer(id, name, type);
        };
    }
}
