package com.devcourse.voucherapp.repository;

import com.devcourse.voucherapp.entity.customer.Customer;
import javax.sql.DataSource;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
@Profile("dev")
public class JdbcCustomerRepository implements CustomerRepository {

    private final NamedParameterJdbcTemplate template;

    public JdbcCustomerRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Customer create(Customer customer) {
        String sql = "insert into customer(id, type, nickname) values (:id, :typeNumber, :nickname)";
        template.update(sql, getParameterSource(customer));

        return customer;
    }

    private SqlParameterSource getParameterSource(Customer customer) {
        return new MapSqlParameterSource()
                .addValue("id", customer.getId().toString())
                .addValue("typeNumber", customer.getType().getNumber())
                .addValue("nickname", customer.getNickname());
    }
}
