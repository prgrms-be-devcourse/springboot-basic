package org.prgrms.kdt.voucher.service;

import org.prgrms.kdt.voucher.model.Voucher;

import java.io.IOException;
import java.util.List;

public interface VoucherService {

    void create(Voucher voucher);

    List<Voucher> list();
}
