package com.example.kdtspringmission.customer.ui;

import com.example.kdtspringmission.Handler;
import com.example.kdtspringmission.ModelAndView;
import com.example.kdtspringmission.customer.domain.Customer;
import com.example.kdtspringmission.customer.application.CustomerService;
import com.example.kdtspringmission.voucher.application.VoucherService;
import java.util.Scanner;
import java.util.UUID;
import org.springframework.stereotype.Controller;

@Controller
public class FindOwnerController implements Handler {

    private final CustomerService customerService;
    private final VoucherService voucherService;

    public FindOwnerController(
        CustomerService customerService,
        VoucherService voucherService) {
        this.customerService = customerService;
        this.voucherService = voucherService;
    }

    @Override
    public ModelAndView process(Scanner scanner) {

        System.out.print("type voucher id: ");
        UUID voucherId = UUID.fromString(scanner.nextLine());
        System.out.println();

        Customer owner = customerService.getCustomerOwn(voucherService.getVoucher(voucherId));

        ModelAndView mv = new ModelAndView("Owner");
        mv.getModel().put("owner", owner);

        return mv;
    }
}
