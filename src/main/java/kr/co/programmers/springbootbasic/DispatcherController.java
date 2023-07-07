package kr.co.programmers.springbootbasic;

import kr.co.programmers.springbootbasic.customer.controller.CustomerController;
import kr.co.programmers.springbootbasic.customer.domain.CustomerStatus;
import kr.co.programmers.springbootbasic.customer.dto.CustomerResponse;
import kr.co.programmers.springbootbasic.io.Input;
import kr.co.programmers.springbootbasic.io.Output;
import kr.co.programmers.springbootbasic.io.enums.*;
import kr.co.programmers.springbootbasic.util.ApplicationUtils;
import kr.co.programmers.springbootbasic.voucher.controller.VoucherController;
import kr.co.programmers.springbootbasic.voucher.domain.VoucherType;
import kr.co.programmers.springbootbasic.voucher.dto.VoucherResponse;
import kr.co.programmers.springbootbasic.wallet.controller.WalletController;
import kr.co.programmers.springbootbasic.wallet.dto.WalletResponse;
import kr.co.programmers.springbootbasic.wallet.dto.WalletSaveDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Controller;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class DispatcherController implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherController.class);
    private final Input inputConsole;
    private final Output outputConsole;
    private final VoucherController voucherController;
    private final CustomerController customerController;
    private final WalletController walletController;
    private boolean isExit;

    public DispatcherController(Input inputConsole, Output outputConsole, VoucherController voucherController, CustomerController customerController, WalletController walletController) {
        this.inputConsole = inputConsole;
        this.outputConsole = outputConsole;
        this.voucherController = voucherController;
        this.customerController = customerController;
        this.walletController = walletController;
    }

    @Override
    public void run(ApplicationArguments args) {
        while (!isExit) {
            executeServiceLoop();
        }
        logger.info("서비스를 종료합니다.");
        outputConsole.printExit();
    }

    private void executeServiceLoop() {
        try {
            doService();
        } catch (RuntimeException e) {
            outputConsole.printMessage(e.getMessage());
        }
    }

    private void doService() {
        outputConsole.printProgramMenu();
        EntireServiceCommand command = inputConsole.readEntireServiceCommand();

        switch (command) {
            case EXIT -> isExit = true;
            case VOUCHER_SERVICE -> doVoucherService();
            case CUSTOMER_SERVICE -> doCustomerService();
            case WALLET_SERVICE -> doWalletService();
        }
    }

    private void doVoucherService() {
        outputConsole.printVoucherServiceMenu();
        VoucherServiceCommand command = inputConsole.readVoucherCommand();
        switch (command) {
            case CREATE_VOUCHER -> createVoucher();
            case LIST_ALL_VOUCHERS -> listAllVouchers();
        }
    }

    private void doCustomerService() {
        outputConsole.printCustomerServiceMenu();
        CustomerServiceCommand command = inputConsole.readCustomerServiceCommand();
        switch (command) {
            case CREATE_CUSTOMER -> createCustomer();
            case FIND_CUSTOMER -> findCustomer();
            case UPDATE_CUSTOMER -> updateCustomer();
            case DELETE_CUSTOMER -> deleteCustomerById();
        }
    }

    private void doWalletService() {
        outputConsole.printWalletServiceMenu();
        WalletServiceCommand command = inputConsole.readWalletServiceCommand();
        switch (command) {
            case SAVE_VOUCHER_IN_WALLET -> saveVoucherInWallet();
            case FIND_WALLET_BY_WALLET_ID -> findWalletByWalletId();
        }
    }

    private void createVoucher() {
        outputConsole.printVoucherCreateMenu();
        VoucherType type = inputConsole.readVoucherType();

        outputConsole.printAmountEnterMessage(type);
        long amount = inputConsole.readAmount();

        VoucherResponse voucherDto = voucherController.createVoucher(type, amount);
        outputConsole.printVoucherMessage(voucherDto);
    }

    private void listAllVouchers() {
        List<VoucherResponse> voucherDtos = voucherController.listAllVoucher();
        outputConsole.printVoucherListMessage(voucherDtos);
    }

    private void createCustomer() {
        outputConsole.printCustomerCreateMessage();
        String customerName = inputConsole.readCustomerName();
        CustomerResponse createdCustomer = customerController.createCustomer(customerName);
        outputConsole.printCustomerMessage(createdCustomer);
    }

    private void findCustomer() {
        outputConsole.printCustomerFindMenu();
        CustomerFindCommand command = inputConsole.readCustomerFindCommand();
        switch (command) {
            case FIND_BY_CUSTOMER_ID -> findByCustomerId();
            case FIND_BY_VOUCHER_ID -> findByVoucherId();
            case FIND_ALL -> findAllCustomers();
            case FIND_ALL_BLACK_CUSTOMER -> findAllBlackCustomer();
        }
    }

    private void findByVoucherId() {
        outputConsole.printVoucherUuidTypeMessage();
        String voucherId = inputConsole.readUUID();

        Optional<CustomerResponse> customerResponse = customerController.findByVoucherId(voucherId);

        if (customerResponse.isPresent()) {
            outputConsole.printCustomerMessage(customerResponse.get());
        } else {
            outputConsole.printNoResult();
        }
    }

    private void findByCustomerId() {
        outputConsole.printCustomerUuidTypeMessage();
        String customerId = inputConsole.readUUID();

        Optional<CustomerResponse> customerResponse = customerController.findByCustomerId(customerId);

        if (customerResponse.isPresent()) {
            outputConsole.printCustomerMessage(customerResponse.get());
        } else {
            outputConsole.printNoResult();
        }
    }

    private void findAllCustomers() {
        List<CustomerResponse> customerResponses = customerController.findAllCustomers();
        outputConsole.printCustomerListMessage(customerResponses);
    }

    private void findAllBlackCustomer() {
        List<CustomerResponse> customerResponse = customerController.findAllBlackCustomer();
        outputConsole.printCustomerListMessage(customerResponse);
    }

    private void updateCustomer() {
        outputConsole.printCustomerUuidTypeMessage();
        String customerUUID = inputConsole.readUUID();
        outputConsole.printTypeCustomerStatus();
        CustomerStatus customerStatus = inputConsole.readCustomerStatus();

        CustomerResponse customerResponse = customerController.updateCustomer(customerUUID, customerStatus);

        outputConsole.printCustomerMessage(customerResponse);
    }

    private void deleteCustomerById() {
        outputConsole.printCustomerUuidTypeMessage();
        String customerId = inputConsole.readUUID();

        customerController.deleteById(customerId);

        outputConsole.printCustomerDeleteMessage(customerId);
    }

    private void saveVoucherInWallet() {
        outputConsole.printVoucherUuidTypeMessage();
        String voucherId = inputConsole.readUUID();
        outputConsole.printWalletUuidTypeMessage();
        String walletId = inputConsole.readUUID();

        WalletSaveDto requestDto = new WalletSaveDto(voucherId, walletId);
        WalletSaveDto responseDto = walletController.saveVoucherInWallet(requestDto);

        outputConsole.printWalletSaveMessage(responseDto);
    }

    private void findWalletByWalletId() {
        outputConsole.printWalletUuidTypeMessage();
        String walletId = inputConsole.readUUID();

        WalletResponse walletResponse = walletController.findWalletById(walletId);

        outputConsole.printWalletFindMessage(walletResponse);
    }
}
