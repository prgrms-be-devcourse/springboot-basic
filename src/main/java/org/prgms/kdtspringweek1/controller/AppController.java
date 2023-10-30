package org.prgms.kdtspringweek1.controller;

import org.prgms.kdtspringweek1.console.ConsoleOutput;
import org.prgms.kdtspringweek1.controller.dto.customerDto.UpdateCustomerRequestDto;
import org.prgms.kdtspringweek1.customer.service.CustomerService;
import org.prgms.kdtspringweek1.controller.dto.customerDto.FindCustomerResponseDto;
import org.prgms.kdtspringweek1.controller.dto.voucherDto.FindVoucherResponseDto;
import org.prgms.kdtspringweek1.customer.entity.Customer;
import org.prgms.kdtspringweek1.voucher.service.VoucherService;
import org.prgms.kdtspringweek1.wallet.entity.Wallet;
import org.prgms.kdtspringweek1.wallet.service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

// 뷰 영역(console 패키지)에 컨트롤러가 포함되어 있어, 따로 빼주었습니다.
// 기존에, 입출력 관련 기능들이 컨트롤러에 명시적으로 드러나 있어, 콘솔 입출력의 경우 따로 클래스를 두어 구현
@Component
public class AppController {
    private final ConsoleInputConverter consoleInputConverter;
    private final ConsoleOutput consoleOutput;
    private final VoucherService voucherService;
    private final CustomerService customerService;
    private final WalletService walletService;
    private final static Logger logger = LoggerFactory.getLogger(AppController.class);

    public AppController(ConsoleInputConverter consoleInputConverter, ConsoleOutput consoleOutput, VoucherService voucherService, CustomerService customerService, WalletService walletService) {
        this.consoleInputConverter = consoleInputConverter;
        this.consoleOutput = consoleOutput;
        this.voucherService = voucherService;
        this.customerService = customerService;
        this.walletService = walletService;
    }

    public void startVoucherProgram() {
        selectFunction();
    }

    private void selectFunction() {
        consoleOutput.printFunctionsToSelect();
        try {
            switch (consoleInputConverter.getFunctionType()) {
                case EXIT -> exitVoucherProgram();
                case CREATE_VOUCHER -> createVoucher();
                case LIST_VOUCHERS -> getAllVouchers();
                case SEARCH_VOUCHER -> getVoucherById();
                case UPDATE_VOUCHER -> updateVoucherInfo();
                case DELETE_ALL_VOUCHERS -> deleteAllVouchers();
                case DELETE_VOUCHER -> deleteVoucher();
                case CREATE_CUSTOMER -> createCustomer();
                case LIST_CUSTOMERS -> getAllCustomers();
                case SEARCH_CUSTOMER -> getCustomerById();
                case UPDATE_CUSTOMER -> updateCustomerInfo();
                case DELETE_ALL_CUSTOMERS -> deleteAllCustomers();
                case DELETE_CUSTOMER -> deleteCustomer();
                case LIST_BLACK_CUSTOMERS -> getAllBlackCustomers();
                case CREATE_MY_WALLET -> createWallet();
                case SEARCH_MY_VOUCHERS -> getAllVouchersOwnedByCustomer();
                case DELETE_MY_WALLET -> deleteWallet();
                case SEARCH_MY_CUSTOMERS -> getAllCustomersOwnedByVoucher();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            logger.error(e.getMessage());
            selectFunction();
        }
    }

    private void exitVoucherProgram() {
        consoleOutput.printExitMessage();
        System.exit(0);
    }

    private void createVoucher() {
        consoleOutput.printVouchersToSelect();
        switch (consoleInputConverter.getVoucherType()) {
            case FIXED_AMOUNT -> {
                consoleOutput.printRequestMessageforDiscountValue();
                voucherService.registerVoucher(consoleInputConverter.getCreateVoucherRequestDto().toFixedAmountVoucher());
            }
            case PERCENT_DISCOUNT -> {
                consoleOutput.printRequestMessageforDiscountValue();
                voucherService.registerVoucher(consoleInputConverter.getCreateVoucherRequestDto().toPercentDiscountVoucher());
            }
        }
        consoleOutput.printSuccessToCreate();
        selectFunction();
    }

    private void getAllVouchers() {
        voucherService.searchAllVouchers()
                .forEach(FindVoucherResponseDto::printVoucherInfo);
        consoleOutput.printSuccessToSearch();
        selectFunction();
    }

    private void getVoucherById() {
        consoleOutput.printRequestMessageForVoucherId();
        voucherService.searchVoucherById(consoleInputConverter.getId())
                .ifPresentOrElse(
                        findVoucherResponseDto -> {
                            findVoucherResponseDto.printVoucherInfo();
                            consoleOutput.printSuccessToSearch();
                        },
                        consoleOutput::printValueNotFound
                );
        selectFunction();
    }

    private void updateVoucherInfo() {
        consoleOutput.printRequestMessageForVoucherId();
        UUID voucherId = consoleInputConverter.getId();
        consoleOutput.printVouchersToSelect();
        switch (consoleInputConverter.getVoucherType()) {
            case FIXED_AMOUNT -> {
                consoleOutput.printRequestMessageforDiscountValue();
                voucherService.update(consoleInputConverter.getUpdateVoucherRequestDto(voucherId).toFixedAmountVoucher());
            }
            case PERCENT_DISCOUNT -> {
                consoleOutput.printRequestMessageforDiscountValue();
                voucherService.update(consoleInputConverter.getUpdateVoucherRequestDto(voucherId).toPercentDiscountVoucher());
            }
        }
        consoleOutput.printSuccessToUpdate();
        selectFunction();
    }

    private void deleteVoucher() {
        consoleOutput.printRequestMessageForVoucherId();
        voucherService.deleteVoucherById(consoleInputConverter.getId());
        consoleOutput.printSuccessToDelete();
        selectFunction();
    }

    private void deleteAllVouchers() {
        voucherService.deleteAllVouchers();
        consoleOutput.printSuccessToDelete();
        selectFunction();
    }

    private void createCustomer() {
        consoleOutput.printRequestMessageForName();
        customerService.registerCustomer(Customer.createWithName(consoleInputConverter.getCustomerName()));
        consoleOutput.printSuccessToCreate();
        selectFunction();
    }

    private void getAllCustomers() {
        customerService.searchAllCustomers()
                .forEach(FindCustomerResponseDto::printCustomerInfo);
        consoleOutput.printSuccessToSearch();
        selectFunction();
    }

    private void getCustomerById() {
        consoleOutput.printRequestMessageForCustomerId();
        customerService.searchCustomerById(consoleInputConverter.getId())
                .ifPresentOrElse(
                        findCustomerResponseDto -> {
                            findCustomerResponseDto.printCustomerInfo();
                            consoleOutput.printSuccessToSearch();
                        },
                        consoleOutput::printValueNotFound
                );
        selectFunction();
    }

    private void updateCustomerInfo() {
        consoleOutput.printRequestMessageForCustomerId();
        UUID customerId = consoleInputConverter.getId();
        consoleOutput.printRequestMessageForName();
        String name = consoleInputConverter.getCustomerName();
        consoleOutput.printRequestMessageForIsBlackCustomer();
        boolean isBlackCustomer = consoleInputConverter.getIsBlackCustomer();
        customerService.update(new UpdateCustomerRequestDto(customerId, name, isBlackCustomer).toCustomer());
        consoleOutput.printSuccessToUpdate();
        selectFunction();
    }

    private void deleteCustomer() {
        consoleOutput.printRequestMessageForCustomerId();
        customerService.deleteCustomerById(consoleInputConverter.getId());
        consoleOutput.printSuccessToDelete();
        selectFunction();
    }

    private void deleteAllCustomers() {
        customerService.deleteAllCustomers();
        consoleOutput.printSuccessToDelete();
        selectFunction();
    }

    private void getAllBlackCustomers() {
        customerService.searchAllBlackCustomers()
                .forEach(FindCustomerResponseDto::printCustomerInfo);
        consoleOutput.printSuccessToSearch();
        selectFunction();
    }

    private void createWallet() {
        consoleOutput.printRequestMessageForCustomerId();
        UUID customerId = consoleInputConverter.getId();
        consoleOutput.printRequestMessageForVoucherId();
        UUID voucherId = consoleInputConverter.getId();
        walletService.registerWallet(Wallet.createWithVoucherIdAndCustomerId(voucherId, customerId));
        consoleOutput.printSuccessToCreate();
        selectFunction();
    }

    private void getAllVouchersOwnedByCustomer() {
        consoleOutput.printRequestMessageForCustomerId();
        walletService.searchAllVouchersByCustomerId(consoleInputConverter.getId())
                .forEach(FindVoucherResponseDto::printVoucherInfo);
        consoleOutput.printSuccessToSearch();
        selectFunction();
    }

    private void deleteWallet() {
        consoleOutput.printRequestMessageForCustomerId();
        UUID customerId = consoleInputConverter.getId();
        consoleOutput.printRequestMessageForVoucherId();
        UUID voucherId = consoleInputConverter.getId();
        walletService.deleteWalletByVoucherIdAndCustomerId(voucherId, customerId);
        consoleOutput.printSuccessToDelete();
        selectFunction();
    }

    private void getAllCustomersOwnedByVoucher() {
        consoleOutput.printRequestMessageForVoucherId();
        walletService.searchAllCustomersByVoucherId(consoleInputConverter.getId())
                .forEach(FindCustomerResponseDto::printCustomerInfo);
        consoleOutput.printSuccessToSearch();
        selectFunction();
    }
}
