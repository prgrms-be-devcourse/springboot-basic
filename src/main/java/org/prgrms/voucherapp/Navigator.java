package org.prgrms.voucherapp;

import org.prgrms.voucherapp.engine.customer.service.CustomerService;
import org.prgrms.voucherapp.engine.voucher.service.VoucherService;
import org.prgrms.voucherapp.engine.wallet.service.WalletService;
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
    private final WalletService walletService;
    private final Logger logger = LoggerFactory.getLogger(Navigator.class);

    public Navigator(Input input, Output output, VoucherService voucherService, CustomerService customerService, WalletService walletService) {
        this.input = input;
        this.output = output;
        this.voucherService = voucherService;
        this.customerService = customerService;
        this.walletService = walletService;
    }

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
                        customerService.removeCustomer(customerService.getCustomer(customerId));
                        output.completeMessage("삭제");
                    }
                    case UPDATE -> {
                        logger.info("User chose update command.");
                        UUID customerId = input.UUIDInput("Type a customer ID : ");
                        output.informCustomerStatus();
                        Optional<CustomerStatus> status = input.customerStatusInput("Type a customer status's number(press enter to skip) : ");
                        String name = input.customerNameInput("Type a customer's name : ");
                        customerService.updateCustomer(customerService.getCustomer(customerId), name, status);
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
                WalletCommand userCommand = input.walletCommandInput("Type a number of the command : ");
                switch (userCommand) {
                    case CANCEL -> {
                        logger.info("User chose cancel command.");
                        output.cancelMessage();
                        return;
                    }
                    case LIST -> {
                        logger.info("User chose list command.");
                        output.infoMessage(walletService.getWalletListByStr());
                    }
                    case ASSIGN_VOUCHER -> {
                        logger.info("User chose assign_voucher command.");
                        UUID customerId = input.UUIDInput("Type a customer ID : ");
                        UUID voucherId = input.UUIDInput("Type a voucher ID : ");
                        walletService.assignVoucherToCustomer(UUID.randomUUID(), customerId, voucherId);
                        output.completeMessage("할당");
                    }
                    case GET_VOUCHER -> {
                        logger.info("User chose get_voucher command.");
                        UUID customerId = input.UUIDInput("Type a customer ID : ");
                        output.infoMessage(walletService.getVouchersOfCustomerByStr(customerService.getCustomer(customerId)));
                    }
                    case DELETE_VOUCHER -> {
                        logger.info("User chose delete_voucher command.");
                        UUID walletId = input.UUIDInput("Type a wallet ID : ");
                        walletService.removeByWalletId(walletId);
                        output.completeMessage("삭제");
                    }
                    case GET_CUSTOMER -> {
                        UUID voucherId = input.UUIDInput("Type a voucher ID : ");
                        output.infoMessage(walletService.getCustomersOfVoucherByStr(voucherService.getVoucher(voucherId)));
                    }
                }
            } catch (Exception e) {
                logger.error("Wallet : Exception has occurred for the reason : %s".formatted(e.toString()));
                output.errorMessage(e.toString());
            }
        }
    }
}
