package org.prgrms.kdt.engine.io;

import org.prgrms.kdt.CommandLineApplication;
import org.prgrms.kdt.engine.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class Console implements Input, Output {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Logger logger = LoggerFactory.getLogger(Console.class);


    @Override
    public String inputCommand(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    @Override
    public void help() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type exit to exit the program.");
        System.out.println("Type create to create a new voucher.");
        System.out.println("Type list to list all vouchers.");
    }

    @Override
    public void showVoucherOptions() {
        System.out.print("'fixed' for fixed voucher, 'percent' for percent voucher : ");
    }

    @Override
    public void illegalInputError() {
        logger.warn("illegalInputError");
        System.out.println("Wrong Input");
    }

    @Override
    public void createVoucher(Voucher voucher) {
        System.out.println("Created Voucher " + voucher);
    }

    @Override
    public void listVoucher(Map<UUID, Voucher> voucherList) {
        for (Voucher voucher : voucherList.values())
            System.out.println(voucher);
    }

    @Override
    public void voucherListNotFoundError() {
        logger.warn("voucherListNotFoundError");
        System.out.println("No Vouchers Found");
    }
}
