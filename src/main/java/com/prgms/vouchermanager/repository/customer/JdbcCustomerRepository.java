package com.prgms.vouchermanager.repository.customer;

import com.prgms.vouchermanager.domain.customer.Customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static com.prgms.vouchermanager.repository.customer.CustomerQueryType.*;


@Repository
@Slf4j
@Profile("jdbc")
public class JdbcCustomerRepository implements CustomerRepository {
    private final NamedParameterJdbcTemplate template;

    public JdbcCustomerRepository(DataSource dataSource) {
        template = new NamedParameterJdbcTemplate(dataSource);
    }

    public Customer save(Customer customer) {

        SqlParameterSource forSequenceParam = new MapSqlParameterSource();
        Long sequence = template.queryForObject(COUNT.getQuery(), forSequenceParam, Long.class);
        customer.setId(sequence+1);

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", customer.getId())
                .addValue("name", customer.getName())
                .addValue("email", customer.getEmail())
                .addValue("black_list", customer.isBlackList());


        template.update(INSERT.getQuery(), param);

        return customer;

    }

    @Override
    public void update(Customer customer) {

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", customer.getId())
                .addValue("name", customer.getName())
                .addValue("email", customer.getEmail())
                .addValue("black_list", customer.isBlackList());

        template.update(UPDATE.getQuery(), param);
    }

    @Override
    public Optional<Customer> findById(Long id) {

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", id);

        try {
            return Optional.of(template.queryForObject(SELECT_BY_ID.getQuery(), param, customerRowMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

    }

    @Override
    public List<Customer> findAll() {

        return template.query(SELECT_ALL.getQuery(), customerRowMapper());
    }

    @Override
    public void deleteById(Long id) {


        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", id);

        template.update(DELETE_BY_ID.getQuery(), param);

    }

    @Override
    public void deleteAll() {

        template.update(DELETE_ALL.getQuery(),new MapSqlParameterSource());

    }

    @Override
    public List<Customer> findBlackList() {
        return template.query(BLACKLIST.getQuery(), customerRowMapper());
    }

    private RowMapper<Customer> customerRowMapper() {
        return (rs, count) -> {
            Long id = Long.parseLong(rs.getString("id"));
            String name = rs.getString("name");
            String email = rs.getString("email");
            Boolean black_list = Boolean.valueOf(rs.getString("black_list"));

            return new Customer(id, name, email, black_list);
        };
    }
}
