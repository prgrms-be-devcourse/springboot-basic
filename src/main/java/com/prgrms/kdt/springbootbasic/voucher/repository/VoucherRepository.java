package com.prgrms.kdt.springbootbasic.voucher.repository;

import com.prgrms.kdt.springbootbasic.voucher.entity.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    public Optional<Voucher> findById(UUID voucherId);

    public Optional<Voucher> findByTypeAndAmount(String voucherType, long amount);

    public List<Voucher> findByType(String voucherType);

    public List<Voucher> findOrderByCreatedAt();

    public Optional<Voucher> saveVoucher(Voucher voucher);

    public List<Voucher> getAllVouchers();

    public Optional<Voucher> updateVoucherAmount(Voucher voucher);

    public boolean deleteVoucher(Voucher voucher);
}
