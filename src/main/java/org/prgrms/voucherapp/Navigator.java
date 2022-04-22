package org.prgrms.voucherapp;

import org.prgrms.voucherapp.engine.customer.service.CustomerService;
import org.prgrms.voucherapp.engine.voucher.service.VoucherService;
import org.prgrms.voucherapp.global.enums.*;
import org.prgrms.voucherapp.io.Input;
import org.prgrms.voucherapp.io.Output;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.UUID;

public class Navigator implements Runnable {

    private final Input input;
    private final Output output;
    private final VoucherService voucherService;
    private final CustomerService customerService;
    private final Logger logger = LoggerFactory.getLogger(Navigator.class);

    public Navigator(Input input, Output output, VoucherService voucherService, CustomerService customerService) {
        this.input = input;
        this.output = output;
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    //    TODO : 지금은 모든 exception에 대해서 프로그램 초기부터 시작함. create에서 예외 발생시, create부터 다시 시작할 수 있도록 변경해보자.
    //    TODO : 테스트 코드 작성
    //    TODO : 고객리스트 심화 과제
    //    TODO : properties yaml 외부 설정 주입
    @Override
    public void run() {
        while (true) {
            try {
                output.informModuleMenu();
                ModuleCommand selectedModule = input.moduleInput("Type a module name : ");
                switch (selectedModule) {
                    case EXIT -> {
                        output.exitMessage();
                        return;
                    }
                    case VOUCHER -> executeVoucherCommand();
                    case CUSTOMER -> executeCustomerCommand();
                    case WALLET -> executeWalletCommand();
                }
            } catch (Exception e) {
                logger.error("Main : Exception has occurred for the reason : %s".formatted(e.toString()));
                output.errorMessage(e.toString());
            }
        }
    }

    private void executeVoucherCommand() {
        while (true) {
            try {
                output.informCrudCommand(ModuleCommand.VOUCHER);
                CrudCommand userCommand = input.crudCommandInput("Type a command : ");
                switch (userCommand) {
                    case CANCEL -> {
                        logger.info("User chose cancel command.");
                        output.cancelMessage();
                        return;
                    }
                    case CREATE -> {
                        logger.info("User chose create command.");
                        output.informVoucherTypeFormat();
                        VoucherType voucherType = input.voucherTypeInput("Type a voucher type's number : ");
                        long discountAmount = input.discountAmountInput(voucherType, "Type discount amount(0 < x <= %d) : ".formatted(voucherType.getMaxDiscountAmount()));
                        voucherService.createVoucher(voucherType, UUID.randomUUID(), discountAmount);
                        output.completeMessage("생성");
                    }
                    case LIST -> {
                        logger.info("User chose list command.");
                        output.infoMessage(voucherService.getVoucherListByStr());
                    }
                    case DELETE -> {
                        logger.info("User chose delete command.");
                        UUID voucherId = input.UUIDInput("Type a voucher ID : ");
                        voucherService.removeVoucher(voucherId);
                        output.completeMessage("삭제");
                    }
                    case UPDATE -> {
                        logger.info("User chose update command.");
                        UUID voucherId = input.UUIDInput("Type a voucher ID : ");
                        output.informVoucherTypeFormat();
                        VoucherType newVoucherType = input.voucherTypeInput("Type a new voucher type's number : ");
                        long newDiscountAmount = input.discountAmountInput(newVoucherType, "Type discount amount of a new voucher(0 < x <= %d) : ".formatted(newVoucherType.getMaxDiscountAmount()));
                        voucherService.updateVoucher(voucherId, newVoucherType, newDiscountAmount);
                        output.completeMessage("수정");
                    }
                }
            } catch (Exception e) {
                logger.error("Voucher : Exception has occurred for the reason : %s".formatted(e.toString()));
                output.errorMessage(e.toString());
            }
        }
    }

    private void executeCustomerCommand() {
        while (true) {
            try {
                output.informCrudCommand(ModuleCommand.CUSTOMER);
                CrudCommand userCommand = input.crudCommandInput("Type a command : ");
                switch (userCommand) {
                    case CANCEL -> {
                        logger.info("User chose cancel command.");
                        output.cancelMessage();
                        return;
                    }
                    case CREATE -> {
                        logger.info("User chose create command.");
                        output.informCustomerStatus();
                        Optional<CustomerStatus> status = input.customerStatusInput("Type a customer status's number(press enter to skip) : ");
                        String name = input.customerNameInput("Type a customer's name : ");
                        String email = input.customerEmailInput("Type a customer's email : ");
                        customerService.createCustomer(UUID.randomUUID(), name, email, status);
                        output.completeMessage("생성");
                    }
                    case LIST -> {
                        logger.info("User chose list command.");
                        output.infoMessage(customerService.getCustomerListByStr());
                    }
                    case DELETE -> {
                        logger.info("User chose delete command.");
                        UUID customerId = input.UUIDInput("Type a customer ID : ");
                        customerService.removeCustomer(customerId);
                        output.completeMessage("삭제");
                    }
                    case UPDATE -> {
                        logger.info("User chose update command.");
                        UUID customerId = input.UUIDInput("Type a customer ID : ");
                        output.informCustomerStatus();
                        Optional<CustomerStatus> status = input.customerStatusInput("Type a customer status's number(press enter to skip) : ");
                        String name = input.customerNameInput("Type a customer's name : ");
                        customerService.updateCustomer(customerId, name, status);
                        output.completeMessage("수정");
                    }
                }
            } catch (Exception e) {
                logger.error("Customer : Exception has occurred for the reason : %s".formatted(e.toString()));
                output.errorMessage(e.toString());
            }
        }
    }

    private void executeWalletCommand() {
        while (true) {
            try {
                output.informWalletCommand();
                WalletCommand userCommand = input.walletCommandInput("Type a command : ");
                switch (userCommand) {
                    case CANCEL -> {
                        logger.info("User chose cancel command.");
                        output.cancelMessage();
                        return;
                    }
                    case LIST -> {
                        logger.info("User chose list command.");

                    }
                    case ASSIGN_VOUCHER -> {
                        logger.info("User chose list command.");
                    }
                    case GET_VOUCHER -> {
                        logger.info("User chose delete command.");
                    }
                    case DELETE_VOUCHER -> {
                        logger.info("User chose update command.");
                    }
                    case GET_CUSTOMER -> {

                    }
                }
            } catch (Exception e) {
                logger.error("Customer : Exception has occurred for the reason : %s".formatted(e.toString()));
                output.errorMessage(e.toString());
            }
        }
    }
}
