package com.wonu606.vouchermanager.repository.voucherwallet;

import com.wonu606.vouchermanager.repository.voucherwallet.query.OwnedCustomersQuery;
import com.wonu606.vouchermanager.repository.voucherwallet.query.OwnedVouchersQuery;
import com.wonu606.vouchermanager.repository.voucherwallet.query.WalletDeleteQuery;
import com.wonu606.vouchermanager.repository.voucherwallet.query.WalletInsertQuery;
import com.wonu606.vouchermanager.repository.voucherwallet.query.WalletRegisterQuery;
import com.wonu606.vouchermanager.repository.voucherwallet.resultset.OwnedCustomerResultSet;
import com.wonu606.vouchermanager.repository.voucherwallet.resultset.OwnedVoucherResultSet;
import com.wonu606.vouchermanager.repository.voucherwallet.resultset.WalletInsertResultSet;
import java.util.List;

public interface VoucherWalletRepository {

    List<OwnedVoucherResultSet> findOwnedVouchersByCustomer(OwnedVouchersQuery query);

    void delete(WalletDeleteQuery query);

    List<OwnedCustomerResultSet> findOwnedCustomersByVoucher(OwnedCustomersQuery query);

    WalletInsertResultSet insert(WalletInsertQuery wallet);

    void register(WalletRegisterQuery query);
}
