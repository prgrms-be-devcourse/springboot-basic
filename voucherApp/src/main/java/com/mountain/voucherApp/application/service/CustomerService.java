package com.mountain.voucherApp.application.service;

import com.mountain.voucherApp.application.port.in.CustomerDto;
import com.mountain.voucherApp.application.port.out.CustomerPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerPort customerPort;

    public CustomerService(CustomerPort customerPort) {
        this.customerPort = customerPort;
    }

    public List<CustomerDto> findAll() {
        return customerPort.findAll();
    }
}
