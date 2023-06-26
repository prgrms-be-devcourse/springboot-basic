package com.dev.bootbasic;

import com.dev.bootbasic.view.Command;
import com.dev.bootbasic.view.ViewManager;
import com.dev.bootbasic.voucher.controller.VoucherController;
import com.dev.bootbasic.voucher.dto.VoucherCreateRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class CommandLineApplication implements CommandLineRunner {

    private final ViewManager viewManager;
    private final VoucherController voucherController;

    public CommandLineApplication(ViewManager viewManager, VoucherController voucherController) {
        this.viewManager = viewManager;
        this.voucherController = voucherController;
    }

    @Override
    public void run(String... args) throws Exception {
        Command command = viewManager.readCommand();

        switch (command) {
            case CREATE -> createVoucher();
            case LIST -> System.out.println("todo");
            case EXIT -> System.out.println("todo");
        }
    }

    private void createVoucher() throws IOException {
        VoucherCreateRequest request = viewManager.readVoucherCreateInfo();
        UUID voucher = voucherController.createVoucher(request);
        viewManager.showMessage(voucher.toString());
    }

}
