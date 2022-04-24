package kdt.vouchermanagement.domain.voucher.repository;

import kdt.vouchermanagement.domain.voucher.domain.Voucher;

public interface VoucherRepository {

    Voucher save(Voucher voucher);
}
