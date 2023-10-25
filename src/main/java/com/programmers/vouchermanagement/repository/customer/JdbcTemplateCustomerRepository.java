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
    public List<Customer> findAll(GetCustomersRequestDto request) {
        String sql = "SELECT * FROM customers";

        if (request.getBlacklisted() != null) {
            sql += " WHERE blacklisted = :blacklisted";
        }

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("blacklisted", request.getBlacklisted());

        return template.query(sql, getCustomerRowMapper());
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
