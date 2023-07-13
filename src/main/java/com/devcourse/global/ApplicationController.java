package com.devcourse.global;

import com.devcourse.global.console.Console;
import com.devcourse.user.repository.UserRepository;
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
           Type <EXIT> to exit the program.
           Type <CREATE> to create a new voucher.
           Type <LIST> to list all vouchers.
           """;
    private static final String CREATION_RESPONSE = "\n:: Voucher Created ::";
    private static final String EXITING_RESPONSE = "\n:: Application Ended ::";

    private final Console console = new Console();
    private final VoucherService voucherService;
    private final UserRepository userRepository;

    public ApplicationController(VoucherService voucherService, UserRepository userRepository) {
        this.voucherService = voucherService;
        this.userRepository = userRepository;
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
            case CREATE -> {
                CreateVoucherRequest request = console.readCreationRequest();
                voucherService.create(request);
                console.print(CREATION_RESPONSE);
            }
            case LIST -> {
                List<String> responses = voucherService.findAll();
                console.print(responses);
            }
            case EXIT -> {
                console.print(EXITING_RESPONSE);
                return false;
            }
            case BLACKLIST -> {
                List<String> blackList = userRepository.findAllBlack();
                console.print(blackList);
            }
        }

        return true;
    }
}
