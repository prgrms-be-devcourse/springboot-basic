package org.prgms.voucher.voucher;

import org.prgms.voucher.voucher.repository.AmountVoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmountVoucherService {
    private final AmountVoucherRepository amountVoucherRepository;

    public AmountVoucherService(AmountVoucherRepository amountVoucherRepository) {
        this.amountVoucherRepository = amountVoucherRepository;
    }

    public AmountVoucher createAmountVoucher(AmountVoucherCreateVo amountVoucherCreateVo) {
        AmountVoucherCreationType creationType = amountVoucherCreateVo.getAmountVoucherCreationType();
        AmountVoucher amountVoucher = creationType.createAmountVoucher(amountVoucherCreateVo.getDiscountAmount());

        return amountVoucherRepository.save(amountVoucher);
    }

    public List<AmountVoucher> listAmountVoucher() {
        return amountVoucherRepository.findAll();
    }
}
