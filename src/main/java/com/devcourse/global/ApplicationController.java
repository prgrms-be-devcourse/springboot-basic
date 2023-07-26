package com.devcourse.global;

import com.devcourse.global.console.Console;
import com.devcourse.global.dto.CreateVoucherRequest;
import com.devcourse.user.application.UserService;
import com.devcourse.voucher.application.VoucherService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplicationController implements ApplicationRunner {
    private static final String GREETING = """
            === Voucher Program ===
           Type <VOUCHER> to create a new voucher.
           Type <LIST> to list all vouchers.
           Type <USER> to create a new user.
           Type <USERLIST> to list all users.
           Type <BLACKLIST> to list all black users.
           Type <EXIT> to exit the program.
           """;
    private static final String VOCHER_CREATED = "\n:: Voucher Created ::";
    private static final String USER_CREATED = "\n:: USER Created ::";
    private static final String APPLICATION_ENDED = "\n:: Application Ended ::";

    private final Console console = new Console();
    private final VoucherService voucherService;
    private final UserService userService;

    public ApplicationController(VoucherService voucherService, UserService userService) {
        this.voucherService = voucherService;
        this.userService = userService;
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
            case VOUCHER -> createVoucher();
            case VOUCHERLIST -> listVouchers();
            case USER -> createUser();
            case USERLIST -> listUsers();
            case BLACKLIST -> listBlackUsers();
            case EXIT -> {
                console.print(APPLICATION_ENDED);
                return false;
            }
        }

        return true;
    }

    private void createVoucher() {
        CreateVoucherRequest request = console.readCreationRequest();
        voucherService.create(request.discount(), request.expiredAt(), request.type());
        console.print(VOCHER_CREATED);
    }

    private void listVouchers() {
        List<String> responses = voucherService.findAll();
        console.print(responses);
    }

    private void createUser() {
        String name = console.readUserName();
        userService.create(name);
        console.print(USER_CREATED);
    }

    private void listUsers() {
        List<String> users = userService.findAll();
        console.print(users);
    }

    private void listBlackUsers() {
        List<String> blackList = userService.findAllBlack();
        console.print(blackList);
    }
}
