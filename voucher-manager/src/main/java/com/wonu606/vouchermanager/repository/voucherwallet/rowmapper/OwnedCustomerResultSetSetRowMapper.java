package com.wonu606.vouchermanager.repository.voucherwallet.rowmapper;

import com.wonu606.vouchermanager.repository.voucherwallet.resultset.OwnedCustomerResultSet;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class OwnedCustomerResultSetSetRowMapper implements RowMapper<OwnedCustomerResultSet> {

    @Override
    public OwnedCustomerResultSet mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new OwnedCustomerResultSet(rs.getString("customer_id"));
    }
}
