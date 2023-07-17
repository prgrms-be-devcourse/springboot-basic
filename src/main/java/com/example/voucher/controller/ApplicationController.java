package com.example.voucher.controller;

import java.util.UUID;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import com.example.voucher.constant.ModeType;
import com.example.voucher.constant.ServiceType;
import com.example.voucher.controller.request.VoucherRequest;
import com.example.voucher.controller.response.Response;
import com.example.voucher.io.Console;
import com.example.voucher.service.voucher.dto.VoucherDTO;

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
        ServiceType selectedServiceType = null;

        while (isRunning) {
            try {
                selectedServiceType = console.getServiceType();
            } catch (Exception e) {
                console.displayError(e.getMessage());
                continue;
            }

            switch (selectedServiceType) {
                case EXIT -> isRunning = false;
                case VOUCHER -> startVoucherProcess();
                case CUSTOMER -> startCustomerProcess();
            }
        }
    }

    public void startCustomerProcess() {
        ModeType selectedModeType = null;

        try {
            selectedModeType = console.getModeType();
        } catch (Exception e) {
            console.displayError(e.getMessage());
            return;
        }

        switch (selectedModeType) {
            case CREATE -> createCustomer();
        }
    }

    public void createCustomer() {

    }

    public void startVoucherProcess() {
        ModeType selectedModeType = null;

        try {
            selectedModeType = console.getModeType();
        } catch (Exception e) {
            console.displayError(e.getMessage());
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
        try {
            VoucherRequest.Create request = console.getCreateRequest();
            Response<VoucherDTO> response = voucherController.createVoucher(request);
            console.displayResponse(response.getResultMessage());
        } catch (Exception e) {
            console.displayError(e.getMessage());
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
        try {
            UUID voucherId = console.getVoucherId();
            Response<VoucherDTO> response = voucherController.search(voucherId);
            console.displayResponse(response.getResultMessage());
        } catch (Exception e) {
            console.displayError(e.getMessage());
        }
    }

    private void updateVoucher() {
        try {
            VoucherRequest.Update request = console.getUpdateRequest();
            Response<VoucherDTO> response = voucherController.update(request);
            console.displayResponse(response.getResultMessage());
        } catch (Exception e) {
            console.displayError(e.getMessage());
        }
    }

    private void removeVoucher() {
        try {
            UUID voucherId = console.getVoucherId();
            voucherController.deleteVoucher(voucherId);
        } catch (Exception e) {
            console.displayError(e.getMessage());
        }
    }

}
