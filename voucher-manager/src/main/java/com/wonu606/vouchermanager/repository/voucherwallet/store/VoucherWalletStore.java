package com.wonu606.vouchermanager.repository.voucherwallet.store;

import com.wonu606.vouchermanager.repository.voucherwallet.query.WalletDeleteQuery;
import com.wonu606.vouchermanager.repository.voucherwallet.query.WalletInsertQuery;
import com.wonu606.vouchermanager.repository.voucherwallet.query.WalletRegisterQuery;
import com.wonu606.vouchermanager.repository.voucherwallet.query.WalletUpdateQuery;
import com.wonu606.vouchermanager.repository.voucherwallet.resultset.WalletInsertResultSet;
import com.wonu606.vouchermanager.repository.voucherwallet.resultset.WalletUpdateResultSet;

public interface VoucherWalletStore {

    void delete(WalletDeleteQuery query);

    WalletInsertResultSet insert(WalletInsertQuery wallet);

    void register(WalletRegisterQuery query);
}
