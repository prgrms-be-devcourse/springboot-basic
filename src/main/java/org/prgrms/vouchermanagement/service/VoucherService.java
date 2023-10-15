package org.prgrms.vouchermanagement.service;

import org.prgrms.vouchermanagement.exception.InvalidRangeException;
import org.prgrms.vouchermanagement.repository.VoucherRepository;
import org.prgrms.vouchermanagement.voucher.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
        voucherRepository.load();
    }

    public void createVoucher(PolicyStatus policy, long amountOrPercent) {
        UUID voucherId = UUID.randomUUID();
        voucherRepository.create(voucherId, amountOrPercent, policy);
    }

    public List<Voucher> voucherLists() {
        return voucherRepository.voucherLists();
    }
}
