package com.wonu606.vouchermanager.repository.voucher.rowmapper;

import com.wonu606.vouchermanager.repository.voucher.resultset.VoucherResultSet;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class VoucherResultSetRowMapper implements RowMapper<VoucherResultSet> {

    @Override
    public VoucherResultSet mapRow(ResultSet rs, int rowNum) throws SQLException {
        String voucherSimpleName = rs.getString("voucher_type");
        String uuid = rs.getString("voucher_id");
        double discountValue = rs.getDouble("discount_value");
        return new VoucherResultSet(voucherSimpleName, uuid, discountValue);
    }
}
