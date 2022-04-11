package org.prgms.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Console implements InOut {
    private static final Logger logger = LoggerFactory.getLogger(Console.class);
    private static final Scanner scanner = new Scanner(System.in);

    @Override
    public void optionMessage() {
        logger.info("=== Voucher Program ===");
        logger.info("Type exit to exit the program");
        logger.info("Type create to create a new voucher");
        logger.info("Type list to list all vouchers");
    }

    @Override
    public String input() {
        return scanner.nextLine();
    }

    @Override
    public void inputError(String input) {
        logger.error("입력이 올바르지 않습니다 : {}. 다시 입력해주세요.", input);
    }

    @Override
    public int chooseVoucher() {
        logger.info("which one to create : 1. FixedAmountVoucher,  2. PercentDiscountVoucher");
        String opt = scanner.nextLine();
        return Integer.parseInt(opt);
    }
}
