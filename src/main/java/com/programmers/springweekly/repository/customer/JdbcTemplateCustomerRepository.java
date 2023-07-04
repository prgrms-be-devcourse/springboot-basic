package com.programmers.springweekly.repository.customer;

import com.programmers.springweekly.domain.customer.Customer;
import com.programmers.springweekly.domain.customer.CustomerType;
import com.programmers.springweekly.dto.CustomerUpdateDto;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile({"test", "local"})
public class JdbcTemplateCustomerRepository implements CustomerRepository {

    private NamedParameterJdbcTemplate template;

    public JdbcTemplateCustomerRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Customer save(Customer customer) {
        String sql = "insert into customers values (:customerId, :customerName, :customerEmail, :customerType)";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("customerId", customer.getCustomerId().toString())
                .addValue("customerName", customer.getCustomerName())
                .addValue("customerEmail", customer.getCustomerEmail())
                .addValue("customerType", customer.getCustomerType().toString());

        template.update(sql, param);

        return customer;
    }

    @Override
    public Customer update(UUID customerId, CustomerUpdateDto customerUpdateDto) {
        String sql = "update customers set customer_name = :customerName, customer_email = :customerEmail, customer_type = :customerType where customer_id = :customerId";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("customerName", customerUpdateDto.getCustomerName())
                .addValue("customerEmail", customerUpdateDto.getCustomerEmail())
                .addValue("customerType", customerUpdateDto.getCustomerType().toString())
                .addValue("customerId", customerId.toString());

        template.update(sql, param);

        return new Customer(
                customerId,
                customerUpdateDto.getCustomerName(),
                customerUpdateDto.getCustomerEmail(),
                customerUpdateDto.getCustomerType()
        );
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        String sql = "select * from customers where customer_id = :customerId";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("customerId", customerId.toString());

        Customer customer = template.queryForObject(sql, param, customerRowMapper());

        return Optional.ofNullable(customer);
    }

    @Override
    public List<Customer> findAll() {
        String sql = "select * from customers";

        return template.query(sql, customerRowMapper());
    }

    @Override
    public List<Customer> getBlackList() {
        String sql = "select * from customers where customer_type = :customerType";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("customerType", CustomerType.BLACKLIST.toString());

        return template.query(sql, param, customerRowMapper());
    }

    @Override
    public void deleteById(UUID customerId) {
        String sql = "delete from customers where customer_id = :customerId";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("customerId", customerId.toString());

        template.update(sql, param);
    }

    @Override
    public void deleteAll() {
        String sql = "delete from customers where 1=:one";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("one", 1);

        template.update(sql, param);
    }

    private RowMapper<Customer> customerRowMapper() {
        return ((resultSet, rowMap) ->
                new Customer(
                        UUID.fromString(resultSet.getString("customer_id")),
                        resultSet.getString("customer_name"),
                        resultSet.getString("customer_email"),
                        CustomerType.valueOf(resultSet.getString("customer_type"))
                )
        );
    }
}
