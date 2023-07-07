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

            if (customerCommand.isCreate()) {
                String inputCustomerStatus = input.inputCustomerStatus();
                CustomerStatus inputStatus = CustomerStatus.findByStatus(inputCustomerStatus);
                CustomerCreateReqDto customerCreateReqDto = new CustomerCreateReqDto(inputStatus);
                customerDaoHandler.save(customerCreateReqDto);
                continue;
            }

            if (customerCommand.isFindAll()) {
                CustomersResDto findCustomers = customerDaoHandler.findAll();
                output.printCustomers(findCustomers);
                continue;
            }

            if (customerCommand.isFindOne()) {
                String inputUUID = input.inputUUID();
                UUID customerId = UUID.fromString(inputUUID);
                customerDaoHandler.findOne(customerId)
                    .ifPresentOrElse(output::printCustomer, output::printFindEmpty);
                continue;
            }

            if (customerCommand.isFindByStatus()) {
                String inputCustomerStatus = input.inputCustomerStatus();
                CustomerStatus customerStatus = CustomerStatus.findByStatus(inputCustomerStatus);
                CustomerCreateReqDto customerCreateReqDto = new CustomerCreateReqDto(customerStatus);
                CustomersResDto findCustomers = customerDaoHandler.findByStatus(customerCreateReqDto);
                output.printCustomers(findCustomers);
                continue;
            }

            if (customerCommand.isFindBlackList()) {
                CustomersResDto blackLists = customerDaoHandler.readBlackLists();
                output.printBlackLists(blackLists);
                continue;
            }

            if (customerCommand.isUpdate()) {
                String inputUUID = input.inputUUID();
                UUID customerId = UUID.fromString(inputUUID);
                String inputCustomerStatus = input.inputCustomerStatus();
                CustomerStatus customerStatus = CustomerStatus.findByStatus(inputCustomerStatus);
                CustomerUpdateReqDto customerUpdateReqDto = new CustomerUpdateReqDto(customerId, customerStatus);
                customerDaoHandler.update(customerUpdateReqDto);
                continue;
            }

            if (customerCommand.isDelete()) {
                String inputUUID = input.inputUUID();
                UUID customerId = UUID.fromString(inputUUID);
                customerDaoHandler.deleteById(customerId);
                continue;
            }


            if (customerCommand.isExit()) {
                isRunning = false;
                continue;
            }

            output.printNotImplementMsg();
        }
    }
}
