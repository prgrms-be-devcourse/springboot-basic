package com.wonu606.vouchermanager.repository.voucherwallet.reader.rowmapper;

import com.wonu606.vouchermanager.repository.voucherwallet.resultset.OwnedVoucherResultSet;
import com.wonu606.vouchermanager.util.TypedRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OwnedVoucherResultSetRowMapper implements TypedRowMapper<OwnedVoucherResultSet> {

    @Override
    public OwnedVoucherResultSet mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new OwnedVoucherResultSet(rs.getString("voucher_id"));
    }

    @Override
    public Class<OwnedVoucherResultSet> getTargetType() {
        return OwnedVoucherResultSet.class;
    }
}
