package com.wonu606.vouchermanager.repository.voucherwallet;

import com.wonu606.vouchermanager.repository.voucherwallet.query.OwnedCustomersQuery;
import com.wonu606.vouchermanager.repository.voucherwallet.query.OwnedVouchersQuery;
import com.wonu606.vouchermanager.repository.voucherwallet.query.WalletDeleteQuery;
import com.wonu606.vouchermanager.repository.voucherwallet.query.WalletInsertQuery;
import com.wonu606.vouchermanager.repository.voucherwallet.query.WalletRegisterQuery;
import com.wonu606.vouchermanager.repository.voucherwallet.query.WalletUpdateQuery;
import com.wonu606.vouchermanager.repository.voucherwallet.reader.VoucherWalletReader;
import com.wonu606.vouchermanager.repository.voucherwallet.resultset.OwnedCustomerResultSet;
import com.wonu606.vouchermanager.repository.voucherwallet.resultset.OwnedVoucherResultSet;
import com.wonu606.vouchermanager.repository.voucherwallet.resultset.WalletInsertResultSet;
import com.wonu606.vouchermanager.repository.voucherwallet.resultset.WalletUpdateResultSet;
import com.wonu606.vouchermanager.repository.voucherwallet.store.VoucherWalletStore;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class VoucherWalletJdbcRepository implements VoucherWalletRepository {

    private final VoucherWalletReader reader;
    private final VoucherWalletStore store;

    public VoucherWalletJdbcRepository(VoucherWalletReader reader, VoucherWalletStore store) {
        this.reader = reader;
        this.store = store;
    }

    @Override
    public List<OwnedVoucherResultSet> findOwnedVouchersByCustomer(OwnedVouchersQuery query) {
        return reader.findOwnedVouchersByCustomer(query);
    }

    @Override
    public void delete(WalletDeleteQuery query) {
        store.delete(query);
    }

    public WalletInsertResultSet insert(WalletInsertQuery query) {
        return store.insert(query);
    }

    @Override
    public List<OwnedCustomerResultSet> findOwnedCustomersByVoucher(OwnedCustomersQuery query) {
        return reader.findOwnedCustomersByVoucher(query);
    }

    @Override
    public WalletUpdateResultSet update(WalletUpdateQuery query) {
        return store.update(query);
    }

    @Override
    public void register(WalletRegisterQuery query) {
        store.register(query);
    }
}
