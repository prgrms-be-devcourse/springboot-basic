package org.programmers.kdtspring;

import org.programmers.kdtspring.ConsoleIO.CommandType;
import org.programmers.kdtspring.ConsoleIO.Input;
import org.programmers.kdtspring.ConsoleIO.Output;
import org.programmers.kdtspring.entity.voucher.Voucher;
import org.programmers.kdtspring.entity.voucher.VoucherType;
import org.programmers.kdtspring.repository.voucher.VoucherRepository;
import org.programmers.kdtspring.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

@Component
public class VoucherManagement implements Runnable {

    private final VoucherService voucherService;
    private final Input input;
    private final Output output;

    private static final Logger logger = LoggerFactory.getLogger(VoucherManagement.class);
    private final Scanner scanner = new Scanner(System.in);

    public VoucherManagement(VoucherService voucherService, Input input, Output output) {
        this.voucherService = voucherService;
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() {
        logger.info("Voucher Application run...");

        while (true) {
            String selectedOption = input.showOption();

            try {
                if (selectedOption.equalsIgnoreCase(String.valueOf(CommandType.CREATE))) {
                    createVoucher();
                    output.voucherCreated();
                    continue;
                }
                if (selectedOption.equalsIgnoreCase(String.valueOf(CommandType.LIST))) {
                    output.showAllVoucher();
                }
                if(selectedOption.equalsIgnoreCase(String.valueOf(CommandType.APPEND))) {

                }
                if (selectedOption.equalsIgnoreCase(String.valueOf(CommandType.EXIT))) {
                    break;
                }

            } catch (IllegalStateException e) {
                output.errorMessage();
            } catch (IOException ie) {
                ie.printStackTrace();
            }
        }
    }


    private void createVoucher() throws IOException {
        String chosenVoucher = input.chooseVoucher();
        if (chosenVoucher.equalsIgnoreCase(String.valueOf(VoucherType.FixedAmountVoucher))) {
            long amount = scanner.nextLong();
            voucherService.createFixedAmountVoucher(amount);
        }
        if (chosenVoucher.equalsIgnoreCase(String.valueOf(VoucherType.PercentDiscountVoucher))) {
            long percent = scanner.nextLong();
            voucherService.createPercentDiscountVoucher(percent);

        }
    }
}
