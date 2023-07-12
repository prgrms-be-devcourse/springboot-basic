package kr.co.programmers.springbootbasic.customer.controller;

import kr.co.programmers.springbootbasic.customer.domain.CustomerStatus;
import kr.co.programmers.springbootbasic.customer.dto.CustomerResponse;
import kr.co.programmers.springbootbasic.customer.service.BlackCustomerService;
import kr.co.programmers.springbootbasic.customer.service.NormalCustomerService;
import kr.co.programmers.springbootbasic.io.Input;
import kr.co.programmers.springbootbasic.io.Output;
import kr.co.programmers.springbootbasic.io.enums.CustomerFindCommand;
import kr.co.programmers.springbootbasic.io.enums.CustomerServiceCommand;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
@Profile("console")
public class CustomerController {
    private final Input inputConsole;
    private final Output outputConsole;
    private final BlackCustomerService blackCustomerService;
    private final NormalCustomerService normalCustomerService;

    public CustomerController(Input inputConsole, Output outputConsole,
                              BlackCustomerService blackCustomerService,
                              NormalCustomerService jdbcCustomerService) {
        this.inputConsole = inputConsole;
        this.outputConsole = outputConsole;
        this.blackCustomerService = blackCustomerService;
        this.normalCustomerService = jdbcCustomerService;
    }

    public void doCustomerService() {
        outputConsole.printCustomerServiceMenu();
        CustomerServiceCommand command = inputConsole.readCustomerServiceCommand();
        switch (command) {
            case CREATE_CUSTOMER -> createCustomer();
            case FIND_CUSTOMER -> findCustomer();
            case UPDATE_CUSTOMER -> updateCustomer();
            case DELETE_CUSTOMER -> deleteById();
        }
    }

    private void createCustomer() {
        outputConsole.printCustomerCreateMessage();
        String customerName = inputConsole.readCustomerName();
        CustomerResponse createdCustomer = normalCustomerService.createCustomer(customerName);
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

    private void findByCustomerId() {
        outputConsole.printVoucherUuidTypeMessage();
        String voucherId = inputConsole.readUUID();
        Optional<CustomerResponse> customerResponse = normalCustomerService.findByVoucherId(voucherId);
        customerResponse.ifPresentOrElse(outputConsole::printCustomerMessage, outputConsole::printNoResult);
    }

    private void findByVoucherId() {
        outputConsole.printCustomerUuidTypeMessage();
        String customerId = inputConsole.readUUID();
        Optional<CustomerResponse> customerResponse = normalCustomerService.findByCustomerId(customerId);
        customerResponse.ifPresentOrElse(outputConsole::printCustomerMessage, outputConsole::printNoResult);
    }

    private void findAllCustomers() {
        List<CustomerResponse> customerResponses = normalCustomerService.findAllCustomer();
        outputConsole.printCustomerListMessage(customerResponses);
    }

    public void findAllBlackCustomer() {
        List<CustomerResponse> customerResponse = blackCustomerService.findAllBlackCustomer();
        outputConsole.printCustomerListMessage(customerResponse);
    }

    public void updateCustomer() {
        outputConsole.printCustomerUuidTypeMessage();
        String customerUUID = inputConsole.readUUID();
        outputConsole.printTypeCustomerStatus();
        CustomerStatus customerStatus = inputConsole.readCustomerStatus();

        CustomerResponse customerResponse = normalCustomerService.updateCustomer(customerUUID, customerStatus);

        outputConsole.printCustomerMessage(customerResponse);
    }

    private void deleteById() {
        outputConsole.printCustomerUuidTypeMessage();
        String customerId = inputConsole.readUUID();

        normalCustomerService.deleteById(customerId);

        outputConsole.printCustomerDeleteMessage(customerId);
    }
}
