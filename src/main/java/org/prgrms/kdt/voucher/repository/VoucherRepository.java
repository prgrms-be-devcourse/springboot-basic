package org.prgrms.kdt.voucher.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.kdt.controller.dto.VoucherSearchCriteria;
import org.prgrms.kdt.voucher.model.Voucher;

public interface VoucherRepository {

    Voucher save(Voucher voucher);

    List<Voucher> findAll();

    Optional<Voucher> findById(UUID uuid);

    void update(UUID id, long value);

    void deleteAll();

    void deleteById(UUID id);

    public List<Voucher> searchVoucher(VoucherSearchCriteria criteria);

}
