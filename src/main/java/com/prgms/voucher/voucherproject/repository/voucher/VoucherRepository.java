package com.prgms.voucher.voucherproject.repository.voucher;

import com.prgms.voucher.voucherproject.domain.voucher.Voucher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface VoucherRepository {
    public Optional<Voucher> findById(UUID voucherId);

    public void save(Voucher voucher);

    public List<Voucher> findAll();

    public void deleteById(UUID voucherId);

}
