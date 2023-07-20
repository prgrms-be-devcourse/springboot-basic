package com.wonu606.vouchermanager.repository.customer.store;

import com.wonu606.vouchermanager.repository.customer.query.CustomerCreateQuery;
import com.wonu606.vouchermanager.repository.customer.resultset.CustomerCreateResultSet;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CustomerJdbcStore implements CustomerStore {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CustomerJdbcStore(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public CustomerCreateResultSet insert(CustomerCreateQuery query) {
        String insertionSql = "INSERT INTO customer (email, nickname) VALUES (:email, :nickname)";
        Map<String, Object> params = new HashMap<>();
        params.put("email", query.getEmail());
        params.put("nickname", query.getNickname());
        return new CustomerCreateResultSet(namedParameterJdbcTemplate.update(insertionSql, params));
    }

    @Override
    public void deleteByCustomerId(String email) {
        String deletionSql = "DELETE FROM customer WHERE email = :email";
        Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        namedParameterJdbcTemplate.update(deletionSql, params);
    }
}
