package com.programmers.vouchermanagement.repository.customer;

import com.programmers.vouchermanagement.domain.customer.Customer;
import com.programmers.vouchermanagement.dto.customer.GetCustomersRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class JdbcTemplateCustomerRepository implements CustomerRepository {
    private final NamedParameterJdbcTemplate template;

    @Autowired
    public JdbcTemplateCustomerRepository(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    @Override
    public void save(Customer customer) {
        String sql = "INSERT INTO customers (email) VALUES (:email)";

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("email", customer.getEmail());

        template.update(sql, params);
    }

    @Override
    public void saveAll(List<Customer> customers) {
        String sql = "INSERT INTO customers (email) VALUES (:email)";

        template.batchUpdate(sql, customers.stream()
                .map(customer -> new MapSqlParameterSource()
                        .addValue("email", customer.getEmail()))
                .toArray(SqlParameterSource[]::new));
    }

    @Override
    public List<Customer> findAll(GetCustomersRequestDto request) {
        String sql = "SELECT * FROM customers";

        if (request.getBlacklisted() != null) {
            sql += " WHERE blacklisted = :blacklisted";
        }

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("blacklisted", request.getBlacklisted());

        return template.query(sql, getCustomerRowMapper());
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM customers";
        template.update(sql, new MapSqlParameterSource());
    }

    private RowMapper<Customer> getCustomerRowMapper() {
        return (rs, rowNum) -> {
            UUID id = UUID.fromString(rs.getString("id"));
            String email = rs.getString("email");
            boolean blacklisted = rs.getBoolean("blacklisted");

            return new Customer(id, email, blacklisted);
        };
    }
}
