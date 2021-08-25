package org.prgrms.kdt.service;

import org.prgrms.kdt.model.*;
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

    public Map<UUID, Voucher> getAllVouchers() {
        return voucherRepository.findAllVoucher();
    }

    public Voucher createVoucher(Voucher voucher) {
        return voucherRepository.insert(voucher);
    }

//    TODO
//    public void useVoucher(Voucher voucher) {
//        throw new UnsupportedOperationException();
//    }


}
