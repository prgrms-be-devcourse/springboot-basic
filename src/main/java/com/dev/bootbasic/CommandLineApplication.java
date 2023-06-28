package com.dev.bootbasic;

import com.dev.bootbasic.view.Command;
import com.dev.bootbasic.view.ViewManager;
import com.dev.bootbasic.view.dto.VoucherDetailsViewResponse;
import com.dev.bootbasic.voucher.controller.VoucherController;
import com.dev.bootbasic.voucher.dto.VoucherCreateRequest;
import com.dev.bootbasic.voucher.dto.VoucherDetailsResponse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static com.dev.bootbasic.view.Command.EXIT;
import static java.util.stream.Collectors.toUnmodifiableList;

@Component
public class CommandLineApplication implements CommandLineRunner {

    private final ViewManager viewManager;
    private final VoucherController voucherController;

    public CommandLineApplication(ViewManager viewManager, VoucherController voucherController) {
        this.viewManager = viewManager;
        this.voucherController = voucherController;
    }

    @Override
    public void run(String... args) {
        try {
            executeApplication();
        } catch (Exception e) {
            viewManager.showMessage(e.getMessage());
            run();
        }
    }

    private void executeApplication() {
        Command command;
        while ((command = viewManager.readCommand()) != EXIT) {
            switch (command) {
                case CREATE -> createVoucher();
                case LIST -> showAllVouchers();
            }
        }
    }

    private void createVoucher() {
        VoucherCreateRequest request = viewManager.readVoucherCreateInfo();
        UUID voucherId = voucherController.createVoucher(request);
        viewManager.showMessage(voucherId.toString());
    }

    private void showAllVouchers() {
        List<VoucherDetailsResponse> allVouchers = voucherController.findAllVouchers();

        List<VoucherDetailsViewResponse> voucherResponses = allVouchers.stream()
                .map(VoucherDetailsViewResponse::from)
                .collect(toUnmodifiableList());
        viewManager.showCollectionMessage(voucherResponses);
    }

}
