package org.prgms.voucher.voucher;

import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AmountVoucherController {
    private final AmountVoucherService amountVoucherService;

    public AmountVoucherController(AmountVoucherService amountVoucherService) {
        this.amountVoucherService = amountVoucherService;
    }

    public AmountVoucher createAmountVoucher(AmountVoucherCreateDto amountVoucherCreateDto) {
        return amountVoucherService.createAmountVoucher(
                new AmountVoucherCreateVo(
                        amountVoucherCreateDto.getAmountVoucherOptionType(),
                        amountVoucherCreateDto.getOriginalPrice(),
                        amountVoucherCreateDto.getDiscountAmount()
                )
        );
    }

    public String listVoucher() {
        List<AmountVoucher> amountVouchers = amountVoucherService.listAmountVoucher();

        return amountVouchers.stream()
                .map(VoucherPrintDto::new)
                .map(VoucherPrintDto::getVoucherPrint)
                .collect(Collectors.joining("\n"));
    }
}
