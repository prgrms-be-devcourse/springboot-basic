package com.example.kdtspringmission.customer.ui;

import com.example.kdtspringmission.Handler;
import com.example.kdtspringmission.ModelAndView;
import com.example.kdtspringmission.customer.domain.Customer;
import com.example.kdtspringmission.customer.domain.CustomerRepository;
import java.util.List;
import java.util.Scanner;
import org.springframework.stereotype.Controller;

@Controller
public class ListCustomerController implements Handler {

    private final CustomerRepository customerRepository;

    public ListCustomerController(
        CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Override
    public ModelAndView process(Scanner scanner) {
        ModelAndView mv = new ModelAndView("Customer List");
        List<Customer> customers = customerRepository.findAll();
        mv.getModel().put("customers", customers);
        return mv;
    }
}
