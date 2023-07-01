package org.prgrms.kdt.voucher.service;

import org.prgrms.kdt.exception.InvalidInputException;
import org.prgrms.kdt.util.ErrorMessage;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.prgrms.kdt.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.domain.VoucherType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(VoucherType voucherType) {
        switch (voucherType){
            case FIXED:
                return voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID()));
            case PERCENT:
                return voucherRepository.insert(new PercentDiscountVoucher(UUID.randomUUID()));
            default:
                throw new InvalidInputException(ErrorMessage.INVALID_INPUT);
        }
    }

    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }
}
