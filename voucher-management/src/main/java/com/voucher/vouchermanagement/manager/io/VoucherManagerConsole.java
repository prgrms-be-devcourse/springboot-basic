package com.voucher.vouchermanagement.manager.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class VoucherManagerConsole implements VoucherManagerInput, VoucherManagerOutput {

    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final Logger logger = LoggerFactory.getLogger(VoucherManagerConsole.class);

    @Override
    public String input(String prompt) {
        try {
            bw.write(prompt);
            bw.flush();

            return br.readLine();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        return "";
    }

    @Override
    public void println(String string) {
        try {
            bw.write(string + "\n");
            bw.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void printMenu() {
        try {
            bw.write("=== Voucher Program ===" + "\n");
            bw.write("Type exit to exit program." + "\n");
            bw.write("Type create to create a new voucher." + "\n");
            bw.write("Type list to list all vouchers." + "\n");
            bw.write("Type blacklist to list all blacklist" + "\n");
            bw.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void printVoucherType() {
        System.out.println("=== Voucher Type ===");

        Arrays.stream(VoucherType.values())
                .forEach(System.out::println);
    }
}
