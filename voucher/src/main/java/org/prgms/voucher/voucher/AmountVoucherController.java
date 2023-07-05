package org.prgms.voucher.voucher;

import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AmountVoucherController {
    private final AmountVoucherService amountVoucherService;
    private static final int INITIAL_MONEY = 100_000;

    public AmountVoucherController(AmountVoucherService amountVoucherService) {
        this.amountVoucherService = amountVoucherService;
    }

    public AmountVoucher createAmountVoucher(AmountVoucherCreateDto amountVoucherCreateDto) {
        return amountVoucherService.createAmountVoucher(
                new AmountVoucherCreateVo(
                        amountVoucherCreateDto.getAmountVoucherOptionType(),
                        amountVoucherCreateDto.getAmount())
        );
    }

    public String listVoucher() {
        List<AmountVoucher> amountVouchers = amountVoucherService.listAmountVoucher();

        return amountVouchers.stream()
                .map(voucher -> new VoucherPrintDto(
                        voucher.getOptionTypeName(),
                        voucher.discount(INITIAL_MONEY),
                        voucher.getPublishDate(),
                        voucher.getExpirationDate()))
                .map(VoucherPrintDto::getVoucherPrint)
                .collect(Collectors.joining("\n"));
    }
}
