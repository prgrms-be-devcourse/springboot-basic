package com.devcourse.voucherapp.repository;

import com.devcourse.voucherapp.entity.CustomerType;
import com.devcourse.voucherapp.entity.customer.Customer;
import com.devcourse.voucherapp.exception.ExistedCustomerException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.sql.DataSource;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
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
        String nickname = customer.getNickname();
        Optional<Customer> existedCustomer = findCustomerByNickname(nickname);

        if (existedCustomer.isPresent()) {
            throw new ExistedCustomerException(nickname);
        }

        String sql = "insert into customer(id, type, nickname) values (:id, :typeNumber, :nickname)";
        template.update(sql, getParameterSource(customer));

        return customer;
    }

    @Override
    public Optional<Customer> findCustomerByNickname(String nickname) {
        String sql = "select id, type, nickname from customer where nickname = :nickname";

        try {
            Customer customer = template.queryForObject(sql, getParameterMap(nickname), getCustomerRowMapper());

            return Optional.of(customer);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Customer> findAllCustomers() {
        String sql = "select id, type, nickname from customer";

        return template.query(sql, getCustomerRowMapper());
    }

    private SqlParameterSource getParameterSource(Customer customer) {
        return new MapSqlParameterSource()
                .addValue("id", customer.getId().toString())
                .addValue("typeNumber", customer.getType().getNumber())
                .addValue("nickname", customer.getNickname());
    }

    private Map<String, String> getParameterMap(String nickname) {
        return Map.of("nickname", nickname);
    }

    private RowMapper<Customer> getCustomerRowMapper() {
        return (resultSet, rowNum) -> {
            String id = resultSet.getString("id");
            int typeNumber = resultSet.getInt("type");
            String nickname = resultSet.getString("nickname");

            return Customer.from(UUID.fromString(id), CustomerType.from(typeNumber), nickname);
        };
    }
}
