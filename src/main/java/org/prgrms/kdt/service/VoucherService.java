package org.prgrms.kdt.service;

import org.prgrms.kdt.domain.*;
import org.prgrms.kdt.repository.*;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
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

    public Optional<Voucher> createVoucher(VoucherType type, long value) {
        if (type == VoucherType.FIXED_AMOUNT) {
            return Optional.ofNullable(voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), value)));
        } else if (type == VoucherType.PERCENT) {
            return Optional.ofNullable(voucherRepository.insert(new PercentDiscountVoucher(UUID.randomUUID(), value)));
        }
        return Optional.empty();
    }

//    TODO
    public void useVoucher(Voucher voucher) {
        throw new UnsupportedOperationException();
    }


}
