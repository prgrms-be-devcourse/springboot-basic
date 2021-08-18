package org.prgrms.kdt.service;

import org.prgrms.kdt.entity.*;
import org.prgrms.kdt.repository.*;

import java.util.*;


public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException("Can not find a voucher %s".formatted(voucherId)));
    }

    public Collection<Voucher> listVoucher() {
        return voucherRepository.findAllVoucher();
    }

    public void createVoucher(String type, long value) {
        if(type.equals("fixed")) {
            voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), value));
        } else if(type.equals("percent")) {
            voucherRepository.insert(new PercentDiscountVoucher(UUID.randomUUID(), value));
        }
    }

    public void useVoucher(Voucher voucher) {
        throw new UnsupportedOperationException();
    }


}
