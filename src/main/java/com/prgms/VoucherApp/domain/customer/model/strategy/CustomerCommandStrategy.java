package com.prgms.VoucherApp.domain.customer.model.strategy;

import com.prgms.VoucherApp.domain.customer.model.CustomerService;
import com.prgms.VoucherApp.view.Input;
import com.prgms.VoucherApp.view.Output;

public interface CustomerCommandStrategy {

    void execute(Input input, Output output, CustomerService customerService);
}
