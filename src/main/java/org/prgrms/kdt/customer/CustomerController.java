package org.prgrms.kdt.customer;

import org.prgrms.kdt.io.InputHandler;
import org.prgrms.kdt.io.OutputHandler;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CustomerController {

    private final CustomerService customerService;
    private final OutputHandler outputHandler;
    private final InputHandler inputHandler;

    public CustomerController(CustomerService customerService, OutputHandler outputHandler, InputHandler inputHandler) {
        this.customerService = customerService;
        this.outputHandler = outputHandler;
        this.inputHandler = inputHandler;
    }

    public void getBlackList() {
        List<Customer> blackList = customerService.getBlackList();
        outputHandler.outputBlackList(blackList);
    }

    public void getAllVoucher() {
        List<Voucher> voucherList = customerService.getAllVoucher();
        outputHandler.outputVouchers(voucherList);
    }

    public void removeVoucher() {

    }
}
