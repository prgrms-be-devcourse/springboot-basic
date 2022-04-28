package com.blessing333.springbasic.console.customer;

import com.blessing333.springbasic.console.RunnableController;
import com.blessing333.springbasic.console.customer.ui.CustomerCommandOptionType;
import com.blessing333.springbasic.console.customer.ui.CustomerManagingUserInterface;
import com.blessing333.springbasic.console.ui.CommandNotSupportedException;
import com.blessing333.springbasic.domain.customer.converter.CustomerCreateFormConverter;
import com.blessing333.springbasic.domain.customer.model.Customer;
import com.blessing333.springbasic.domain.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Profile("console")
@RequiredArgsConstructor
@Slf4j
public class ConsoleCustomerController implements RunnableController {
    private final CustomerManagingUserInterface userInterface;
    private final CustomerService customerService;
    private final CustomerCreateFormConverter customerCreateFormConverter = new CustomerCreateFormConverter();

    @Override
    public void startService() {
        boolean isExit = false;
        while (!isExit) {
            userInterface.printGuide();
            String command = userInterface.requestMessage();
            try {
                CustomerCommandOptionType type = CustomerCommandOptionType.fromString(command);
                switch (type) {
                    case CREATE -> registerNewCustomer();
                    case INQUIRY -> showCustomerInformation();
                    case LIST -> showAllCustomerInformation();
                    case QUIT -> isExit = true;
                    default -> userInterface.printHelp();
                }
            } catch (CommandNotSupportedException e) {
                log.error(e.getMessage(), e);
                userInterface.printHelp();
            }
        }
    }

    private void registerNewCustomer() {
        try {
            Customer customer = customerCreateFormConverter.convert(userInterface.requestCustomerInformation());
            Customer registeredCustomer = customerService.registerCustomer(customer);
            userInterface.printRegisterComplete(registeredCustomer);
        } catch (IllegalArgumentException | DataAccessException e) {
            log.error(e.getMessage(), e);
            System.out.println(e.getMessage());
        }
    }

    private void showCustomerInformation() {
        try {
            UUID id = UUID.fromString(userInterface.requestCustomerId());
            Customer customer = customerService.inquiryCustomerInformation(id);
            userInterface.printCustomerInformation(customer);

        } catch (IllegalArgumentException | DataAccessException e) {
            log.error(e.getMessage(), e);
            System.out.println("잘못되거나 존재하지 않는 id 입니다");
        }
    }

    private void showAllCustomerInformation() {
        List<Customer> customerList = customerService.loadAllCustomerInformation();
        userInterface.printAllCustomerInformation(customerList);
    }
}
