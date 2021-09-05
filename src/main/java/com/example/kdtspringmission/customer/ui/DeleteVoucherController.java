package com.example.kdtspringmission.customer.ui;

import com.example.kdtspringmission.Handler;
import com.example.kdtspringmission.ModelAndView;
import com.example.kdtspringmission.customer.application.CustomerService;
import java.util.Scanner;
import java.util.UUID;
import org.springframework.stereotype.Controller;

@Controller
public class DeleteVoucherController implements Handler {

    private final CustomerService customerService;

    public DeleteVoucherController(
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

        customerService.deleteVoucher(customerName, voucherId);

        return new ModelAndView("Successfully delete the voucher from the customer");
    }
}
