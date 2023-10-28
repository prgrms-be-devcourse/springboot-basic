package com.programmers.vouchermanagement.wallet.presentation;

import com.programmers.vouchermanagement.customer.dto.CustomerResponseDto;
import com.programmers.vouchermanagement.customer.exception.CustomerNotFoundException;
import com.programmers.vouchermanagement.utils.ConsoleInputManager;
import com.programmers.vouchermanagement.utils.ConsoleOutputManager;
import com.programmers.vouchermanagement.voucher.dto.VoucherResponseDto;
import com.programmers.vouchermanagement.voucher.exception.VoucherNotFoundException;
import com.programmers.vouchermanagement.wallet.application.WalletService;
import com.programmers.vouchermanagement.wallet.dto.WalletRequestDto;
import com.programmers.vouchermanagement.wallet.exception.WalletNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class WalletController {

    private static final Logger logger = LoggerFactory.getLogger(WalletController.class);

    private final ConsoleInputManager consoleInputManager;
    private final ConsoleOutputManager consoleOutputManager;
    private final WalletService walletService;

    public WalletController(ConsoleInputManager consoleInputManager, ConsoleOutputManager consoleOutputManager, WalletService walletService) {
        this.consoleInputManager = consoleInputManager;
        this.consoleOutputManager = consoleOutputManager;
        this.walletService = walletService;
    }

    public void createWallet() {

        consoleOutputManager.printCreateWallet();

        consoleOutputManager.printGetCustomerId();
        UUID customerId = UUID.fromString(consoleInputManager.inputString());

        consoleOutputManager.printGetVoucherId();
        UUID voucherId = UUID.fromString(consoleInputManager.inputString());

        try {
            walletService.createWallet(new WalletRequestDto(customerId, voucherId));

        } catch (CustomerNotFoundException e) {
            logger.error(e.getMessage() + "Console Input : " + customerId, e);

            consoleOutputManager.printReturnMain(e.getMessage());
            return;

        } catch (VoucherNotFoundException e) {
            logger.error(e.getMessage() + "Console Input : " + voucherId, e);

            consoleOutputManager.printReturnMain(e.getMessage());
            return;
        }

        consoleOutputManager.printSuccessCreation();
    }

    public void readVouchersByCustomer() {

        consoleOutputManager.printReadVouchersByCustomer();

        consoleOutputManager.printGetCustomerId();
        UUID customerId = UUID.fromString(consoleInputManager.inputString());

        try {
            List<VoucherResponseDto> voucherResponseDtos = walletService.readVouchersByCustomer(customerId);
            consoleOutputManager.printVoucherInfo(voucherResponseDtos);

        } catch (WalletNotFoundException e) {
            logger.error(e.getMessage() + "Console Input Customer Id : " + customerId, e);

            consoleOutputManager.printReturnMain(e.getMessage());

        } catch (VoucherNotFoundException e) {
            logger.error(e.getMessage(), e);

            consoleOutputManager.printReturnMain(e.getMessage());
        }
    }

    public void readCustomersByVoucher() {

        consoleOutputManager.printReadCustomersByVoucher();

        consoleOutputManager.printGetVoucherId();
        UUID voucherId = UUID.fromString(consoleInputManager.inputString());

        try {
            List<CustomerResponseDto> customerResponseDtos = walletService.readCustomersByVoucher(voucherId);
            consoleOutputManager.printCustomerInfo(customerResponseDtos);

        } catch (WalletNotFoundException e) {
            logger.error(e.getMessage() + "Console Input Voucher Id : " + voucherId, e);

            consoleOutputManager.printReturnMain(e.getMessage());

        } catch (CustomerNotFoundException e) {
            logger.error(e.getMessage(), e);

            consoleOutputManager.printReturnMain(e.getMessage());
        }
    }

    public void removeWalletsByCustomer() {

        consoleOutputManager.printRemoveWallet();

        consoleOutputManager.printGetCustomerId();
        UUID customerId = UUID.fromString(consoleInputManager.inputString());

        try {
            walletService.removeWalletsByCustomer(customerId);
        } catch (CustomerNotFoundException e) {
            logger.error(e.getMessage() + "Console Input Customer Id : " + customerId, e);

            consoleOutputManager.printReturnMain(e.getMessage());
        }
    }
}
