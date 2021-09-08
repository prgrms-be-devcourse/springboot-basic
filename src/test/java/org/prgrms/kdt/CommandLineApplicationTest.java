package org.prgrms.kdt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.assignments.*;
import org.prgrms.kdt.voucher.VoucherRepository;
import org.prgrms.kdt.voucher.VoucherService;

import java.io.IOException;
import java.util.Scanner;

public class CommandLineApplicationTest {

    @Test
    @DisplayName("TEST")
    void createVoucher() {
        Console console = new Console();
        console.guide();
        VoucherRepository voucherRepository = new VoucherMemoryRepository();
        VoucherService voucherService = new VoucherService(voucherRepository);
        CreateVoucher createVoucher = new CreateVoucher(voucherService);

        BlackListCSVFileService blackListCSVFileService = new BlackListCSVFileService();
        try {
            blackListCSVFileService.readBlakcList();
        } catch (IOException e) {
            e.printStackTrace();
        }


        int idx = 1;
        String command = "CREATE";
        while (idx < 9) {
            switch (command) {
                case "CREATE" -> {
                    VoucherData voucherData = new VoucherData(2,50);
                    createVoucher.create(voucherData);
                    console.successfullyCreated();
                    if(idx >= 8){
                        command = "LIST";
                    }

                }
                case "LIST" -> {
                    console.printVoucherList(voucherService.getAllVoucher());
                    command = "EXIT";
                }
                case "EXIT" -> {
                    console.exit();
                    System.exit(0);
                }
                default -> console.commandError();
            }
            idx++;
        }
    }
}