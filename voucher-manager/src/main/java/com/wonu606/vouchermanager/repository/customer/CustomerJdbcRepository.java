package com.wonu606.vouchermanager.repository.customer;

import com.wonu606.vouchermanager.repository.customer.query.CustomerCreateQuery;
import com.wonu606.vouchermanager.repository.customer.resultset.CustomerCreateResultSet;
import com.wonu606.vouchermanager.repository.customer.resultset.CustomerResultSet;
import com.wonu606.vouchermanager.repository.customer.rowmapper.CustomerResultSetRowMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CustomerJdbcRepository implements CustomerRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final CustomerResultSetRowMapper customerResultSetRowMapper;

    public CustomerJdbcRepository(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

        customerResultSetRowMapper = new CustomerResultSetRowMapper();
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
    public List<CustomerResultSet> findAll() {
        String selectionSql = "SELECT email, nickname FROM customer";
        return namedParameterJdbcTemplate.query(selectionSql, customerResultSetRowMapper);
    }

    @Override
    public void deleteByCustomerId(String email) {
        String deletionSql = "DELETE FROM customer WHERE email = :email";
        Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        namedParameterJdbcTemplate.update(deletionSql, params);
    }
}
