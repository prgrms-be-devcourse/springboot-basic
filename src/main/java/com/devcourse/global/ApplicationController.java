package com.devcourse.global;

import com.devcourse.global.console.Console;
import com.devcourse.user.repository.BlackListRepository;
import com.devcourse.voucher.application.VoucherService;
import com.devcourse.voucher.application.dto.CreateVoucherRequest;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplicationController implements ApplicationRunner {
    private static final String GREETING = """
            === Voucher Program ===
           Type <CREATE> to create a new voucher.
           Type <LIST> to list all vouchers.
           Type <BLACKLIST> to list all black users.
           Type <EXIT> to exit the program.
           """;
    private static final String CREATION_RESPONSE = "\n:: Voucher Created ::";
    private static final String EXITING_RESPONSE = "\n:: Application Ended ::";

    private final Console console = new Console();
    private final VoucherService voucherService;
    private final BlackListRepository blackListRepository;

    public ApplicationController(VoucherService voucherService, BlackListRepository blackListRepository) {
        this.voucherService = voucherService;
        this.blackListRepository = blackListRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        boolean status = true;
        console.print(GREETING);

        while (status) {
            Command command = console.readCommand();
            status = executeCommand(command);
        }
    }

    private boolean executeCommand(Command command) {
        switch (command) {
            case CREATE -> createVoucher();
            case LIST -> listVouchers();
            case BLACKLIST -> listBlackUsers();
            case EXIT -> {
                console.print(EXITING_RESPONSE);
                return false;
            }
        }

        return true;
    }

    private void createVoucher() {
        CreateVoucherRequest request = console.readCreationRequest();
        voucherService.create(request);
        console.print(CREATION_RESPONSE);
    }

    private void listVouchers() {
        List<String> responses = voucherService.findAll();
        console.print(responses);
    }

    private void listBlackUsers() {
        List<String> blackList = blackListRepository.findAllBlack();
        console.print(blackList);
    }
}
