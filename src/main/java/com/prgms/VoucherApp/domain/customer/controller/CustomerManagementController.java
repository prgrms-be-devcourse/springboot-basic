package com.prgms.VoucherApp.domain.customer.controller;

import com.prgms.VoucherApp.domain.customer.CustomerCommand;
import com.prgms.VoucherApp.domain.customer.CustomerStatus;
import com.prgms.VoucherApp.domain.customer.dto.CustomerCreateReqDto;
import com.prgms.VoucherApp.domain.customer.dto.CustomerUpdateReqDto;
import com.prgms.VoucherApp.domain.customer.dto.CustomersResDto;
import com.prgms.VoucherApp.domain.customer.model.CustomerDao;
import com.prgms.VoucherApp.view.Input;
import com.prgms.VoucherApp.view.Output;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class CustomerManagementController implements Runnable {

    private final CustomerDao customerDao;
    private final Input input;
    private final Output output;

    public CustomerManagementController(CustomerDao customerDao, Input input, Output output) {
        this.customerDao = customerDao;
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
                customerDao.save(customerCreateReqDto);
                continue;
            }

            if (customerCommand.isFindAll()) {
                CustomersResDto findCustomers = customerDao.findAll();
                output.printCustomers(findCustomers);
                continue;
            }

            if (customerCommand.isFindOne()) {
                String inputUUID = input.inputUUID();
                UUID customerId = UUID.fromString(inputUUID);
                customerDao.findOne(customerId)
                    .ifPresentOrElse(output::printCustomer, output::printFindEmpty);
                continue;
            }

            if (customerCommand.isFindByStatus()) {
                String inputCustomerStatus = input.inputCustomerStatus();
                CustomerStatus customerStatus = CustomerStatus.findByStatus(inputCustomerStatus);
                CustomerCreateReqDto customerCreateReqDto = new CustomerCreateReqDto(customerStatus);
                CustomersResDto findCustomers = customerDao.findByStatus(customerCreateReqDto);
                output.printCustomers(findCustomers);
                continue;
            }

            if (customerCommand.isFindBlackList()) {
                CustomersResDto blackLists = customerDao.readBlackLists();
                output.printBlackLists(blackLists);
                continue;
            }

            if (customerCommand.isUpdate()) {
                String inputUUID = input.inputUUID();
                UUID customerId = UUID.fromString(inputUUID);
                String inputCustomerStatus = input.inputCustomerStatus();
                CustomerStatus customerStatus = CustomerStatus.findByStatus(inputCustomerStatus);
                CustomerUpdateReqDto customerUpdateReqDto = new CustomerUpdateReqDto(customerId, customerStatus);
                customerDao.update(customerUpdateReqDto);
                continue;
            }

            if (customerCommand.isDelete()) {
                String inputUUID = input.inputUUID();
                UUID customerId = UUID.fromString(inputUUID);
                customerDao.deleteById(customerId);
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
