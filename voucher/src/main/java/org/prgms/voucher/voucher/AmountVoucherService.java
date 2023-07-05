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
        return amountVoucherRepository.save(
                amountVoucherCreateVo
                        .getAmountVoucherOptionType()
                        .createAmountVoucher(amountVoucherCreateVo.getAmount())
        );
    }

    public List<AmountVoucher> listAmountVoucher() {
        return amountVoucherRepository.findAll();
    }
}
