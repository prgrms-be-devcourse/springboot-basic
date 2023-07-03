package org.prgms.voucher.application;

import org.prgms.voucher.voucher.AmountVoucherController;
import org.prgms.voucher.voucher.VoucherPrintDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Output implements ConsoleOutput, CommonOutput {
    private static final String VOUCHER_TYPE = "바우처 종류";
    private static final String AFTER_DISCOUNT_VALUE = "할인가";
    private static final String PUBLISH_DATE = "발행일";
    private static final String EXPIRATION_DATE = "만료일";
    private static final String SEPARATOR = ": ";
    private final AmountVoucherController amountVoucherController;

    public Output(AmountVoucherController amountVoucherController) {
        this.amountVoucherController = amountVoucherController;
    }

    @Override
    public void printSupportedCommands() {

    }

    @Override
    public void printList() {
        List<VoucherPrintDto> voucherPrintDtos = amountVoucherController.listVoucher();
        StringBuilder sb = new StringBuilder();

        voucherPrintDtos.forEach((voucherPrintDto -> {
            sb.append("\n")
                    .append(formatVoucherPrintDto(voucherPrintDto));
        }));

        System.out.println(sb);
    }

    private String formatVoucherPrintDto(VoucherPrintDto voucherPrintDto) {
        return VOUCHER_TYPE + SEPARATOR + voucherPrintDto.getOptionType() + "\n" +
                AFTER_DISCOUNT_VALUE + SEPARATOR + voucherPrintDto.getAfterDiscountValue() + "\n" +
                PUBLISH_DATE + SEPARATOR + voucherPrintDto.getPublishDate() + "\n" +
                EXPIRATION_DATE + SEPARATOR + voucherPrintDto.getExpirationDate() + "\n";
    }
}
