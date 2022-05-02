package org.prgms.kdtspringvoucher.io;

import org.prgms.kdtspringvoucher.commandLine.CommandType;
import org.prgms.kdtspringvoucher.customer.domain.CustomerType;
import org.prgms.kdtspringvoucher.voucher.domain.VoucherType;
import org.prgms.kdtspringvoucher.wallet.WalletCommandType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Scanner;

@Component
public class Console implements Input, Output {

    private static final Scanner scanner = new Scanner(System.in);
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
    public WalletCommandType inputWalletCommandType() {
        String inputWalletCommandType = scanner.nextLine();
        logger.info("Input voucher type String => {}", inputWalletCommandType);
        return Arrays.stream(WalletCommandType.values())
                .filter(value -> value.getCommandType().equals(inputWalletCommandType))
                .findAny()
                .orElse(null);
    }

    @Override
    public Long inputDiscountAmountOrPercent() {
        return Long.valueOf(scanner.nextLine());
    }

    @Override
    public String inputCustomerName() {
        System.out.println("=== Insert Customer Name ===");
        return scanner.nextLine();
    }

    @Override
    public String inputCustomerEmail() {
        System.out.println("=== Insert Customer Email ===");
        return scanner.nextLine();
    }

    @Override
    public CustomerType inputCustomerType() {
        try {
            System.out.println("=== Insert Customer Type ===");
            System.out.println("Basic or BlackList");
            return CustomerType.valueOf(scanner.nextLine().toUpperCase());
        }catch (IllegalArgumentException exception) {
            logger.error("Got wrong customer type Error");
            return null;
        }
    }

    @Override
    public int inputCustomerNumber() {
        System.out.println("=== Choose customer number ===");
        return Integer.valueOf(scanner.nextLine());
    }

    @Override
    public int inputVoucherNumber() {
        System.out.println("Choose voucher number!!!");
        return Integer.valueOf(scanner.nextLine());
    }

    @Override
    public void printCommandTypeInputPrompt() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type ** exit ** to exit the program");
        System.out.println("Type ** create ** to create a new voucher");
        System.out.println("Type ** list ** to all vouchers.");
        System.out.println("Type ** customer ** to create a new customer");
        System.out.println("Type ** black ** to all blackList");
        System.out.println("Type ** wallet ** to go to wallet program");
    }

    @Override
    public void printWalletCommandTypeInputPrompt() {
        System.out.println("=== Wallet Program ===");
        System.out.println("Type ** assign ** to assign to customer");
        System.out.println("Type ** customer ** to show vouchers by customer");
        System.out.println("Type ** delete ** to delete voucher by customer");
        System.out.println("Type ** voucher ** to show customer by voucher");
    }

    @Override
    public void printVoucherTypeInputPrompt() {
        System.out.println("=== Choose Voucher Type ===");
        System.out.println("Type ** fixed ** to create a new FixedAmountVoucher");
        System.out.println("Type ** percent ** to create a new PercentDiscountVoucher");
    }

    @Override
    public void outputAmountOrPercentPrompt(VoucherType voucherType) {
        switch (voucherType) {
            case FIXED -> noticeFixedAmountPrompt();
            case PERCENT -> noticePercentPrompt();
        }
    }

    private void noticeFixedAmountPrompt() {
        System.out.println("=== Insert Fixed Amount ===");
        System.out.println("Amount is over 0 !!");
    }

    private void noticePercentPrompt() {
        System.out.println("=== Insert Percent Amount ===");
        System.out.println("Amount is over 0 and under 100 !!");
    }
}
