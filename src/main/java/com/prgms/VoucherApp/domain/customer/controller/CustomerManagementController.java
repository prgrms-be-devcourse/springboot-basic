package com.prgms.VoucherApp.domain.customer.controller;

import com.prgms.VoucherApp.domain.customer.dto.CustomerCreateRequest;
import com.prgms.VoucherApp.domain.customer.dto.CustomerUpdateRequest;
import com.prgms.VoucherApp.domain.customer.dto.CustomersResponse;
import com.prgms.VoucherApp.domain.customer.model.CustomerDaoHandler;
import com.prgms.VoucherApp.domain.customer.model.CustomerStatus;
import com.prgms.VoucherApp.view.CustomerCommand;
import com.prgms.VoucherApp.view.Input;
import com.prgms.VoucherApp.view.Output;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class CustomerManagementController implements Runnable {

    private final CustomerDaoHandler customerDaoHandler;
    private final Input input;
    private final Output output;

    public CustomerManagementController(CustomerDaoHandler customerDaoHandler, Input input, Output output) {
        this.customerDaoHandler = customerDaoHandler;
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() {
        boolean isRunning = true;
        while (isRunning) {
            try {
                output.printCustomerCommand();
                Integer inputCustomerNumber = input.inputCustomerCommand();
                CustomerCommand customerCommand = CustomerCommand.findByCustomerTypeNumber(inputCustomerNumber);

                switch (customerCommand) {
                    case CREATE -> {
                        String inputCustomerStatus = input.inputCustomerStatus();
                        CustomerStatus inputStatus = CustomerStatus.findByStatus(inputCustomerStatus);

                        CustomerCreateRequest customerCreateRequest = new CustomerCreateRequest(inputStatus);

                        customerDaoHandler.save(customerCreateRequest);
                    }

                    case FIND_ALL -> {
                        CustomersResponse findCustomers = customerDaoHandler.findAll();
                        output.printCustomers(findCustomers);
                    }

                    case FIND_ONE -> {
                        String inputUUID = input.inputUUID();
                        UUID customerId = UUID.fromString(inputUUID);

                        customerDaoHandler.findOne(customerId)
                            .ifPresentOrElse(output::printCustomer, output::printFindEmpty);
                    }

                    case FIND_BY_STATUS -> {
                        String inputCustomerStatus = input.inputCustomerStatus();
                        CustomerStatus customerStatus = CustomerStatus.findByStatus(inputCustomerStatus);

                        CustomerCreateRequest customerCreateRequest = new CustomerCreateRequest(customerStatus);

                        CustomersResponse findCustomers = customerDaoHandler.findByStatus(customerCreateRequest);

                        output.printCustomers(findCustomers);
                    }

                    case FIND_BLACKLIST -> {
                        CustomersResponse blackLists = customerDaoHandler.readBlackLists();
                        output.printBlackLists(blackLists);
                    }

                    case UPDATE -> {
                        String inputUUID = input.inputUUID();
                        UUID customerId = UUID.fromString(inputUUID);

                        String inputCustomerStatus = input.inputCustomerStatus();
                        CustomerStatus customerStatus = CustomerStatus.findByStatus(inputCustomerStatus);

                        CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest(customerId, customerStatus);

                        customerDaoHandler.update(customerUpdateRequest);
                    }

                    case DELETE -> {
                        String inputUUID = input.inputUUID();
                        UUID customerId = UUID.fromString(inputUUID);

                        customerDaoHandler.deleteById(customerId);
                    }

                    case EXIT -> {
                        isRunning = false;
                    }

                    default -> {
                        output.printNotImplementMsg();
                    }
                }

            } catch (IllegalArgumentException exception) {
                output.printErrorMsg(exception.getMessage());
            }
        }
    }
}
