package com.ray.junho.voucher.controller;

import com.ray.junho.voucher.controller.dto.VoucherRequest;
import com.ray.junho.voucher.controller.dto.VoucherResponse;
import com.ray.junho.voucher.domain.Voucher;
import com.ray.junho.voucher.domain.VoucherType;
import com.ray.junho.voucher.service.VoucherService;
import com.ray.junho.voucher.view.Console;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandLineController {

    private final VoucherService voucherService;
    private final Console console;

    public CommandLineController(VoucherService voucherService, Console console) {
        this.voucherService = voucherService;
        this.console = console;
    }

    @PostConstruct
    public void run() {
        while (true) {
            String command = console.readCommand();
            switch (Command.find(command)) {
                case EXIT -> {
                    console.printExitMessage();
                    return;
                }
                case LIST -> findVouchersAndPrint();
                case CREATE -> getVoucherInfoAndCreate();
            }
        }
    }

    private void findVouchersAndPrint() {
        List<Voucher> vouchers = voucherService.findAll();

        vouchers.stream()
                .map(this::createVoucherResponseMessage)
                .forEach(console::print);
    }

    private String createVoucherResponseMessage(Voucher voucher) {
        return new VoucherResponse(voucher.getId(), voucher.getVoucherType(), 1000)
                .generateMessage();
    }

    private void getVoucherInfoAndCreate() {
        VoucherRequest voucherRequest = createVoucherRequest();
        voucherService.create(voucherRequest);
        console.printCSuccessfullyCreatedMessage();
    }

    private VoucherRequest createVoucherRequest() {
        int voucherTypeOrder = console.readVoucherTypeToCreate();
        int discountAmount = console.readVoucherDiscountAmount(voucherTypeOrder);
        int voucherAmount = console.readVoucherAmountToCreate();

        VoucherType voucherType = VoucherType.findVoucherType(voucherTypeOrder);
        return new VoucherRequest(voucherType, discountAmount, voucherAmount);
    }
}
