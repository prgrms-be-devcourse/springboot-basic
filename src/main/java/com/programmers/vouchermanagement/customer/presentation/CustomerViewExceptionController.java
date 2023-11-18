package com.programmers.vouchermanagement.customer.presentation;

import com.programmers.vouchermanagement.customer.exception.CustomerNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomerViewExceptionController {

    @ExceptionHandler(CustomerNotFoundException.class)
    public String catchCustomerNotFoundException(CustomerNotFoundException e, Model model) {

        model.addAttribute("errorMessage", e.getMessage());

        return "exception-detail";
    }
}
