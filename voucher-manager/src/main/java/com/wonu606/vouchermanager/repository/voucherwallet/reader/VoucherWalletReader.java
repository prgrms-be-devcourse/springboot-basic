package com.wonu606.vouchermanager.repository.voucherwallet.reader;

import com.wonu606.vouchermanager.repository.voucherwallet.query.OwnedCustomersQuery;
import com.wonu606.vouchermanager.repository.voucherwallet.query.OwnedVouchersQuery;
import com.wonu606.vouchermanager.repository.voucherwallet.resultset.OwnedCustomerResultSet;
import com.wonu606.vouchermanager.repository.voucherwallet.resultset.OwnedVoucherResultSet;
import java.util.List;

public interface VoucherWalletReader {

    List<OwnedVoucherResultSet> findOwnedVouchersByCustomer(OwnedVouchersQuery query);

    List<OwnedCustomerResultSet> findOwnedCustomersByVoucher(OwnedCustomersQuery query);
}
