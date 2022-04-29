package com.prgrms.kdt.springbootbasic.repository;

import com.prgrms.kdt.springbootbasic.entity.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    public Optional<Voucher> findById(UUID voucherId);

    public Optional<Voucher> saveVoucher(Voucher voucher);

    public List<Voucher> getAllVouchers();

    public Optional<Voucher> updateVoucherAmount(Voucher voucher);

    public boolean deleteVoucher(Voucher voucher);
}
