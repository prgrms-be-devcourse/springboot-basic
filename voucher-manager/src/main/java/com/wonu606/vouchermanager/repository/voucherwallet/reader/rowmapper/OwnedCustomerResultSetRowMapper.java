package com.wonu606.vouchermanager.repository.voucherwallet.reader.rowmapper;

import com.wonu606.vouchermanager.repository.voucherwallet.resultset.OwnedCustomerResultSet;
import com.wonu606.vouchermanager.util.TypedRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class OwnedCustomerResultSetRowMapper implements TypedRowMapper<OwnedCustomerResultSet> {

    @Override
    public OwnedCustomerResultSet mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new OwnedCustomerResultSet(rs.getString("customer_id"));
    }

    @Override
    public Class<OwnedCustomerResultSet> getTargetType() {
        return OwnedCustomerResultSet.class;
    }
}
