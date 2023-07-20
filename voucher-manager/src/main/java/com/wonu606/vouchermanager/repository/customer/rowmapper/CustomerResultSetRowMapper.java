package com.wonu606.vouchermanager.repository.customer.rowmapper;

import com.wonu606.vouchermanager.repository.customer.resultset.CustomerResultSet;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class CustomerResultSetRowMapper implements RowMapper<CustomerResultSet> {

    @Override
    public CustomerResultSet mapRow(ResultSet rs, int rowNum) throws SQLException {
        String email = rs.getString("email");
        String nickname = rs.getString("nickname");

        return new CustomerResultSet(email, nickname);
    }
}
