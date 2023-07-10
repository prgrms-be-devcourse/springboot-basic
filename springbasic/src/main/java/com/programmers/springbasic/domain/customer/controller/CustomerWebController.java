package com.programmers.springbasic.domain.customer.controller;

import com.programmers.springbasic.domain.customer.dto.request.CustomerDeleteRequestDTO;
import com.programmers.springbasic.domain.customer.dto.request.CustomerSingleFindRequestDTO;
import com.programmers.springbasic.domain.customer.dto.request.CustomerUpdateRequestDTO;
import com.programmers.springbasic.domain.customer.dto.response.CustomerResponseDTO;
import com.programmers.springbasic.domain.customer.dto.request.CustomerCreateRequestDTO;
import com.programmers.springbasic.domain.customer.service.CustomerService;
import com.programmers.springbasic.domain.customer.validator.CustomerCreateRequestValidator;
import com.programmers.springbasic.domain.customer.validator.CustomerIdValidator;
import com.programmers.springbasic.domain.customer.validator.CustomerUpdateRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Profile("web")
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class CustomerWebController {
    private final CustomerService customerService;

    @GetMapping("/new")
    public String createForm() {
        return "customers/createCustomerForm";
    }

    @PostMapping("/new")
    public String create(CustomerCreateRequestDTO customerCreateRequestDTO) {
        CustomerCreateRequestValidator.validateCreateCustomerRequest(customerCreateRequestDTO);
        customerService.createCustomer(customerCreateRequestDTO);

        return "redirect:/";    // 고객 추가가 끝나고 home 화면으로 보냄.
    }
}
