package com.prgms.VoucherApp.domain.customer.model.strategy;

import com.prgms.VoucherApp.domain.customer.dto.CustomerCreateRequest;
import com.prgms.VoucherApp.domain.customer.model.CustomerService;
import com.prgms.VoucherApp.domain.customer.model.CustomerStatus;
import com.prgms.VoucherApp.view.Input;
import com.prgms.VoucherApp.view.Output;

public class CustomerCreate implements CustomerCommandStrategy {

    @Override
    public void execute(Input input, Output output, CustomerService customerService) {
        String inputCustomerStatus = input.inputCustomerStatus();
        CustomerStatus inputStatus = CustomerStatus.findByStatus(inputCustomerStatus);

        CustomerCreateRequest customerCreateRequest = new CustomerCreateRequest(inputStatus);

        customerService.save(customerCreateRequest);
    }
}
