package com.example.voucher.controller;

import java.util.UUID;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import com.example.voucher.constant.ModeType;
import com.example.voucher.constant.ServiceType;
import com.example.voucher.controller.request.VoucherRequest;
import com.example.voucher.controller.response.Response;
import com.example.voucher.domain.dto.VoucherDTO;
import com.example.voucher.io.Console;

@Controller
public class ApplicationController implements CommandLineRunner {

    private final Console console;
    private final VoucherController voucherController;

    private ApplicationController(VoucherController voucherController) {
        this.console = new Console();
        this.voucherController = voucherController;
    }

    @Override
    public void run(String... args) {
        boolean isRunning = true;

        while (isRunning) {
            ServiceType selectedServiceType = console.getServiceType();

            if (selectedServiceType == null) {
                continue;
            }

            switch (selectedServiceType) {
                case EXIT -> isRunning = false;
                case Voucher -> startVoucherProcess();
            }
        }
    }

    public void startVoucherProcess() {
        ModeType selectedModeType = console.getModeType();

        if (selectedModeType == null) {
            return;
        }

        switch (selectedModeType) {
            case CREATE -> createVoucher();
            case LIST -> displayVouchers();
            case DELETE_ALL -> removeVouchers();
            case SEARCH -> searchVoucher();
            case UPDATE -> updateVoucher();
            case DELETE -> removeVoucher();
        }
    }

    private void createVoucher() {
        VoucherRequest.Create request = console.getCreateRequest();

        if (request == null) {
            return;
        }

        try {
            Response<VoucherDTO> response = voucherController.createVoucher(request);
            console.displayResponse(response.getResultMessage());
        } catch (Exception e) {
            console.displayVoucherServiceError(e.getMessage());
        }
    }

    private void displayVouchers() {
        Response<VoucherDTO> response = voucherController.getVouchers();

        console.displayResponse(response.getResultMessage());
    }

    private void removeVouchers() {
        voucherController.deleteVouchers();
    }

    private void searchVoucher() {
        UUID voucherId = console.getVoucherId();

        if (voucherId == null) {
            return;
        }

        try {
            Response<VoucherDTO> response = voucherController.search(voucherId);
            console.displayResponse(response.getResultMessage());

        } catch (Exception e) {
            console.displayVoucherServiceError(e.getMessage());
        }
    }

    private void updateVoucher() {
        VoucherRequest.Update request = console.getUpdateRequest();

        if (request == null) {
            return;
        }

        try {
            Response<VoucherDTO> response = voucherController.update(request);
            console.displayResponse(response.getResultMessage());
        } catch (Exception e) {
            console.displayVoucherServiceError(e.getMessage());
        }
    }

    private void removeVoucher() {
        UUID voucherId = console.getVoucherId();

        if (voucherId == null) {
            return;
        }

        try {
            voucherController.deleteVoucher(voucherId);
        } catch (Exception e) {
            console.displayVoucherServiceError(e.getMessage());
        }
    }

}
