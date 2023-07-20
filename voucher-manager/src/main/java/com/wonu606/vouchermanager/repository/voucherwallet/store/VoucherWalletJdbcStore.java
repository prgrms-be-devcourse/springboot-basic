package com.wonu606.vouchermanager.repository.voucherwallet.store;

import com.wonu606.vouchermanager.repository.voucherwallet.query.WalletDeleteQuery;
import com.wonu606.vouchermanager.repository.voucherwallet.query.WalletInsertQuery;
import com.wonu606.vouchermanager.repository.voucherwallet.query.WalletUpdateQuery;
import com.wonu606.vouchermanager.repository.voucherwallet.resultset.WalletInsertResultSet;
import com.wonu606.vouchermanager.repository.voucherwallet.resultset.WalletUpdateResultSet;
import java.util.HashMap;
import java.util.Map;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class VoucherWalletJdbcStore implements VoucherWalletStore {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public VoucherWalletJdbcStore(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void delete(WalletDeleteQuery query) {
        String deletionSql = "DELETE FROM voucher_wallet WHERE customer_id = :customer_id AND voucher_id = :voucher_id";
        Map<String, Object> params = new HashMap<>();
        params.put("customer_id", query.getCustomerId());
        params.put("voucher_id", query.getVoucherId());

        jdbcTemplate.update(deletionSql, params);
    }

    public WalletInsertResultSet insert(WalletInsertQuery query) {
        String insertionSql = "INSERT INTO voucher_wallet (voucher_id, customer_id) VALUES (:voucher_id, NULL)";
        Map<String, Object> params = new HashMap<>();
        params.put("voucher_id", query.getVoucherId());

        int resultSet = jdbcTemplate.update(insertionSql, params);
        return new WalletInsertResultSet(resultSet);
    }

    public WalletUpdateResultSet update(WalletUpdateQuery query) {
        String insertSql = "INSERT INTO voucher_wallet (customer_id, voucher_id) VALUES (:customer_id, :voucher_id)";
        Map<String, Object> params = new HashMap<>();
        params.put("customer_id", query.getCustomerId());
        params.put("voucher_id", query.getVoucherId());

        return new WalletUpdateResultSet(jdbcTemplate.update(insertSql, params));
    }
}
