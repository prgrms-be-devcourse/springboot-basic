package com.wonu606.vouchermanager.repository.voucher;

import com.wonu606.vouchermanager.repository.voucher.query.VoucherDeleteQuery;
import com.wonu606.vouchermanager.repository.voucher.query.VoucherFindQuery;
import com.wonu606.vouchermanager.repository.voucher.query.VoucherInsertQuery;
import com.wonu606.vouchermanager.repository.voucher.resultset.VoucherInsertResultSet;
import com.wonu606.vouchermanager.repository.voucher.resultset.VoucherResultSet;
import java.util.List;
import java.util.Optional;

public interface VoucherRepository {

    VoucherInsertResultSet insert(VoucherInsertQuery query);

    Optional<VoucherResultSet> findById(VoucherFindQuery query);

    List<VoucherResultSet> findAll();

    void deleteById(VoucherDeleteQuery query);
}
