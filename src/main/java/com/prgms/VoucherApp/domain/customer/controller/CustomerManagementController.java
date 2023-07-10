package com.prgms.VoucherApp.domain.customer.controller;

import com.prgms.VoucherApp.domain.customer.dto.CustomerCreateReqDto;
import com.prgms.VoucherApp.domain.customer.dto.CustomerUpdateReqDto;
import com.prgms.VoucherApp.domain.customer.dto.CustomersResDto;
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
            output.printCustomerCommand();
            Integer inputCustomerNumber = input.inputCustomerCommand();
            CustomerCommand customerCommand = CustomerCommand.findByCustomerTypeNumber(inputCustomerNumber);

            switch (customerCommand) {
                case CREATE -> {
                    String inputCustomerStatus = input.inputCustomerStatus();
                    CustomerStatus inputStatus = CustomerStatus.findByStatus(inputCustomerStatus);

                    CustomerCreateReqDto customerCreateReqDto = new CustomerCreateReqDto(inputStatus);

                    customerDaoHandler.save(customerCreateReqDto);
                }

                case FIND_ALL -> {
                    CustomersResDto findCustomers = customerDaoHandler.findAll();
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

                    CustomerCreateReqDto customerCreateReqDto = new CustomerCreateReqDto(customerStatus);

                    CustomersResDto findCustomers = customerDaoHandler.findByStatus(customerCreateReqDto);

                    output.printCustomers(findCustomers);
                }

                case FIND_BLACKLIST -> {
                    CustomersResDto blackLists = customerDaoHandler.readBlackLists();
                    output.printBlackLists(blackLists);
                }

                case UPDATE -> {
                    String inputUUID = input.inputUUID();
                    UUID customerId = UUID.fromString(inputUUID);

                    String inputCustomerStatus = input.inputCustomerStatus();
                    CustomerStatus customerStatus = CustomerStatus.findByStatus(inputCustomerStatus);

                    CustomerUpdateReqDto customerUpdateReqDto = new CustomerUpdateReqDto(customerId, customerStatus);

                    customerDaoHandler.update(customerUpdateReqDto);
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
        }
    }
}
