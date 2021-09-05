package com.example.kdtspringmission.customer.ui;

import com.example.kdtspringmission.Handler;
import com.example.kdtspringmission.ModelAndView;
import com.example.kdtspringmission.customer.application.CustomerService;
import com.example.kdtspringmission.voucher.domain.Voucher;
import java.util.List;
import java.util.Scanner;
import org.springframework.stereotype.Controller;

@Controller
public class ListOwnerController implements Handler {

    private final CustomerService customerService;

    public ListOwnerController(
        CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public ModelAndView process(Scanner scanner) {

        System.out.print("type customer name: ");
        String customerName = scanner.nextLine();
        System.out.println();

        List<Voucher> wallet = customerService.getWallet(customerName);

        ModelAndView mv = new ModelAndView("Voucher list");
        mv.getModel().put("wallet", wallet);

        return mv;
    }
}
