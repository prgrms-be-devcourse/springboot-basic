package com.prgms.VoucherApp.domain.customer.controller;

import com.prgms.VoucherApp.domain.customer.dto.CustomerDto;
import com.prgms.VoucherApp.domain.customer.model.CustomerDao;
import com.prgms.VoucherApp.view.Output;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CustomerManagementApp {

    private final CustomerDao customerDao;
    private final Output output;

    public CustomerManagementApp(CustomerDao customerDao, Output output) {
        this.customerDao = customerDao;
        this.output = output;
    }


    public void readBlackList() {
        List<CustomerDto> customerDtos = customerDao.readBlackLists();
        output.printBlackLists(customerDtos);
    }
}
