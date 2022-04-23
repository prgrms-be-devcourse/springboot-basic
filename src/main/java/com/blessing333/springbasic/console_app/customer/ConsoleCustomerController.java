package com.blessing333.springbasic.console_app.customer;

import com.blessing333.springbasic.common.util.ExceptionStackTraceConverter;
import com.blessing333.springbasic.console_app.RunnableController;
import com.blessing333.springbasic.console_app.customer.ui.CustomerCommandOptionType;
import com.blessing333.springbasic.console_app.customer.ui.CustomerManagingUserInterface;
import com.blessing333.springbasic.console_app.ui.CommandNotSupportedException;
import com.blessing333.springbasic.customer.converter.CustomerCreateFormConverter;
import com.blessing333.springbasic.customer.domain.Customer;
import com.blessing333.springbasic.customer.service.CustomerService;
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
        while(!isExit){
            userInterface.showGuideText();
            String command = userInterface.inputMessage();
            try {
                CustomerCommandOptionType type = CustomerCommandOptionType.fromString(command);
                switch (type) {
                    case CREATE -> registerNewCustomer();
                    case INQUIRY -> showCustomerInformation();
                    case LIST -> showAllCustomerInformation();
                    case QUIT -> isExit = true;
                    default -> userInterface.showHelpText();
                }
            } catch (CommandNotSupportedException e) {
                log.error(ExceptionStackTraceConverter.convertToString(e));
                userInterface.showHelpText();
            }
        }
    }

    private void registerNewCustomer(){
        try {
            Customer customer = customerCreateFormConverter.convert(userInterface.requestCustomerInformation()); //IllegalArgumentException 발생 가능
            Customer registeredCustomer = customerService.registerCustomer(customer);  // DataAccessException 발생 가능
            userInterface.printCustomerCreateSuccessMessage(registeredCustomer);
        } catch (IllegalArgumentException | DataAccessException e){
            log.error(ExceptionStackTraceConverter.convertToString(e));
            System.out.println(e.getMessage());
        }
    }

    private void showCustomerInformation(){
        try {
            UUID id = UUID.fromString(userInterface.requestCustomerId());
            Customer customer = customerService.inquiryCustomerInformation(id);
            userInterface.printCustomerInformation(customer);

        }catch (IllegalArgumentException | DataAccessException e){
            log.error(ExceptionStackTraceConverter.convertToString(e));
            System.out.println("잘못되거나 존재하지 않는 id 입니다");
        }
    }

    private void showAllCustomerInformation(){
        List<Customer> customerList = customerService.loadAllCustomerInformation();
        userInterface.printAllCustomerInformation(customerList);
    }
}
