package com.wonu606.vouchermanager.repository.voucher.reader;

import com.wonu606.vouchermanager.repository.voucher.query.VoucherFindQuery;
import com.wonu606.vouchermanager.repository.voucher.resultset.VoucherResultSet;
import java.util.List;
import java.util.Optional;

public interface VoucherReader {

    Optional<VoucherResultSet> findById(VoucherFindQuery query);

    List<VoucherResultSet> findAll();
}
