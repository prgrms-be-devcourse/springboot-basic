package com.program.voucher;

import com.program.voucher.io.Input;
import com.program.voucher.io.Output;
import com.program.voucher.service.CustomerService;
import com.program.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;


@Component(value = "voucherController")
public class VoucherController implements Runnable {

    private final Input input;
    private final Output output;
    private final VoucherService voucherService;
    private final CustomerService customerService;

    final Logger logger = LoggerFactory.getLogger(VoucherProgramApplication.class);

    private enum MenuType {
        EXIT, CREATE, LIST, BLACKLIST
    }


    public VoucherController(Input input, Output output, VoucherService voucherService, CustomerService customerService) {
        this.input = input;
        this.output = output;
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    @Override
    public void run() {
        while (true) {
            output.menuView();
            String choseMenu = input.input();

            try {
                MenuType menuType = MenuType.valueOf(choseMenu.toUpperCase());
                switch (menuType) {
                    case LIST -> viewVoucherList();
                    case CREATE -> createVoucher();
                    case BLACKLIST -> viewBlackListCustomer();
                    case EXIT -> {
                        output.successMessage("End the program. See you.");
                        return;
                    }
                }
            } catch (Exception e) {
                logger.warn("컨트롤러 run 메소드에서 에러 처리 :" + e.getMessage());
                output.errorMessage(e.getMessage());
            }

        }
    }

    private void createVoucher() {
        String voucherType = input.input("What type of voucher to create?   1.Fixed Amount   2.percent discount : ");
        String discount = input.input("Enter discount amount or percentage : ");

        voucherService.createVoucher(voucherType, UUID.randomUUID(), Integer.parseInt(discount));
        output.successMessage("Success create voucher!");
    }

    private void viewVoucherList() {
        output.allVoucherView(voucherService.getAllVoucher());
    }

    private void viewBlackListCustomer() {
        try {
            List<String> blackList = customerService.getBlackListCustomers();
            output.customerBlackListView(blackList);
        } catch (IOException e) {
            logger.warn("black list 파일 오픈 실패");
            throw new RuntimeException("! Failed to open blacklist file");
        }
    }
}
