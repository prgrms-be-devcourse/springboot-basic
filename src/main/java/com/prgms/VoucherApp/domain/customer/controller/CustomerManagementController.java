package com.prgms.VoucherApp.domain.customer.controller;

import com.prgms.VoucherApp.domain.customer.dto.CustomerDto;
import com.prgms.VoucherApp.domain.customer.model.BlackListReader;
import com.prgms.VoucherApp.view.Output;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CustomerManagementController {

    private final BlackListReader blackListReader;
    private final Output output;

    public CustomerManagementController(BlackListReader blackListReader, Output output) {
        this.blackListReader = blackListReader;
        this.output = output;
    }


    public void readBlackList() {
        try {
            List<CustomerDto> customerDtos = blackListReader.readBlackLists();
            output.printBlackLists(customerDtos);
        } catch (RuntimeException e) {
            output.printErrorMsg(e.getMessage());
        }
    }
}
