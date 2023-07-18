package com.wonu606.vouchermanager.repository.voucherwallet.rowmapper;

import com.wonu606.vouchermanager.repository.voucherwallet.resultset.OwnedVoucherResultSet;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class OwnedVoucherResultSetSetRowMapper implements RowMapper<OwnedVoucherResultSet> {

    @Override
    public OwnedVoucherResultSet mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new OwnedVoucherResultSet(rs.getString("voucher_id"));
    }
}
