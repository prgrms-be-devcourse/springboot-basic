package com.programmers.customer.repository;

import com.programmers.customer.domain.Customer;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Customer save(Customer customer) {
        String sql = "insert into customer (id, name, created_at, modified_at)" +
                " values(:id, :name, :createdAt, :modifiedAt)";
        jdbcTemplate.update(sql, converParameterToMap(customer));
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        return null;
    }

    @Override
    public List<Customer> findAll() {
        return null;
    }

    @Override
    public Customer findById(UUID customerId) {
        return null;
    }

    @Override
    public void deleteById(UUID customerId) {

    }

    private Map<String, Object> converParameterToMap (Customer customer) {
        return new HashMap<>() {{
            put("id", customer.getCustomerId().toString());
            put("name", customer.getName());
            put("createdAt", Timestamp.valueOf(customer.getCreatedAt()));
            Timestamp modifiedAt = null;
            if (customer.getModifiedAt() != null)
                modifiedAt = Timestamp.valueOf(customer.getModifiedAt());
            put("modifiedAt", modifiedAt);
        }};
    }

    private RowMapper<Customer> customerRowMapper() {
        return (rs, rowNum) -> {
            UUID customerId = UUID.fromString(rs.getString("id"));
            String name = rs.getString("name");
            LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
            LocalDateTime modifiedAt = null;
            if (rs.getTimestamp("modified_at") != null)
                modifiedAt = rs.getTimestamp("modified_at").toLocalDateTime();
            return new Customer(customerId, name, createdAt, modifiedAt);
        };
    }
}
