package org.prgms.voucher.service;

import org.prgms.voucher.voucher.Voucher;

import java.util.List;

public interface VoucherService {

    Voucher findVoucher(long id);

    Voucher create(Voucher voucher);

    List<Voucher> list();
}
