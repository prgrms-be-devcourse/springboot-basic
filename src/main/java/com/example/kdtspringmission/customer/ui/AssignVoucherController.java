package com.example.kdtspringmission.customer.ui;

import com.example.kdtspringmission.Handler;
import com.example.kdtspringmission.ModelAndView;
import com.example.kdtspringmission.customer.application.CustomerService;
import java.util.Scanner;
import java.util.UUID;
import org.springframework.stereotype.Controller;

// TODO: 2021/09/06 voucher를 할당하는 책임은 어디에 있어야할까?
@Controller
public class AssignVoucherController implements Handler{

    private final CustomerService customerService;

    public AssignVoucherController(
        CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public ModelAndView process(Scanner scanner) {
        System.out.print("type customer name: ");
        String customerName = scanner.nextLine();
        System.out.println();

        System.out.print("type voucher id: ");
        UUID voucherId = UUID.fromString(scanner.nextLine());
        System.out.println();

        customerService.assignVoucher(customerName, voucherId);

        return new ModelAndView("Successfully assigned the voucher to the customer");
    }
}
