package com.example.voucher;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import com.example.voucher.constant.ModeType;
import com.example.voucher.constant.ServiceType;
import com.example.voucher.customer.controller.CustomerController;
import com.example.voucher.customer.controller.model.CustomerRequest;
import com.example.voucher.customer.controller.model.CustomerResponse;
import com.example.voucher.io.Console;
import com.example.voucher.voucher.controller.VoucherController;
import com.example.voucher.voucher.controller.model.VoucherRequest;
import com.example.voucher.voucher.controller.model.VoucherResponse;
import com.example.voucher.wallet.controller.WalletController;
import com.example.voucher.wallet.controller.model.WalletRequest;
import com.example.voucher.wallet.controller.model.WalletResponse;

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
        VoucherRequest request = console.getCreateVoucherRequest();
        try {
            VoucherResponse response = voucherController.createVoucher(request);
            console.displayVoucherResponse(response);
        } catch (Exception e) {
            console.displayError(e.getMessage());
        }
    }

    private void getVouchers() {
        VoucherResponse response = voucherController.getVouchers();
        console.displayVoucherResponse(response);
    }

    private void getVoucher() {
        VoucherRequest request = console.getSearchVoucherRequest();

        VoucherResponse response = voucherController.getVoucher(request);

        console.displayVoucherResponse(response);
    }

    private void updateVoucher() {
        VoucherRequest request = console.getUpdateVoucherRequest();
        try {
            VoucherResponse response = voucherController.update(request);
            console.displayVoucherResponse(response);
        } catch (Exception e) {
            console.displayError(e.getMessage());
        }
    }

    private void removeVouchers() {
        voucherController.deleteVouchers();
    }

    private void removeVoucher() {
        VoucherRequest request = console.getDeleteVoucherRequest();
        try {
            voucherController.deleteVoucher(request);
        } catch (Exception e) {
            console.displayError(e.getMessage());
        }
    }

    public void createCustomer() {
        CustomerRequest request = console.getCreateCustomerRequest();
        try {
            CustomerResponse response = customerController.createCustomer(request);
            console.displayCustomerResponse(response);
        } catch (Exception e) {
            console.displayError(e.getMessage());
        }
    }

    private void getCustomers() {
        CustomerResponse response = customerController.getCustomers();
        console.displayCustomerResponse(response);
    }

    private void getCustomer() {
        CustomerRequest request = console.getSearchCustomerRequest();
        try {
            CustomerResponse response = customerController.getCustomer(request);
            console.displayCustomerResponse(response);
        } catch (Exception e) {
            console.displayError(e.getMessage());
        }
    }

    private void updateCustomer() {
        CustomerRequest request = console.getUpdateCustomerRequest();
        try {
            CustomerResponse response = customerController.update(request);
            console.displayCustomerResponse(response);
        } catch (Exception e) {
            console.displayError(e.getMessage());
        }
    }

    private void removeCustomers() {
        customerController.deleteCustomers();
    }

    private void removeCustomer() {
        CustomerRequest request = console.getDeleteCustomerRequest();
        try {
            customerController.deleteCustomer(request);
        } catch (Exception e) {
            console.displayError(e.getMessage());
        }
    }

    private void createWallet() {
        WalletRequest walletRequest = console.getCreateWalletRequest();
        try {
            WalletResponse response = walletController.createWallet(walletRequest);
            console.displayWalletResponse(response);
        } catch (Exception e) {
            console.displayError(e.getMessage());
        }
    }

    private void getWalletByCustomer() {
        WalletRequest walletRequest = console.getSearchByCustomerWalletRequest();
        try {
            WalletResponse response = walletController.getWalletByCustomer(walletRequest);
            console.displayWalletResponse(response);
        } catch (Exception e) {
            console.displayError(e.getMessage());
        }
    }

    private void getWalletByVoucher() {
        WalletRequest walletRequest = console.getSearchByVoucherWalletRequest();
        try {
            WalletResponse response = walletController.getWalletByVoucher(walletRequest);
            console.displayWalletResponse(response);
        } catch (Exception e) {
            console.displayError(e.getMessage());
        }
    }

    private void deleteWallet() {
        WalletRequest walletRequest = console.getDeleteWalletRequest();
        try {
            walletController.deleteWallet(walletRequest);
        } catch (Exception e) {
            console.displayError(e.getMessage());
        }
    }

}
