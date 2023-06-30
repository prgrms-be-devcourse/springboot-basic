package org.prgms.voucher.voucher;

import org.prgms.voucher.voucher.AmountVoucher;
import org.prgms.voucher.voucher.VoucherService;
import org.prgms.voucher.voucher.VoucherCreateDto;
import org.prgms.voucher.voucher.VoucherPrintDto;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class VoucherController {
    private final VoucherService voucherService;
    private static final int INITIAL_MONEY = 100_000;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public AmountVoucher createAmountVoucher(VoucherCreateDto voucherCreateDto) {
        return voucherService.createAmountVoucher(voucherCreateDto);
    }

    public List<VoucherPrintDto> listVoucher() {
        List<AmountVoucher> amountVouchers = voucherService.listAmountVoucher();
        List<VoucherPrintDto> voucherPrintDtos = new ArrayList<>();

        for (AmountVoucher voucher : amountVouchers) {
            voucherPrintDtos.add(
                    new VoucherPrintDto(
                            voucher.getOptionTypeName(),
                            voucher.discount(INITIAL_MONEY),
                            voucher.getPublishDate(),
                            voucher.getExpirationDate()
                    )
            );
        }
        return voucherPrintDtos;
    }
}
