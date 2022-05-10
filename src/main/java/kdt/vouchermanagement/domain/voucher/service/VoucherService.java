package kdt.vouchermanagement.domain.voucher.service;

import kdt.vouchermanagement.domain.voucher.domain.Voucher;
import kdt.vouchermanagement.domain.voucher.domain.VoucherType;

import java.time.LocalDate;
import java.util.List;

public interface VoucherService {

    Voucher createVoucher(Voucher voucher);

    List<Voucher> findVouchers();

    void deleteVoucher(Long voucherId);

    Voucher findVoucher(Long voucherId);

    List<Voucher> findVouchersByTypeAndDate(VoucherType type, LocalDate date);

    List<Voucher> findVouchersByType(VoucherType type);

    List<Voucher> findVouchersByDate(LocalDate date);
}
