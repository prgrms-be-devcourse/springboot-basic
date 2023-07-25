package com.wonu606.vouchermanager.repository.voucher;

import com.wonu606.vouchermanager.repository.voucher.query.VoucherDeleteQuery;
import com.wonu606.vouchermanager.repository.voucher.query.VoucherFindQuery;
import com.wonu606.vouchermanager.repository.voucher.query.VoucherInsertQuery;
import com.wonu606.vouchermanager.repository.voucher.reader.VoucherReader;
import com.wonu606.vouchermanager.repository.voucher.resultset.VoucherInsertResultSet;
import com.wonu606.vouchermanager.repository.voucher.resultset.VoucherResultSet;
import com.wonu606.vouchermanager.repository.voucher.store.VoucherStore;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class VoucherJdbcRepository implements VoucherRepository {

    VoucherReader reader;
    VoucherStore store;

    public VoucherJdbcRepository(VoucherReader reader, VoucherStore store) {
        this.reader = reader;
        this.store = store;
    }

    @Override
    public VoucherInsertResultSet insert(VoucherInsertQuery query) {
        return store.insert(query);
    }

    @Override
    public Optional<VoucherResultSet> findById(VoucherFindQuery query) {
        return reader.findById(query);
    }

    @Override
    public List<VoucherResultSet> findAll() {
        return reader.findAll();
    }

    @Override
    public void deleteById(VoucherDeleteQuery query) {
        store.deleteById(query);
    }
}
