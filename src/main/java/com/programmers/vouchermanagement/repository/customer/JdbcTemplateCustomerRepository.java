package com.programmers.vouchermanagement.repository.customer;

import com.programmers.vouchermanagement.domain.customer.Customer;
import com.programmers.vouchermanagement.dto.customer.request.GetCustomersRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
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
        String sql = "INSERT INTO customers (email, blacklisted) VALUES (:email, :blacklisted)";

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("email", customer.getEmail())
                .addValue("blacklisted", customer.isBlacklisted());

        template.update(sql, params);
    }

    @Override
    public void saveAll(List<Customer> customers) {
        String sql = "INSERT INTO customers (email, blacklisted) VALUES (:email, :blacklisted)";

        template.batchUpdate(sql, customers.stream()
                .map(customer -> new MapSqlParameterSource()
                        .addValue("email", customer.getEmail())
                        .addValue("blacklisted", customer.isBlacklisted())
                )
                .toArray(SqlParameterSource[]::new));
    }

    @Override
    public Optional<Customer> findById(UUID id) {
        String sql = "SELECT * FROM customers WHERE id = :id";

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id.toString());

        try {
            Customer customer = template.queryForObject(sql, params, getCustomerRowMapper());
            return Optional.ofNullable(customer);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        String sql = "SELECT * FROM customers WHERE email = :email";

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("email", email);

        try {
            Customer customer = template.queryForObject(sql, params, getCustomerRowMapper());
            return Optional.ofNullable(customer);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Customer> findAll(GetCustomersRequestDto request) {
        String sql = "SELECT * FROM customers";
        MapSqlParameterSource params = new MapSqlParameterSource();

        if (request.getBlacklisted() != null) {
            sql += " WHERE blacklisted = :blacklisted";
            params.addValue("blacklisted", request.getBlacklisted());
        }

        return template.query(sql, params, getCustomerRowMapper());
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
