package com.kdt.commandLineApp.customer;

import com.kdt.commandLineApp.exception.WrongCustomerParamsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.*;

import static com.kdt.commandLineApp.UUIDConverter.toUUID;

@Repository
@Profile("db")
public class JdbcCustomerRepository implements CustomerRepository{
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i)->{
        try {
            UUID customerId = toUUID(resultSet.getBytes("cid"));
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
    public Optional<Customer> get(UUID customerId) {
        return Optional.ofNullable(
                namedParameterJdbcTemplate.queryForObject(
                "select * from mysql.customer where cid = UUID_TO_BIN(:customerId)",
                Collections.singletonMap("customerId", customerId.toString().getBytes()),
                customerRowMapper
        ));
    }

    @Override
    public void add(Customer customer) {
        Map<String,Object> paramMap = new HashMap<>() {{
            put("customerId", customer.getCustomerId().toString().getBytes());
            put("name", customer.getName());
            put("age", customer.getAge());
            put("sex", customer.getSex());
        }};

        namedParameterJdbcTemplate.update(
                "insert into mysql.customer(cid, name, age, sex) values(UUID_TO_BIN(:customerId),:name,:age,:sex)",
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
