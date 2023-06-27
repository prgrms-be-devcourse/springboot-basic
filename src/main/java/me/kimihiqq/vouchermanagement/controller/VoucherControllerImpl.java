package me.kimihiqq.vouchermanagement.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kimihiqq.vouchermanagement.domain.Voucher;
import me.kimihiqq.vouchermanagement.dto.VoucherDto;
import me.kimihiqq.vouchermanagement.option.MainMenuOption;
import me.kimihiqq.vouchermanagement.option.VoucherTypeOption;
import me.kimihiqq.vouchermanagement.service.CustomerService;
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

    private final CustomerService customerService;

    @Override
    public void run() {
        while (true) {
            try {
                MainMenuOption mainMenuOption = console.promptUserChoice(MainMenuOption.class);

                switch (mainMenuOption) {
                    case EXIT:
                        return;
                    case CREATE:
                        VoucherTypeOption voucherTypeOption = console.promptUserChoice(VoucherTypeOption.class);
                        long discount = console.readDiscount("Enter discount amount or rate: ");
                        log.info("Creating voucher with type: " + voucherTypeOption.getDescription() + ", and discount: " + discount);
                        VoucherDto voucherDto = new VoucherDto(voucherTypeOption.name(), discount);
                        Voucher createdVoucher = voucherService.createVoucher(voucherDto);
                        console.printLine(createdVoucher.getVoucherId() + ": " + createdVoucher.getType() + " - " + createdVoucher.getDiscount());
                        log.info("Created voucher with ID: " + createdVoucher.getVoucherId());
                        break;
                    case LIST:
                        log.info("Listing all vouchers");
                        voucherService.listVouchers().forEach(voucher ->
                                console.printLine(voucher.getVoucherId() + ": " + voucher.getType() + " - " + voucher.getDiscount())
                        );
                        break;

                    case BLACKLIST:
                        log.info("Listing all blacklisted customers");
                        customerService.getBlacklistedCustomers().forEach(customer ->
                                console.printLine(customer.getId() + ": " + customer.getName() + " - " + customer.getStatus())
                        );
                        break;
                }
            } catch (Exception e) {
                log.error("Error reading input", e);
                console.printLine("입력을 읽는 중에 오류가 발생했습니다.");
            }
        }
    }
}