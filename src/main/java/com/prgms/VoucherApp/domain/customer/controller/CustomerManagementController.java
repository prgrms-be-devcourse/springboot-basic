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
            CustomerCommand customerCommand = CustomerCommand.findByCustomerTypeNumber(input.inputCustomerCommand());

            if (customerCommand.isCreate()) {
                CustomerStatus inputStatus = CustomerStatus.findByStatus(input.inputCustomerStatus());
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
                UUID inputUUID = UUID.fromString(input.inputUUID());
                customerDao.findOne(inputUUID)
                    .ifPresentOrElse(output::printCustomer, output::printFindEmpty);

                continue;
            }

            if (customerCommand.isFindByStatus()) {
                CustomerStatus inputStatus = CustomerStatus.findByStatus(input.inputCustomerStatus());
                CustomerCreateReqDto customerCreateReqDto = new CustomerCreateReqDto(inputStatus);
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
                UUID inputUUID = UUID.fromString(input.inputUUID());
                CustomerStatus inputStatus = CustomerStatus.findByStatus(input.inputCustomerStatus());
                CustomerUpdateReqDto customerUpdateReqDto = new CustomerUpdateReqDto(inputUUID, inputStatus);
                customerDao.update(customerUpdateReqDto);

                continue;
            }

            if (customerCommand.isDelete()) {
                UUID inputUUID = UUID.fromString(input.inputUUID());
                customerDao.deleteById(inputUUID);
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
