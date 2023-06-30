package org.prgms.voucher.application;

import org.prgms.voucher.voucher.VoucherController;
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
    private final VoucherController voucherController;

    public CommandLineApplication(VoucherController voucherController) {
        this.voucherController = voucherController;
    }

    @Override
    public void printSupportedCommands() {

    }

    @Override
    public void exitProgram() {

    }

    @Override
    public String printList() {
        List<VoucherPrintDto> voucherPrintDtos = voucherController.listVoucher();

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
