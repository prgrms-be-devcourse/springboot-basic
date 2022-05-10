package com.kdt.commandLineApp.service.customer;

import com.kdt.commandLineApp.exception.WrongCustomerParamsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.*;

@Repository
@Primary
public class JdbcCustomerRepository implements CustomerRepository{
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i)->{
        try {
            long customerId = resultSet.getLong("id");
            String name = resultSet.getString("name");
            String sex = resultSet.getString("sex");
            int age = Optional.ofNullable(resultSet.getInt("age")).orElseThrow(()-> new WrongCustomerParamsException());
            return new Customer(customerId, name, age, sex);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    };

    @Autowired
    public JdbcCustomerRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Customer> getAllBlacklist() throws IOException {
        return null;
    }

    @Override
    public List<Customer> getAll() {
        return namedParameterJdbcTemplate.query(
                        "select * from mysql.customer",
                        Collections.emptyMap(),
                        customerRowMapper
                );
    }

    @Override
    public Optional<Customer> get(long id) {
        return Optional.ofNullable(
                namedParameterJdbcTemplate.queryForObject(
                "select * from mysql.customer where id = :customerId",
                Collections.singletonMap("customerId", id),
                customerRowMapper
        ));
    }

    @Override
    public void add(Customer customer) {
        Map<String,Object> paramMap = new HashMap<>();

        paramMap.put("name", customer.getName());
        paramMap.put("age", customer.getAge());
        paramMap.put("sex", customer.getSex());
        namedParameterJdbcTemplate.update(
                "insert into mysql.customer(name, age, sex) values(:name,:age,:sex)",
                paramMap
        );
    }

    public void deleteAll() {
        namedParameterJdbcTemplate.update(
                "delete from mysql.customer",
                Collections.emptyMap()
        );
    }
}
