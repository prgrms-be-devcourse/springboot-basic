package org.programmers.springorder;

import org.programmers.springorder.console.Console;
import org.programmers.springorder.dto.VoucherRequestDto;
import org.programmers.springorder.service.VoucherService;
import org.programmers.springorder.utils.MenuType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class VoucherApplication implements CommandLineRunner {

    private final Console console;
    private final VoucherService voucherService;

    public VoucherApplication(VoucherService voucherService) {
        this.voucherService = voucherService;
        this.console = new Console();
    }

    @Override
    public void run(String... args) {
        boolean isRunning = true;

        while(isRunning) {
            MenuType menu = console.inputMenu();

            switch (menu) {
                case EXIT -> isRunning = false;
                case CREATE -> createVoucher(voucherService);
            }
        }
    }

    private void createVoucher(VoucherService service) {
        VoucherRequestDto request = console.inputVoucherInfo();
        service.save(request);
        console.printMessage("바우처가 등록되었습니다.");
    }
}
