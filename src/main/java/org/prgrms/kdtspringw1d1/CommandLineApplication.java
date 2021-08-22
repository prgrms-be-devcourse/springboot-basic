package org.prgrms.kdtspringw1d1;

import org.prgrms.kdtspringw1d1.config.AppConfiguration;
import org.prgrms.kdtspringw1d1.voucher.Voucher;
import org.prgrms.kdtspringw1d1.voucher.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CommandLineApplication {

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private static final StringBuffer noticeText = new StringBuffer();

    public static void main(String[] args) throws IOException {

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);

        VoucherService voucherService = applicationContext.getBean(VoucherService.class);

        noticeText.append("=== Voucher Program ===\n")
                .append("Type exit to exit the program.\n")
                .append("Type create to create a new voucher.\n")
                .append("Type list to list all vouchers.");

        String command;
        String voucherType;

        System.out.println(noticeText.toString());

        while (true) {
            command = reader.readLine();
            if (command.equals(CommandType.EXIT.getType())) {
                System.out.println("BYE");
                return;
            } else if (command.equals(CommandType.CREATE.getType())) {
                System.out.println("Type fixed or percent for the type of voucher you want to create.");
                voucherType = reader.readLine();
                while (!voucherType.equals(VoucherType.FIXED.getType()) && !voucherType.equals(VoucherType.PERCENT.getType())) {
                    System.out.println("Type either fixed or percent.");
                    voucherType = reader.readLine();
                }
                Voucher createdVoucher = null;
                if (voucherType.equals(VoucherType.FIXED.getType())) {
                    printCreatedVoucher(voucherService.createVoucher(VoucherType.FIXED));
                } else {
                    printCreatedVoucher(voucherService.createVoucher(VoucherType.PERCENT));
                }

            } else if (command.equals(CommandType.LIST.getType())) {
                List<Voucher> vouchers = voucherService.getVoucherAll();
                printVouchers(vouchers);
            }
        }

    }

    private static void printVouchers(List<Voucher> vouchers) {
        for (int i = 0; i < vouchers.size(); i++) {
            System.out.println(i + 1 + " 번 Voucher : " + vouchers.get(i).getVoucherId());
        }
    }

    private static void printCreatedVoucher(Voucher voucher) {
        System.out.println("Success creating Voucher : " + voucher.getVoucherId());
    }

}
