package com.programmers.springweekly;

import com.programmers.springweekly.controller.CustomerController;
import com.programmers.springweekly.controller.VoucherController;
import com.programmers.springweekly.domain.ProgramMenu;
import com.programmers.springweekly.domain.customer.Customer;
import com.programmers.springweekly.domain.voucher.Voucher;
import com.programmers.springweekly.domain.voucher.VoucherType;
import com.programmers.springweekly.view.Console;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile("!test")
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
                    case CREATE -> createVoucher();
                    case LIST -> getVoucherList();
                    case EXIT -> {
                        console.outputExitMessage();
                        running = false;
                    }
                    case BLACKLIST -> getBlackList();
                    default -> throw new IllegalArgumentException("Input :" + selectMenu + "The type you are looking for is not found.");
                }

            } catch (IllegalArgumentException e) {
                log.error("잘못된 입력값 입니다 -> " + e.getMessage());
            } catch (Exception e) {
                log.error("알 수 없는 에러입니다 -> " + e.getMessage());
            }
        }
    }

    private void createVoucher() {
        console.outputSelectCreateVoucherGuide();
        VoucherType voucherType = VoucherType.findVoucherMenu(console.inputMessage());

        console.outputDiscountGuide();
        String inputNumber = console.inputMessage();
        log.info("user input: {} ", inputNumber);

        voucherController.createVoucher(voucherType, inputNumber);
    }

    private void getVoucherList() {
        Map<UUID, Voucher> voucherMap = voucherController.getVoucherList();

        if (voucherMap.isEmpty()) {
            console.outputErrorMessage("No vouchers saved");
            return;
        }

        console.outputGetVoucherAll(voucherMap);
    }

    private void getBlackList() {
        List<Customer> customerBlacklist = customerController.getBlackList();

        if (customerBlacklist.isEmpty()) {
            console.outputErrorMessage("There are no saved blacklists.");
            return;
        }

        console.outputGetCustomerBlackList(customerBlacklist);
    }
}
