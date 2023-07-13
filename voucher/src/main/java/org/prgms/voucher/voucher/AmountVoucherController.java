package org.prgms.voucher.voucher;

import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AmountVoucherController {
    private final AmountVoucherService amountVoucherService;

    public AmountVoucherController(AmountVoucherService amountVoucherService) {
        this.amountVoucherService = amountVoucherService;
    }

    public AmountVoucher createAmountVoucher(AmountVoucherCreateDto amountVoucherCreateDto) {
        return amountVoucherService.createAmountVoucher(
                new AmountVoucherVo(
                        amountVoucherCreateDto.getAmountVoucherCreationType(),
                        amountVoucherCreateDto.getDiscountAmount()
                )
        );
    }

    public List<VoucherPrintDto> listVoucher() {
        List<AmountVoucher> amountVouchers = amountVoucherService.listAmountVoucher();

        return amountVouchers.stream()
                .map(VoucherPrintDto::new)
                .toList();
    }
}
