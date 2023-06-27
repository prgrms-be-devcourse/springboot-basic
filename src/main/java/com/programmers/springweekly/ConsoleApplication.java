package com.programmers.springweekly;

import com.programmers.springweekly.controller.CustomerController;
import com.programmers.springweekly.controller.VoucherController;
import com.programmers.springweekly.domain.ProgramMenu;
import com.programmers.springweekly.domain.customer.Customer;
import com.programmers.springweekly.domain.voucher.Voucher;
import com.programmers.springweekly.view.Console;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ConsoleApplication implements CommandLineRunner {

    private final CustomerController customerController;
    private final VoucherController voucherController;
    private final Console console;

    private boolean running = true;

    @Override
    public void run(String... args) {
        while (running) {
            console.outputProgramGuide();
            try {
                ProgramMenu selectMenu = ProgramMenu.findProgramMenu(console.inputMessage());

                switch (selectMenu) {
                    case CREATE -> voucherController.createVoucher();
                    case LIST -> {
                        Map<UUID, Voucher> voucherMap = voucherController.getVoucherList();

                        if (voucherMap.isEmpty()) {
                            console.outputErrorMessage("No vouchers saved");
                            break;
                        }

                        console.outputGetVoucherAll(voucherMap);
                    }
                    case EXIT -> {
                        console.outputExitMessage();
                        running = false;
                    }
                    case BLACKLIST -> {
                        Map<UUID, Customer> customerMap = customerController.getBlackList();

                        if (customerMap.size() != 0) {
                            console.outputGetCustomerBlackList(customerMap);
                            break;
                        }

                        console.outputErrorMessage("There are no saved blacklists.");
                    }
                    default -> throw new IllegalArgumentException("The type you are looking for is not found.");
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }
}
