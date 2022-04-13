package org.prgms.kdtspringvoucher.io;

import org.prgms.kdtspringvoucher.CommandType;
import org.prgms.kdtspringvoucher.voucher.domain.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Scanner;

@Component
public class Console implements Input, Output {

    private StringBuffer prompt = new StringBuffer();
    private Scanner scanner = new Scanner(System.in);
    private static final Logger logger = LoggerFactory.getLogger(Console.class);

    @Override
    public CommandType inputCommandType() {
        String inputCommandType = scanner.nextLine();
        logger.info("Input command type String => {}", inputCommandType);
        return Arrays.stream(CommandType.values())
                .filter(c -> c.getCommandType().equals(inputCommandType))
                .findAny()
                .orElse(null);
    }

    @Override
    public VoucherType inputVoucherType() {
        String inputVoucherType = scanner.nextLine();
        logger.info("Input voucher type String => {}", inputVoucherType);
        return Arrays.stream(VoucherType.values())
                .filter(value -> value.getVoucherType().equals(inputVoucherType))
                .findAny()
                .orElse(null);
    }

    @Override
    public Long inputDiscountAmountOrPercent() {
        prompt.append("=== Insert Discount Amount Or Percent ===\n");
        System.out.println(prompt);
        prompt.delete(0, prompt.length());
        return Long.valueOf(scanner.nextLine());
    }

    @Override
    public void infoCommandTypeInputPrompt() {
        prompt.append("=== Voucher Program ===\n")
                .append("Type ** exit ** to exit the program\n")
                .append("Type ** create ** to create a new voucher\n")
                .append("Type ** list ** to all vouchers.\n")
                .append("Type ** black ** to all blackList\n");
        System.out.println(prompt);
        prompt.delete(0, prompt.length());
    }

    @Override
    public void infoVoucherTypeInputPrompt() {
        prompt.append("=== Choose Voucher Type ===\n")
                .append("Type ** fixed ** to create a new FixedAmountVoucher\n")
                .append("Type ** percent ** to create a new PercentDiscountVoucher\n");
        System.out.println(prompt);
        prompt.delete(0, prompt.length());
    }

    @Override
    public void commandTypeError() {
        System.out.println("Wrong Command Type.....\n");
    }

    @Override
    public void voucherTypeError() {
        System.out.println("Wrong Voucher Type.....\n");
    }
}
