package com.programmers.vouchermanagement;

import com.programmers.vouchermanagement.view.Console;
import com.programmers.vouchermanagement.voucher.dto.VoucherDto;
import com.programmers.vouchermanagement.voucher.presentation.VoucherController;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CommandLineController implements CommandLineRunner {

    private final VoucherController voucherController;

    @Override
    public void run(String... args) {
        boolean running = true;

        while (running) {
            try {
                running = isRunning();
            } catch (RuntimeException e) {
                Console.outputErrorMessage(e.getMessage());
            }

        }
    }

    private boolean isRunning() {
        String command = Console.selectCommand();
        switch (command) {
            case "create" -> {
                String discountType = Console.selectDiscountType();
                int discountAmount = Integer.parseInt(Console.inputDiscountAmount());
                VoucherDto.Request request = new VoucherDto.Request(discountType, discountAmount);
                voucherController.createVoucher(request);
            }
            case "list" -> {
                VoucherDto.Response voucherList = voucherController.getVoucherList();
                Console.outputVoucherList(voucherList);
            }
            case "exit" -> {
                return false;
            }
            default -> throw new IllegalArgumentException("잘못 입력하였습니다. 다시 입력해주세요.");
        }
        return false;
    }
}
