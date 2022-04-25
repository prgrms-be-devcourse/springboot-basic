package org.prgrms.kdt.io;

import org.prgrms.kdt.model.voucher.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OutputConsole implements Output {

    private static final Logger logger = LoggerFactory.getLogger(OutputConsole.class);

    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void printCommandManual(String manuals) {
        printMessage("=== Voucher Program ===\n" + manuals);
    }

    @Override
    public void printVoucherValue(VoucherType voucherType) {
        StringBuilder stringBuilder = new StringBuilder("바우처 값을 입력해주세요.\n");
        stringBuilder.append(voucherType.getVoucherValidationMessage());
        printMessage(stringBuilder.toString());
    }

    @Override
    public void printWarnMessage(Exception e) {
        logger.warn(e.getMessage(), e);
        printMessage(e.getMessage());
    }

    @Override
    public void printErrorMessage(Exception e) {
        logger.error(e.getMessage(), e);
        printMessage(e.getMessage());
    }
}
