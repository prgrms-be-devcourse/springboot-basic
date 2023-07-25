package com.wonu606.vouchermanager.repository.customer.reader.rowmapper;

import com.wonu606.vouchermanager.repository.customer.resultset.CustomerResultSet;
import com.wonu606.vouchermanager.util.TypedRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.stereotype.Component;

@Component
public class CustomerResultSetRowMapper implements TypedRowMapper<CustomerResultSet> {

    @Override
    public CustomerResultSet mapRow(ResultSet rs, int rowNum) throws SQLException {
        String email = rs.getString("email");
        String nickname = rs.getString("nickname");

        return new CustomerResultSet(email, nickname);
    }

    @Override
    public Class<CustomerResultSet> getTargetType() {
        return CustomerResultSet.class;
    }
}
