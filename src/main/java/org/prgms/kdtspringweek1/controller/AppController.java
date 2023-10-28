package org.prgms.kdtspringweek1.controller;

import org.prgms.kdtspringweek1.console.ConsoleOutput;
import org.prgms.kdtspringweek1.controller.dto.UpdateCustomerRequestDto;
import org.prgms.kdtspringweek1.customer.CustomerService;
import org.prgms.kdtspringweek1.controller.dto.FindCustomerResponseDto;
import org.prgms.kdtspringweek1.controller.dto.FindVoucherResponseDto;
import org.prgms.kdtspringweek1.customer.entity.Customer;
import org.prgms.kdtspringweek1.voucher.service.VoucherService;
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
    private final static Logger logger = LoggerFactory.getLogger(AppController.class);

    public AppController(ConsoleInputConverter consoleInputConverter, ConsoleOutput consoleOutput, VoucherService voucherService, CustomerService customerService) {
        this.consoleInputConverter = consoleInputConverter;
        this.consoleOutput = consoleOutput;
        this.voucherService = voucherService;
        this.customerService = customerService;
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
            }
        } catch (IllegalArgumentException e) {
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
        try {
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
            logger.error(e.getMessage());
            createVoucher();
        }
        selectFunction();
    }

    private void getAllVouchers() {
        try {
            voucherService.searchAllVouchers()
                    .forEach(FindVoucherResponseDto::printVoucherInfo);
            consoleOutput.printSuccessToSearch();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            logger.error(e.getMessage());
        }
        selectFunction();
    }

    private void getVoucherById() {
        try {
            consoleOutput.printRequestMessageForId();
            voucherService.searchVoucherById(consoleInputConverter.getId())
                    .ifPresentOrElse(
                            findVoucherResponseDto -> {
                                findVoucherResponseDto.printVoucherInfo();
                                consoleOutput.printSuccessToSearch();
                            },
                            consoleOutput::printValueNotFound
                    );
        } catch (Exception e) {
            System.out.println(e.getMessage());
            logger.error(e.getMessage());
        }
        selectFunction();
    }

    private void updateVoucherInfo() {
        try {
            consoleOutput.printRequestMessageForId();
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
            logger.error(e.getMessage());
        }
        selectFunction();
    }

    private void deleteVoucher() {
        try {
            consoleOutput.printRequestMessageForId();
            voucherService.deleteVoucherById(consoleInputConverter.getId())
                    .ifPresentOrElse(
                            voucher -> {
                                consoleOutput.printSuccessToDelete();
                            },
                            consoleOutput::printValueNotFound
                    );
        } catch (Exception e) {
            System.out.println(e.getMessage());
            logger.error(e.getMessage());
        }
        selectFunction();
    }

    private void deleteAllVouchers() {
        try {
            voucherService.deleteAllVouchers();
            consoleOutput.printSuccessToDelete();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            logger.error(e.getMessage());
        }
        selectFunction();
    }

    private void createCustomer() {
        try {
            consoleOutput.printRequestMessageForName();
            customerService.registerCustomer(Customer.createWithName(consoleInputConverter.getCustomerName()));
            consoleOutput.printSuccessToCreate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            logger.error(e.getMessage());
        }
        selectFunction();
    }

    private void getAllCustomers() {
        try {
            customerService.searchAllCustomers()
                    .forEach(FindCustomerResponseDto::printCustomerInfo);
            consoleOutput.printSuccessToSearch();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            logger.error(e.getMessage());
        }
        selectFunction();
    }

    private void getCustomerById() {
        try {
            consoleOutput.printRequestMessageForId();
            customerService.searchCustomerById(consoleInputConverter.getId())
                    .ifPresentOrElse(
                            findCustomerResponseDto -> {
                                findCustomerResponseDto.printCustomerInfo();
                                consoleOutput.printSuccessToSearch();
                            },
                            consoleOutput::printValueNotFound
                    );
        } catch (Exception e) {
            System.out.println(e.getMessage());
            logger.error(e.getMessage());
        }
        selectFunction();
    }

    private void updateCustomerInfo() {
        try {
            consoleOutput.printRequestMessageForId();
            UUID customerId = consoleInputConverter.getId();
            consoleOutput.printRequestMessageForName();
            String name = consoleInputConverter.getCustomerName();
            consoleOutput.printRequestMessageForIsBlackCustomer();
            boolean isBlackCustomer = consoleInputConverter.getIsBlackCustomer();
            customerService.update(new UpdateCustomerRequestDto(customerId, name, isBlackCustomer).toCustomer());
            consoleOutput.printSuccessToUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            logger.error(e.getMessage());
        }
        selectFunction();
    }

    private void deleteCustomer() {
        try {
            consoleOutput.printRequestMessageForId();
            customerService.deleteCustomerById(consoleInputConverter.getId())
                    .ifPresentOrElse(
                            voucher -> {
                                consoleOutput.printSuccessToDelete();
                            },
                            consoleOutput::printValueNotFound
                    );
        } catch (Exception e) {
            System.out.println(e.getMessage());
            logger.error(e.getMessage());
        }
        selectFunction();
    }

    private void deleteAllCustomers() {
        try {
            customerService.deleteAllCustomers();
            consoleOutput.printSuccessToDelete();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            logger.error(e.getMessage());
        }
        selectFunction();
    }

    private void getAllBlackCustomers() {
        try {
            customerService.searchAllBlackCustomers()
                    .forEach(FindCustomerResponseDto::printCustomerInfo);
            consoleOutput.printSuccessToSearch();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            logger.error(e.getMessage());
        }
        selectFunction();
    }
}
