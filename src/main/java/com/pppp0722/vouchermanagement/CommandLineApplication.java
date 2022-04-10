package com.pppp0722.vouchermanagement;

import com.pppp0722.vouchermanagement.io.Console;
import com.pppp0722.vouchermanagement.voucher.model.Voucher;
import com.pppp0722.vouchermanagement.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class CommandLineApplication {
    private static final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);
    private final Console console = new Console();
    private final VoucherService voucherService;

    public CommandLineApplication(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public void run() {
        logger.info("Start voucher management application");
        console.printLogo();

        boolean isExit = false;
        while(!isExit) {
            console.printMenu();
            String command = console.getCommand("Input : ");
            switch(command) {
                case "create":
                    createVoucher();
                    break;
                case "list":
                    printVoucherList();
                    break;

                case "exit":
                    isExit = true;
                    break;
                default:
                    console.printInputError();
                    logger.error("Invalid command -> {}", command);
            }
            console.printEmpty();
        }

        logger.info("Terminate voucher management application");
    }

    public void createVoucher() {
        String voucherType = "";
        while(true) {
            console.printVoucherTypeInputRequest();
            voucherType = console.getCommand("Input : ");

            if(!voucherType.equals("f") && !voucherType.equals("p")){
                logger.error("Invalid voucher type -> {}", voucherType);
                console.printInputError();
            }
            else break;
        }

        long discountAmount = 0;
        while(true) {
            console.printAmountInputRequest();
            discountAmount = Long.parseLong(console.getCommand("Input : "));
            if ((voucherType.equals("f") && discountAmount < 1) ||
                    (voucherType.equals("p") && (discountAmount < 1 || discountAmount > 100))) {
                logger.error("Invalid amount -> {}", discountAmount);
                console.printInputError();
            } else break;
        }

        voucherService.createVoucher(voucherType, UUID.randomUUID(), discountAmount);
    }

    public void printVoucherList() {
        Optional<List<Voucher>> voucherList = voucherService.getVouchers();

        if(voucherList.isEmpty()) {
            logger.error("Voucher is Empty");
            console.printVoucherEmpty();
        }
        else console.printVoucherList(voucherList.get());
    }
}