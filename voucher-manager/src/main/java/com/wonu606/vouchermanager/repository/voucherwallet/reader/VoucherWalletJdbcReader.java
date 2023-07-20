package com.wonu606.vouchermanager.repository.voucherwallet.reader;

import com.wonu606.vouchermanager.repository.voucherwallet.query.OwnedCustomersQuery;
import com.wonu606.vouchermanager.repository.voucherwallet.query.OwnedVouchersQuery;
import com.wonu606.vouchermanager.repository.voucherwallet.reader.rowmapper.VoucherWalletReaderRowMapperManager;
import com.wonu606.vouchermanager.repository.voucherwallet.resultset.OwnedCustomerResultSet;
import com.wonu606.vouchermanager.repository.voucherwallet.resultset.OwnedVoucherResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class VoucherWalletJdbcReader implements VoucherWalletReader {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final VoucherWalletReaderRowMapperManager rowMapperManager;

    public VoucherWalletJdbcReader(NamedParameterJdbcTemplate jdbcTemplate,
            VoucherWalletReaderRowMapperManager rowMapperManager) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapperManager = rowMapperManager;
    }

    @Override
    public List<OwnedVoucherResultSet> findOwnedVouchersByCustomer(OwnedVouchersQuery query) {
        String selectionSql = "SELECT voucher_id FROM voucher_wallet WHERE customer_id = :customer_id";
        Map<String, Object> params = new HashMap<>();
        params.put("customer_id", query.getCustomerId());

        return jdbcTemplate.query(selectionSql, params,
                rowMapperManager.getRowMapperForType(OwnedVoucherResultSet.class));
    }

    @Override
    public List<OwnedCustomerResultSet> findOwnedCustomersByVoucher(OwnedCustomersQuery query) {
        String selectionSql = "SELECT customer_id FROM voucher_wallet WHERE voucher_id = :voucher_id";
        Map<String, Object> params = new HashMap<>();
        params.put("voucher_id", query.getVoucherId());

        return jdbcTemplate.query(selectionSql,
                rowMapperManager.getRowMapperForType(OwnedCustomerResultSet.class));
    }
}
