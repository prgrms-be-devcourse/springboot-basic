package com.wonu606.vouchermanager.repository.voucherwallet;

import com.wonu606.vouchermanager.repository.voucherwallet.query.OwnedCustomersQuery;
import com.wonu606.vouchermanager.repository.voucherwallet.query.OwnedVouchersQuery;
import com.wonu606.vouchermanager.repository.voucherwallet.query.WalletDeleteQuery;
import com.wonu606.vouchermanager.repository.voucherwallet.query.WalletInsertQuery;
import com.wonu606.vouchermanager.repository.voucherwallet.query.WalletUpdateQuery;
import com.wonu606.vouchermanager.repository.voucherwallet.resultset.OwnedCustomerResultSet;
import com.wonu606.vouchermanager.repository.voucherwallet.resultset.OwnedVoucherResultSet;
import com.wonu606.vouchermanager.repository.voucherwallet.resultset.WalletInsertResultSet;
import com.wonu606.vouchermanager.repository.voucherwallet.resultset.WalletUpdateResultSet;
import com.wonu606.vouchermanager.repository.voucherwallet.rowmapper.OwnedCustomerResultSetSetRowMapper;
import com.wonu606.vouchermanager.repository.voucherwallet.rowmapper.OwnedVoucherResultSetSetRowMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JdbcVoucherWalletRepository implements VoucherWalletRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final OwnedVoucherResultSetSetRowMapper ownedVoucherResultSetSetRowMapper;
    private final OwnedCustomerResultSetSetRowMapper ownedCustomerResultSetSetRowMapper;


    public JdbcVoucherWalletRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

        ownedVoucherResultSetSetRowMapper = new OwnedVoucherResultSetSetRowMapper();
        ownedCustomerResultSetSetRowMapper = new OwnedCustomerResultSetSetRowMapper();
    }

    @Override
    public List<OwnedVoucherResultSet> findOwnedVouchersByCustomer(OwnedVouchersQuery query) {
        String selectionSql = "SELECT voucher_id FROM voucher_wallet WHERE customer_id = :customer_id";
        Map<String, Object> params = new HashMap<>();
        params.put("customer_id", query.getCustomerId());

        return jdbcTemplate.query(selectionSql, params, ownedVoucherResultSetSetRowMapper);
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

    @Override
    public List<OwnedCustomerResultSet> findOwnedCustomersByVoucher(OwnedCustomersQuery query) {
        String selectionSql = "SELECT customer_id FROM voucher_wallet WHERE voucher_id = :voucher_id";
        Map<String, Object> params = new HashMap<>();
        params.put("voucher_id", query.getVoucherId());

        return jdbcTemplate.query(selectionSql, ownedCustomerResultSetSetRowMapper);
    }

    public WalletUpdateResultSet update(WalletUpdateQuery query) {
        String insertSql = "INSERT INTO voucher_wallet (customer_id, voucher_id) VALUES (:customer_id, :voucher_id)";
        Map<String, Object> params = new HashMap<>();
        params.put("customer_id", query.getCustomerId());
        params.put("voucher_id", query.getVoucherId());

        return new WalletUpdateResultSet(jdbcTemplate.update(insertSql, params));
    }
}
