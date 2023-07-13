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
                new AmountVoucherVo(
                        amountVoucherCreateDto.getAmountVoucherCreationType(),
                        amountVoucherCreateDto.getOriginalPrice(),
                        amountVoucherCreateDto.getDiscountAmount()
                )
        );
    }

    public String listVoucher(int originalPrice) {
        List<AmountVoucher> amountVouchers = amountVoucherService.listAmountVoucher();

        return amountVouchers.stream()
                .map(voucher -> new VoucherPrintDto(voucher).getVoucherPrint(originalPrice))
                .collect(Collectors.joining("\n"));
    }
}
