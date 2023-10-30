package com.programmers.vouchermanagement;

import com.programmers.vouchermanagement.customer.dto.CustomerResponseDto;
import com.programmers.vouchermanagement.customer.presentation.CustomerController;
import com.programmers.vouchermanagement.utils.Command;
import com.programmers.vouchermanagement.exception.CommandNotFoundException;
import com.programmers.vouchermanagement.utils.ConsoleInputManager;
import com.programmers.vouchermanagement.utils.ConsoleOutputManager;
import com.programmers.vouchermanagement.voucher.dto.VoucherRequestDto;
import com.programmers.vouchermanagement.voucher.dto.VoucherResponseDto;
import com.programmers.vouchermanagement.voucher.presentation.VoucherController;
import com.programmers.vouchermanagement.wallet.presentation.WalletController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class VoucherManagementController implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(VoucherManagementController.class);

    private final ConsoleInputManager consoleInputManager;
    private final ConsoleOutputManager consoleOutputManager;
    private final VoucherController voucherController;
    private final CustomerController customerController;
    private final WalletController walletController;

    @Value("${command-line-runner.run}")
    private boolean isRunning;

    public VoucherManagementController(ConsoleInputManager consoleInputManager, ConsoleOutputManager consoleOutputManager, VoucherController voucherController, CustomerController customerController, WalletController walletController) {
        this.consoleInputManager = consoleInputManager;
        this.consoleOutputManager = consoleOutputManager;
        this.voucherController = voucherController;
        this.customerController = customerController;
        this.walletController = walletController;
    }

    @Override
    public void run(String... args) {

        if (isRunning) {
            logger.info("Start Voucher Program.");

            boolean chooseExit = false;

            while (!chooseExit) {

                consoleOutputManager.printCommandMenu();

                Command command;
                String input = consoleInputManager.inputString();

                try {
                    command = Command.getCommandByNumber(input);

                } catch (CommandNotFoundException e) {
                    logger.error(e.getMessage(), e);
                    consoleOutputManager.printEnterAgain(e.getMessage());

                    continue;
                }

                try {
                    switch (command) {

                        case CREATE_VOUCHER -> {

                            consoleOutputManager.printCreateVoucher();

                            consoleOutputManager.printVoucherTypeMenu();
                            String voucherType = consoleInputManager.inputString();

                            consoleOutputManager.printDiscountRequest();
                            Long discount = consoleInputManager.inputDiscount();

                            voucherController.createVoucher(new VoucherRequestDto(voucherType, discount));

                            consoleOutputManager.printSuccessCreation();
                        }

                        case LIST_VOUCHER -> {

                            consoleOutputManager.printList();

                            List<VoucherResponseDto> voucherResponseDtos = voucherController.readAllVoucher();
                            consoleOutputManager.printVoucherInfo(voucherResponseDtos);
                        }

                        case ONE_VOUCHER -> {

                            consoleOutputManager.printReadVoucherById();

                            consoleOutputManager.printGetVoucherId();
                            UUID voucherId = consoleInputManager.inputUUID();

                            VoucherResponseDto voucherResponseDto = voucherController.readVoucherById(voucherId);
                            consoleOutputManager.printVoucherInfo(new ArrayList<>() {{
                                add(voucherResponseDto);
                            }});
                        }

                        case UPDATE_VOUCHER -> {

                            consoleOutputManager.printUpdateVoucher();

                            consoleOutputManager.printGetVoucherId();
                            UUID voucherId = consoleInputManager.inputUUID();

                            consoleOutputManager.printVoucherTypeMenu();
                            String voucherType = consoleInputManager.inputString();

                            consoleOutputManager.printDiscountRequest();
                            Long discount = consoleInputManager.inputDiscount();

                            voucherController.updateVoucher(voucherId, new VoucherRequestDto(voucherType, discount));

                            consoleOutputManager.printSuccessUpdate();
                        }

                        case DELETE_VOUCHER -> {

                            consoleOutputManager.printRemoveVoucher();

                            voucherController.removeAllVoucher();
                        }

                        case DELETE_ONE_VOUCHER -> {

                            consoleOutputManager.printRemoveVoucherById();

                            consoleOutputManager.printGetVoucherId();
                            UUID voucherId = consoleInputManager.inputUUID();

                            voucherController.removeVoucherById(voucherId);
                        }

                        case BLACKLIST -> {

                            consoleOutputManager.printBlackList();

                            List<CustomerResponseDto> customerResponseDtos = customerController.readAllBlackList();
                            consoleOutputManager.printCustomerInfo(customerResponseDtos);
                        }

                        case CREATE_WALLET -> walletController.createWallet();

                        case LIST_WALLET_VOUCHER -> walletController.readVouchersByCustomer();

                        case LIST_WALLET_CUSTOMER -> walletController.readCustomersByVoucher();

                        case DELETE_WALLET -> walletController.removeWalletsByCustomer();

                        case EXIT -> {
                            consoleOutputManager.printExit();
                            chooseExit = true;
                        }
                    }
                } catch (RuntimeException e) {
                    logger.error(e.getMessage(), e);
                    consoleOutputManager.printReturnMain(e.getMessage());

                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    consoleOutputManager.printExceptionExit(e.getMessage());

                    break;
                }
            }

            logger.info("Exit Voucher Program.");
        }
    }
}
