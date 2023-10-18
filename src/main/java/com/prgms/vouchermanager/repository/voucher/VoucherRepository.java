package com.prgms.vouchermanager.repository.voucher;

import com.prgms.vouchermanager.domain.voucher.Voucher;

import java.util.List;

public interface VoucherRepository {

    void create(Voucher voucher);

    List<Voucher> getAllVouchers();
}
