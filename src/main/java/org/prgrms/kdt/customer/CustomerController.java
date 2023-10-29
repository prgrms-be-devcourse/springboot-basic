package org.prgrms.kdt.customer;

import org.prgrms.kdt.io.InputHandler;
import org.prgrms.kdt.io.OutputHandler;
import org.prgrms.kdt.wallet.Wallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.prgrms.kdt.customer.CustomerMessage.*;
import static org.prgrms.kdt.io.SystemMessage.EXCEPTION_NOT_EXIST_MENU;

@Controller
public class CustomerController {

    private final CustomerService customerService;
    private final OutputHandler outputHandler;
    private final InputHandler inputHandler;

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    private static final String lineSeparator = System.lineSeparator();

    private final String CREATE = "create";
    private final String VOUCHERS = "vouchers";
    private final String BLACK = "black";

    public CustomerController(CustomerService customerService, OutputHandler outputHandler, InputHandler inputHandler) {
        this.customerService = customerService;
        this.outputHandler = outputHandler;
        this.inputHandler = inputHandler;
    }

    public void customerMenu() throws IOException {
        String menu = selectCustomerMenu();

        switch (menu) {
            case CREATE:
                createCustomer();
                break;
            case VOUCHERS:
                getHaveVouchers();
                break;
            case BLACK:
                getBlackList();
                break;
            default:
                String errorMessage = EXCEPTION_NOT_EXIST_MENU.getMessage();
                logger.error(errorMessage);
                outputHandler.outputString(errorMessage);
                break;
        }
    }

    private String selectCustomerMenu() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Customer Program ===");
        sb.append(lineSeparator);
        sb.append("[1] Type 'create' to create a new customer.");
        sb.append(lineSeparator);
        sb.append("[2] Type 'vouchers' to view vouchers held by a specific customer.");
        sb.append(lineSeparator);
        sb.append("[3] Type 'black' to view the blacklisted list.");
        sb.append(lineSeparator);

        outputHandler.outputString(sb.toString());

        return inputHandler.inputString();
    }

    private void createCustomer() throws IOException {
        outputHandler.outputString(CREATE_CUSTOMER_NAME.getMessage());
        String name = inputHandler.inputString();
        outputHandler.outputString(CREATE_CUSTOMER_EMAIL.getMessage());
        String email = inputHandler.inputString();

        Customer newCustomer = new Customer(UUID.randomUUID(), name, email, LocalDateTime.now());
        customerService.createCustomer(newCustomer);
    }

    private void getHaveVouchers() throws IOException {
        outputHandler.outputString(GET_HAVE_VOUCHERS.getMessage());
        UUID customerId = UUID.fromString(inputHandler.inputString());
        List<Wallet> walletList = customerService.getHaveVouchers(customerId);
        outputHandler.outputWallets(walletList);
    }

    private void getBlackList() {
        List<Customer> blackList = customerService.getBlackList();
        outputHandler.outputBlackList(blackList);
    }
}
