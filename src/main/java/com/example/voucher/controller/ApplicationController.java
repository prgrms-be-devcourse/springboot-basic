package com.example.voucher.controller;

import java.util.UUID;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import com.example.voucher.constant.ModeType;
import com.example.voucher.constant.ServiceType;
import com.example.voucher.controller.request.CustomerRequest;
import com.example.voucher.controller.request.VoucherRequest;
import com.example.voucher.controller.response.Response;
import com.example.voucher.io.Console;
import com.example.voucher.service.customer.dto.CustomerDTO;
import com.example.voucher.service.voucher.dto.VoucherDTO;

@Controller
public class ApplicationController implements CommandLineRunner {

    private final Console console;
    private final VoucherController voucherController;
    private final CustomerController customerController;

    private ApplicationController(VoucherController voucherController, CustomerController customerController) {
        this.console = new Console();
        this.voucherController = voucherController;
        this.customerController = customerController;
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
            case LIST -> displayCustomer();
        }
    }

    public void createCustomer() {
        try {
            CustomerRequest.Create request = console.getCustomerCreateRequest();
            Response<CustomerDTO> response = customerController.createCustomer(request);
            console.displayResponse(response.getResultMessage());
        } catch (Exception e) {
            console.displayError(e.getMessage());
        }
    }

    private void displayCustomer() {
        Response<CustomerDTO> response = customerController.getCustomers();
        console.displayResponse(response.getResultMessage());
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
            VoucherRequest.Create request = console.getVoucherCreateRequest();
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
            UUID voucherId = console.getId();
            Response<VoucherDTO> response = voucherController.search(voucherId);
            console.displayResponse(response.getResultMessage());
        } catch (Exception e) {
            console.displayError(e.getMessage());
        }
    }

    private void updateVoucher() {
        try {
            VoucherRequest.Update request = console.getVoucherUpdateRequest();
            Response<VoucherDTO> response = voucherController.update(request);
            console.displayResponse(response.getResultMessage());
        } catch (Exception e) {
            console.displayError(e.getMessage());
        }
    }

    private void removeVoucher() {
        try {
            UUID voucherId = console.getId();
            voucherController.deleteVoucher(voucherId);
        } catch (Exception e) {
            console.displayError(e.getMessage());
        }
    }

}
