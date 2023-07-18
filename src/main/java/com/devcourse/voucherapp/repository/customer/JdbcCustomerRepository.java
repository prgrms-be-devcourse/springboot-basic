package com.devcourse.voucherapp.repository.customer;

import static com.devcourse.voucherapp.entity.customer.CustomerType.BLACK;

import com.devcourse.voucherapp.entity.customer.Customer;
import com.devcourse.voucherapp.entity.customer.CustomerType;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    private final NamedParameterJdbcTemplate template;

    public JdbcCustomerRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Customer create(Customer customer) {
        String sql = "insert into customer(id, type, nickname) values (:id, :typeOption, :nickname)";
        template.update(sql, getParameterSource(customer));

        return customer;
    }

    @Override
    public Optional<Customer> findCustomerByNickname(String nickname) {
        String sql = "select id, type, nickname from customer where nickname = :nickname";

        try {
            Customer customer = template.queryForObject(sql, getParameterNickname(nickname), getCustomerRowMapper());

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

    @Override
    public Customer update(Customer customer) {
        String sql = "update customer set type = :typeOption where nickname = :nickname";
        template.update(sql, getParameterSource(customer));

        return customer;
    }

    @Override
    public int deleteByNickname(String nickname) {
        String sql = "delete from customer where nickname = :nickname";

        return template.update(sql, getParameterNickname(nickname));
    }

    @Override
    public List<Customer> findBlackListCustomers() {
        String sql = "select id, type, nickname from customer where type = :typeOption";
        String blackListTypeOption = BLACK.getOption();

        return template.query(sql, getParameterTypeOption(blackListTypeOption), getCustomerRowMapper());
    }

    private SqlParameterSource getParameterSource(Customer customer) {
        return new MapSqlParameterSource()
                .addValue("id", customer.getId().toString())
                .addValue("typeOption", customer.getType().getOption())
                .addValue("nickname", customer.getNickname());
    }

    private Map<String, String> getParameterNickname(String nickname) {
        return Map.of("nickname", nickname);
    }

    private Map<String, String> getParameterTypeOption(String typeOption) {
        return Map.of("typeOption", typeOption);
    }

    private RowMapper<Customer> getCustomerRowMapper() {
        return (resultSet, rowNum) -> {
            String id = resultSet.getString("id");
            String typeOption = resultSet.getString("type");
            String nickname = resultSet.getString("nickname");

            return Customer.from(UUID.fromString(id), CustomerType.from(typeOption), nickname);
        };
    }
}
