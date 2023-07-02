package org.prgms.voucher.application;

import org.prgms.voucher.voucher.AmountVoucherController;
import org.prgms.voucher.voucher.VoucherPrintDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandLineApplication implements Console, OutPut {
    private static final String VOUCHER_TYPE = "바우처 종류";
    private static final String AFTER_DISCOUNT_VALUE = "할인가";
    private static final String PUBLISH_DATE = "발행일";
    private static final String EXPIRATION_DATE = "만료일";
    private static final String SEPARATOR = ": ";
    private final AmountVoucherController amountVoucherController;

    public CommandLineApplication(AmountVoucherController amountVoucherController) {
        this.amountVoucherController = amountVoucherController;
    }

    @Override
    public void printSupportedCommands() {

    }

    @Override
    public void exitProgram() {

    }

    @Override
    public String printList() {
        List<VoucherPrintDto> voucherPrintDtos = amountVoucherController.listVoucher();

        StringBuilder sb = new StringBuilder();

        for (VoucherPrintDto voucherPrintDto : voucherPrintDtos) {
            sb.append("\n");
            sb.append(VOUCHER_TYPE).append(SEPARATOR).append(voucherPrintDto.getOptionType()).append("\n");
            sb.append(AFTER_DISCOUNT_VALUE).append(SEPARATOR).append(voucherPrintDto.getAfterDiscountValue()).append("\n");
            sb.append(PUBLISH_DATE).append(SEPARATOR).append(voucherPrintDto.getPublishDate()).append("\n");
            sb.append(EXPIRATION_DATE).append(SEPARATOR).append(voucherPrintDto.getExpirationDate()).append("\n");
        }

        return sb.toString();
    }
}
