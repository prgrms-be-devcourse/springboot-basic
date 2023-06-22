package org.devcourse.voucher.app;

import org.devcourse.voucher.domain.voucher.Voucher;

import java.util.List;

public interface VoucherService {

    Voucher save(Voucher voucher);

    List<Voucher> findAll();
}
