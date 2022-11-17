package com.example.springbootbasic.repository.voucher;

import com.example.springbootbasic.domain.voucher.Voucher;
import com.example.springbootbasic.domain.voucher.VoucherType;

import java.util.List;

public interface VoucherRepository {
    Voucher save(Voucher voucher);
    List<Voucher> findAllVouchers();
    List<Voucher> findAllVouchersByVoucherType(VoucherType voucherType);
    Voucher update(Voucher voucher);
    void deleteAll();
    void deleteVouchersByVoucherType(VoucherType voucherType);
}
