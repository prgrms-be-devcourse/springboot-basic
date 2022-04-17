package com.kdt.commandLineApp.customer;

import com.kdt.commandLineApp.exception.WrongCustomerParamsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.kdt.commandLineApp.UUIDConverter.toUUID;

@Repository
@Profile("db")
public class JdbcCustomerRepository implements CustomerRepository{
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final RowMapper<Customer> cutomerRowMapper = (resultSet, i)->{
        UUID customerId = toUUID(resultSet.getBytes("cid"));
        String name = resultSet.getString("name");
        String sex = resultSet.getString("sex");
        int age = resultSet.getInt("age");
        UUID voucherId = toUUID(resultSet.getBytes("vid"));

        try {
            return new Customer(customerId, name, age, sex);
        }
        catch (WrongCustomerParamsException e) {
            e.printStackTrace();
        }
        return null;
    };

    @Override
    public List<Customer> getAllBlacklist() throws IOException {
        return null;
    }

    @Override
    public List<Customer> getCustomers(UUID voucherId) {
        //VoucherId를 foreign key로 가지고 있는 Customer를 반환한다.
        return namedParameterJdbcTemplate.query(
                "select * from mysql.customer where vid = UUID_TO_BIN(:voucherId)",
                Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                cutomerRowMapper
        );
    }
}
