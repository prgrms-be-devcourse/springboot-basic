package com.wonu606.vouchermanager.repository.voucher.store;

import com.wonu606.vouchermanager.repository.voucher.query.VoucherDeleteQuery;
import com.wonu606.vouchermanager.repository.voucher.query.VoucherInsertQuery;
import com.wonu606.vouchermanager.repository.voucher.resultset.VoucherInsertResultSet;

public interface VoucherStore {

    VoucherInsertResultSet insert(VoucherInsertQuery query);

    void deleteById(VoucherDeleteQuery query);
}
