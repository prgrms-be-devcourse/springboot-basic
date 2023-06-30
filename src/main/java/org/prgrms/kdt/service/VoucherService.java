package org.prgrms.kdt.service;

import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.utils.VoucherType;
import org.prgrms.kdt.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException("can not find a voucher for" + voucherId));
    }

    public List<Voucher> getVouchers() {
        return voucherRepository.findAll();
    }


    public Voucher save(VoucherType type, Long discount) {
        VoucherType voucherType = type;
        switch (voucherType) {
            case FIXED_AMOUNT_VOUCHER, PERCENT_DISCOUNT_VOUCHER -> {
                System.out.println("voucherType = " + voucherType);
                return voucherRepository.insert(voucherType.makeVoucher(discount));
            }
            default -> throw new RuntimeException("해당 바우처는 발급 불가능합니다");
        }

    }

}
