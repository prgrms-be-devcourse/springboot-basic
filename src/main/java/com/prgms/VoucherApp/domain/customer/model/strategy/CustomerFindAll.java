package com.prgms.VoucherApp.domain.customer.model.strategy;

import com.prgms.VoucherApp.domain.customer.dto.CustomersResponse;
import com.prgms.VoucherApp.domain.customer.model.CustomerService;
import com.prgms.VoucherApp.view.Input;
import com.prgms.VoucherApp.view.Output;

public class CustomerFindAll implements CustomerCommandStrategy {

    @Override
    public void execute(Input input, Output output, CustomerService customerService) {
        CustomersResponse findCustomers = customerService.findAll();
        output.printCustomers(findCustomers);
    }
}
