package com.wonu606.vouchermanager.repository.voucher.reader.rowmapper;

import com.wonu606.vouchermanager.repository.voucher.resultset.VoucherResultSet;
import com.wonu606.vouchermanager.util.TypedRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.stereotype.Component;

@Component
public class VoucherResultSetRowMapper implements TypedRowMapper<VoucherResultSet> {

    @Override
    public VoucherResultSet mapRow(ResultSet rs, int rowNum) throws SQLException {
        String voucherSimpleName = rs.getString("voucher_type");
        String uuid = rs.getString("voucher_id");
        double discountValue = rs.getDouble("discount_value");
        return new VoucherResultSet(voucherSimpleName, uuid, discountValue);
    }

    @Override
    public Class<VoucherResultSet> getTargetType() {
        return VoucherResultSet.class;
    }
}
