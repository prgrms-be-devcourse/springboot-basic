package org.prgrms.kdt.service;

import org.prgrms.kdt.models.Voucher;
import org.prgrms.kdt.repository.VoucherRepository;

import java.util.List;

public class VoucherService {
    private final VoucherRepository repository;

    public VoucherService(VoucherRepository repository) {
        this.repository = repository;
    }

    public Voucher create(long value, String type) {
        return null;
    }

    public List<Voucher> voucherList() {
        return null;
    }

    public void voucherClear() {

    }
}
