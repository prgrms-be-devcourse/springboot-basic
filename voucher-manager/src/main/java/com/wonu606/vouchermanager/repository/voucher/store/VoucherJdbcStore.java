package com.wonu606.vouchermanager.repository.voucher.store;

import com.wonu606.vouchermanager.repository.voucher.query.VoucherDeleteQuery;
import com.wonu606.vouchermanager.repository.voucher.query.VoucherInsertQuery;
import com.wonu606.vouchermanager.repository.voucher.resultset.VoucherInsertResultSet;
import java.util.HashMap;
import java.util.Map;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class VoucherJdbcStore implements VoucherStore {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public VoucherJdbcStore(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public VoucherInsertResultSet insert(VoucherInsertQuery query) {
        String insertionSql = "INSERT INTO voucher (voucher_id, voucher_type, discount_value) VALUES (:voucher_id, :voucher_type, :discount_value)";
        Map<String, Object> params = new HashMap<>();
        params.put("voucher_type", query.getVoucherClassSimpleName());
        params.put("voucher_id", query.getVoucherId());
        params.put("discount_value", query.getDiscountValue());

        return new VoucherInsertResultSet(namedParameterJdbcTemplate.update(insertionSql, params));
    }

    @Override
    public void deleteById(VoucherDeleteQuery query) {
        String deleteSql = "DELETE FROM voucher WHERE voucher_id = :voucher_id";
        Map<String, Object> params = new HashMap<>();
        params.put("voucher_id", query.getVoucherId());

        namedParameterJdbcTemplate.update(deleteSql, params);
    }
}
