package me.kimihiqq.vouchermanagement.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kimihiqq.vouchermanagement.domain.Voucher;
import me.kimihiqq.vouchermanagement.dto.VoucherDto;
import me.kimihiqq.vouchermanagement.service.VoucherService;
import org.springframework.stereotype.Controller;
import me.kimihiqq.vouchermanagement.io.Console;

@Slf4j
@Controller
@RequiredArgsConstructor
public class VoucherControllerImpl implements VoucherController {
    private final Console console;
    private final VoucherService voucherService;

    @Override
    public void run() {
        String input;
        printInstructions();
        while (!(input = console.readLine()).equalsIgnoreCase("exit")) {
            handleInput(input);
            printInstructions();
        }
    }

    private void printInstructions() {
        console.printLine("=== Voucher Program ===");
        console.printLine("Type **exit** to exit the program.");
        console.printLine("Type **create** to create a new voucher.");
        console.printLine("Type **list** to list all vouchers.");
    }

    private void handleInput(String input) {
        log.info("Received input: " + input);
        if (input.equalsIgnoreCase("create")) {
            String type = console.readLine("Enter voucher type (fixed or percent): ");
            long discount = console.readDiscount("Enter discount amount or rate: ");
            if (!type.equalsIgnoreCase("fixed") && !type.equalsIgnoreCase("percent")) {
                log.error("Invalid voucher type: " + type);
                return;
            }
            log.info("Creating voucher with type: " + type + ", and discount: " + discount);
            VoucherDto voucherDto = new VoucherDto(type, discount);
            Voucher voucher = voucherService.createVoucher(voucherDto);
            console.printLine(voucher.getVoucherId() + ": " + voucher.getType() + " - " + voucher.getDiscount());
            log.info("Created voucher with ID: " + voucher.getVoucherId());
        } else if (input.equalsIgnoreCase("list")) {
            log.info("Listing all vouchers");
            voucherService.listVouchers().forEach(voucher ->
                    console.printLine(voucher.getVoucherId() + ": " + voucher.getType() + " - " + voucher.getDiscount())
            );
        } else {
            log.error("Invalid input: " + input);
            console.printLine("잘못된 명령입니다. 다시 시도하세요.");
        }
    }


}