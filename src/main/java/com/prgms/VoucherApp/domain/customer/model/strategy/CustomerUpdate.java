package com.prgms.VoucherApp.domain.customer.model.strategy;

import com.prgms.VoucherApp.domain.customer.dto.CustomerUpdateRequest;
import com.prgms.VoucherApp.domain.customer.model.CustomerService;
import com.prgms.VoucherApp.domain.customer.model.CustomerStatus;
import com.prgms.VoucherApp.view.Input;
import com.prgms.VoucherApp.view.Output;

import java.util.UUID;

public class CustomerUpdate implements CustomerCommandStrategy {

    @Override
    public void execute(Input input, Output output, CustomerService customerService) {
        String inputUUID = input.inputUUID();
        UUID customerId = UUID.fromString(inputUUID);

        String inputCustomerStatus = input.inputCustomerStatus();
        CustomerStatus customerStatus = CustomerStatus.findByStatus(inputCustomerStatus);

        CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest(customerId, customerStatus);

        customerService.update(customerUpdateRequest);
    }
}
