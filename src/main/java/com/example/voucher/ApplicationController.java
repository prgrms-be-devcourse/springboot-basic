package com.example.voucher;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import com.example.voucher.constant.ModeType;
import com.example.voucher.constant.ServiceType;
import com.example.voucher.customer.controller.CustomerController;
import com.example.voucher.customer.controller.CustomerRequest;
import com.example.voucher.customer.service.dto.CustomerDTO;
import com.example.voucher.io.Console;
import com.example.voucher.response.Response;
import com.example.voucher.voucher.controller.VoucherController;
import com.example.voucher.voucher.controller.VoucherRequest;
import com.example.voucher.voucher.service.dto.VoucherDTO;
import com.example.voucher.wallet.controller.WalletController;
import com.example.voucher.wallet.controller.WalletRequest;
import com.example.voucher.wallet.service.dto.WalletDTO;

@Controller
public class ApplicationController implements CommandLineRunner {

    private final Console console;
    private final VoucherController voucherController;
    private final CustomerController customerController;
    private final WalletController walletController;

    private ApplicationController(VoucherController voucherController, CustomerController customerController,
        WalletController walletController) {
        this.console = new Console();
        this.voucherController = voucherController;
        this.customerController = customerController;
        this.walletController = walletController;
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
                case WALLET -> startWalletProcess();
            }
        }
    }

    private void startVoucherProcess() {
        ModeType selectedModeType = null;

        try {
            selectedModeType = console.getModeType();
        } catch (Exception e) {
            console.displayError(e.getMessage());
            return;
        }

        switch (selectedModeType) {
            case CREATE -> createVoucher();
            case LIST -> getVouchers();
            case SEARCH -> getVoucher();
            case UPDATE -> updateVoucher();
            case DELETE_ALL -> removeVouchers();
            case DELETE -> removeVoucher();
        }
    }

    private void startCustomerProcess() {
        ModeType selectedModeType = null;

        try {
            selectedModeType = console.getModeType();
        } catch (Exception e) {
            console.displayError(e.getMessage());
            return;
        }

        switch (selectedModeType) {
            case CREATE -> createCustomer();
            case LIST -> getCustomers();
            case SEARCH -> getCustomer();
            case UPDATE -> updateCustomer();
            case DELETE_ALL -> removeCustomers();
            case DELETE -> removeCustomer();
        }
    }

    private void startWalletProcess() {
        ModeType selectedModeType = null;

        try {
            selectedModeType = console.getWalletModeType();
        } catch (Exception e) {
            console.displayError(e.getMessage());
            return;
        }

        switch (selectedModeType) {
            case CREATE -> createWallet();
            case SEARCH_BY_CUSTOMER -> getWalletByCustomer();
            case SEARCH_BY_VOUCHER -> getWalletByVoucher();
            case DELETE -> deleteWallet();
        }
    }

    private void createVoucher() {
        try {
            VoucherRequest request = console.getCreateVoucherRequest();
            Response<VoucherDTO> response = voucherController.createVoucher(request);
            console.displayResponse(response.getResultMessage());
        } catch (Exception e) {
            console.displayError(e.getMessage());
        }
    }

    private void getVouchers() {
        Response<VoucherDTO> response = voucherController.getVouchers();
        console.displayResponse(response.getResultMessage());
    }

    private void getVoucher() {
        VoucherRequest request = console.getSearchVoucherRequest();

        Response<VoucherDTO> response = voucherController.getVoucher(request);

        console.displayResponse(response.getResultMessage());
    }

    private void updateVoucher() {
        try {
            VoucherRequest request = console.getUpdateVoucherRequest();
            Response<VoucherDTO> response = voucherController.update(request);
            console.displayResponse(response.getResultMessage());
        } catch (Exception e) {
            console.displayError(e.getMessage());
        }
    }

    private void removeVouchers() {
        voucherController.deleteVouchers();
    }

    private void removeVoucher() {
        try {
            VoucherRequest request = console.getDeleteVoucherRequest();
            voucherController.deleteVoucher(request);
        } catch (Exception e) {
            console.displayError(e.getMessage());
        }
    }

    public void createCustomer() {
        try {
            CustomerRequest request = console.getCreateCustomerRequest();
            Response<CustomerDTO> response = customerController.createCustomer(request);
            console.displayResponse(response.getResultMessage());
        } catch (Exception e) {
            console.displayError(e.getMessage());
        }
    }

    private void getCustomers() {
        Response<CustomerDTO> response = customerController.getCustomers();
        console.displayResponse(response.getResultMessage());
    }

    private void getCustomer() {
        try {
            CustomerRequest request = console.getSearchCustomerRequest();
            Response<CustomerDTO> response = customerController.getCustomer(request);
            console.displayResponse(response.getResultMessage());
        } catch (Exception e) {
            console.displayError(e.getMessage());
        }

    }

    private void updateCustomer() {
        try {
            CustomerRequest request = console.getUpdateCustomerRequest();
            Response<CustomerDTO> response = customerController.update(request);
            console.displayResponse(response.getResultMessage());
        } catch (Exception e) {
            console.displayError(e.getMessage());
        }
    }

    private void removeCustomers() {
        customerController.deleteCustomers();
    }

    private void removeCustomer() {
        try {
            CustomerRequest request = console.getDeleteCustomerRequest();
            customerController.deleteCustomer(request);
        } catch (Exception e) {
            console.displayError(e.getMessage());
        }
    }

    private void createWallet() {
        try {
            WalletRequest walletRequest = console.getCreateWalletRequest();
            Response<WalletDTO> response = walletController.createWallet(walletRequest);
            console.displayResponse(response.getResultMessage());
        } catch (Exception e) {
            console.displayError(e.getMessage());
        }
    }

    private void getWalletByCustomer() {
        try {
            WalletRequest walletRequest = console.getSearchByCustomerWalletRequest();
            Response<WalletDTO> response = walletController.getWalletByCustomer(walletRequest);
            console.displayResponse(response.getResultMessage());
        } catch (Exception e) {
            console.displayError(e.getMessage());
        }
    }

    private void getWalletByVoucher() {
        try {
            WalletRequest walletRequest = console.getSearchByVoucherWalletRequest();
            Response<WalletDTO> response = walletController.getWalletByVoucher(walletRequest);
            console.displayResponse(response.getResultMessage());
        } catch (Exception e) {
            console.displayError(e.getMessage());
        }
    }

    private void deleteWallet() {
        try {
            WalletRequest walletRequest = console.getDeleteWalletRequest();
            walletController.deleteWallet(walletRequest);
        } catch (Exception e) {
            console.displayError(e.getMessage());
        }
    }

}
