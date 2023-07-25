package com.wonu606.vouchermanager.repository.voucher;

import com.wonu606.vouchermanager.domain.voucher.Voucher;
import com.wonu606.vouchermanager.domain.voucher.VoucherResultSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherResultSetRepository {

    Voucher save(Voucher voucher);

    Optional<VoucherResultSet> findById(UUID id);

    List<VoucherResultSet> findAll();

    void deleteById(UUID id);

    void deleteAll();

    List<VoucherResultSet> findAllByUuids(List<UUID> uuidList);
}
