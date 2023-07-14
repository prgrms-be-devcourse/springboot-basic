package com.programmers.springbasic.domain.voucher.repository;

import com.programmers.springbasic.domain.voucher.entity.Voucher;
import com.programmers.springbasic.domain.voucher.model.VoucherType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    void save(Voucher voucher);
    Optional<Voucher> findByCode(UUID voucherCode);
    List<Voucher> findAll();
    List<Voucher> findAllByVoucherType(VoucherType voucherType);
    List<Voucher> findAllByCustomerId(UUID customerId);
    void update(Voucher voucher);
    void delete(UUID voucher);

    List<UUID> findAllCustomerIdByVoucherType(String voucherType);
}
