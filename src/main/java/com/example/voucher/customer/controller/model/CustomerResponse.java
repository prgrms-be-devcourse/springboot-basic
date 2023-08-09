package com.example.voucher.customer.controller.model;

import java.util.Arrays;
import java.util.List;

import com.example.voucher.constant.ResponseStatus;
import com.example.voucher.customer.service.dto.CustomerDTO;

public class CustomerResponse {

    private ResponseStatus status;
    private String errorMsg;
    private List<CustomerDTO> customers;

    public CustomerResponse(ResponseStatus status) {
        this.status = status;
    }

    public CustomerResponse(ResponseStatus status, List<CustomerDTO> customers) {
        this.status = status;
        this.customers = customers;
    }

    public CustomerResponse(ResponseStatus status, CustomerDTO customers) {
        this.status = status;
        this.customers = Arrays.asList(customers);
    }

    public CustomerResponse(ResponseStatus status, String errorMsg) {
        this.status = status;
        this.errorMsg = errorMsg;
    }

    public List<CustomerDTO> getCustomers() {
        return customers;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
