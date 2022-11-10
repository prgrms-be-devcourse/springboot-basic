package com.programmers.assignment.voucher;

import com.programmers.assignment.voucher.engine.controller.MenuController;
import com.programmers.assignment.voucher.engine.controller.VoucherController;
import com.programmers.assignment.voucher.engine.io.ConsoleInput;
import com.programmers.assignment.voucher.engine.io.ConsoleOutput;
import com.programmers.assignment.voucher.engine.io.Input;
import com.programmers.assignment.voucher.engine.io.Output;
import com.programmers.assignment.voucher.engine.repository.MemoryVoucherRepository;
import com.programmers.assignment.voucher.engine.repository.VoucherRepository;
import com.programmers.assignment.voucher.engine.service.MenuService;
import com.programmers.assignment.voucher.engine.service.VoucherService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VoucherApplication {

    public static void main(String[] args) {
        SpringApplication.run(VoucherApplication.class, args);

        Input input = new ConsoleInput();
        Output output = new ConsoleOutput();
        VoucherRepository voucherRepository = new MemoryVoucherRepository();

        VoucherService voucherService = new VoucherService(input, output, voucherRepository);
        VoucherController voucherController = new VoucherController(voucherService);
        MenuService menuService = new MenuService(input, output, voucherRepository, voucherController);
        MenuController menuController = new MenuController(menuService);

        new CliApplication(menuController).run();
    }

}
