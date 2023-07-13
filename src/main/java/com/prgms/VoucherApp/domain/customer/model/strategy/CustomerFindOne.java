package com.prgms.VoucherApp.domain.customer.model.strategy;

import com.prgms.VoucherApp.domain.customer.model.CustomerService;
import com.prgms.VoucherApp.view.Input;
import com.prgms.VoucherApp.view.Output;

import java.util.UUID;

public class CustomerFindOne implements CustomerCommandStrategy {

    @Override
    public void execute(Input input, Output output, CustomerService customerService) {
        String inputUUID = input.inputUUID();
        UUID customerId = UUID.fromString(inputUUID);

        customerService.findOne(customerId)
                .ifPresentOrElse(output::printCustomer, output::printFindEmpty);
    }
}
