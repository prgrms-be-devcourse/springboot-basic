package com.prgms.kdtspringorder.adapter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.prgms.kdtspringorder.application.VoucherService;
import com.prgms.kdtspringorder.domain.model.voucher.Voucher;
import com.prgms.kdtspringorder.domain.model.voucher.VoucherType;

public class CommandLineApplication {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter WRITER = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final String EXIT = "exit";
    private static final String CREATE = "create";
    private static final String LIST = "list";

    public static void main(String[] args) throws IOException {
        List<Voucher> vouchers = new ArrayList<>();
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        VoucherService voucherService = context.getBean(VoucherService.class);

        printAvailableCommands();

        while (true) {
            String command = READER.readLine();
            if (command.equals(EXIT)) {
                break;
            }
            if (command.equals(CREATE)) {
                createVoucher(vouchers, voucherService);
            }
            if (command.equals(LIST)) {
                listVouchers(vouchers);
            }
        }

        WRITER.flush();
        WRITER.close();
        READER.close();
    }

    private static void printAvailableCommands() throws IOException {
        WRITER.write("=== Voucher Program ===\n");
        WRITER.write("Type exit to exit the program.\n");
        WRITER.write("Type create to create a new voucher.\n");
        WRITER.write("Type list to list all vouchers.\n\n");
        WRITER.flush();
    }

    private static void createVoucher(List<Voucher> vouchers, VoucherService voucherService) throws IOException {
        WRITER.write("바우처 종류: Fixed 와 Percent 중 하나를 입력하세요:\n");
        WRITER.flush();
        VoucherType voucherType = VoucherType.valueOf(READER.readLine().toUpperCase());

        WRITER.write("할인가격(률)을 입력하세요:\n");
        WRITER.flush();
        long discount = Long.parseLong(READER.readLine());

        Voucher voucher = voucherService.createVoucher(UUID.randomUUID(), voucherType, discount);
        vouchers.add(voucher);
    }

    private static void listVouchers(List<Voucher> vouchers) {
        vouchers
            .forEach(v -> System.out.println("voucher id: " + v.getVoucherId()
                + ", voucher 종류: " + v.getClass().getName() + ", voucher 할인가격(률)" + v.getDiscount()));
        System.out.println();
    }
}
