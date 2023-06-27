package me.kimihiqq.vouchermanagement.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kimihiqq.vouchermanagement.domain.Voucher;
import me.kimihiqq.vouchermanagement.dto.VoucherDto;
import me.kimihiqq.vouchermanagement.service.VoucherService;
import org.springframework.stereotype.Controller;
import me.kimihiqq.vouchermanagement.io.Console;

import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class VoucherControllerImpl implements VoucherController {
    private final Console console;
    private final VoucherService voucherService;

    @Override
    public void run() {
        String input;
        console.printInstructions();
        while (!(input = console.readLine()).equalsIgnoreCase("exit")) {
            try {
                handleInput(input);
            } catch (IOException e) {
                log.error("Error reading input", e);
                console.printLine("입력을 읽는 중에 오류가 발생했습니다.");
            }
            console.printInstructions();
        }
    }

    private void handleInput(String input) throws IOException {
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