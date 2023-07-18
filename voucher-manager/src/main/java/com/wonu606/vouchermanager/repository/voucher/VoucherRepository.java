package com.wonu606.vouchermanager.repository.voucher;

import com.wonu606.vouchermanager.domain.voucher.Voucher;
import com.wonu606.vouchermanager.domain.voucher.VoucherResultSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    VoucherResultSet save(VoucherQuery query);

    Optional<VoucherResultSet> findById(UUID uuid);

    VoucherResultSets findAll();

    VoucherResultSets findAllByUuIds(UUIDList uuidList);

    void deleteById(UUID uuid);

    void deleteAll();
}
