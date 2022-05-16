package com.programmers.springbootbasic.controller.api;

import com.programmers.springbootbasic.controller.dto.CreateCustomerRequest;
import com.programmers.springbootbasic.controller.api.response.Response;
import com.programmers.springbootbasic.domain.Customer;
import com.programmers.springbootbasic.domain.CustomerVoucherLog;
import com.programmers.springbootbasic.domain.Voucher;
import com.programmers.springbootbasic.service.CustomerService;
import com.programmers.springbootbasic.service.CustomerVoucherLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.programmers.springbootbasic.consolestarter.VoucherManager.validateVoucherId;
import static com.programmers.springbootbasic.controller.api.response.ResponseMessage.getCommonResponseEntity;
import static com.programmers.springbootbasic.controller.api.response.ResponseMessage.getErrorResponseEntity;

@RestController
@RequestMapping(value = "/api/v1/customers")
public class CustomerRestController {

    private final CustomerService customerService;
    private final CustomerVoucherLogService customerVoucherLogService;

    @Autowired
    public CustomerRestController(CustomerService customerService, CustomerVoucherLogService customerVoucherLogService) {
        this.customerService = customerService;
        this.customerVoucherLogService = customerVoucherLogService;
    }

    @PostMapping(path = "/")
    public ResponseEntity<? extends Response> createCustomer(@RequestBody CreateCustomerRequest request) {
        try {
            Customer customer = customerService.createCustomer(request.getCustomerId(), request.getName());
            return getCommonResponseEntity(customer, "새로운 고객의 정보를 성공적으로 저장하였습니다.");
        } catch (IllegalArgumentException e) {
            return getErrorResponseEntity(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    //path variable 수정 ->
    @PostMapping(path = "/{customerId}/{voucherId}")
    public ResponseEntity<? extends Response> allocateVoucherToCustomer(@PathVariable String customerId, @PathVariable String voucherId) {
        try {
            validateVoucherId(voucherId);
            CustomerVoucherLog customerVoucherLog = customerVoucherLogService.createCustomerVoucherLog(customerId, UUID.fromString(voucherId));
            return getCommonResponseEntity(customerVoucherLog, customerId + " 고객에게 성공적으로 할인권을 할당하였습니다.");
        } catch (IllegalArgumentException e) {
            return getErrorResponseEntity(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping(path = "/customers/{id}")
    public ResponseEntity<? extends Response> showCustomer(@PathVariable(value = "id") String customerId) {
        try {
            List<Voucher> vouchersOfCustomer = customerVoucherLogService.getVouchersOfCustomer(customerId);
            if (vouchersOfCustomer.size() > 0)
                return getCommonResponseEntity(vouchersOfCustomer, customerId + "님이 보유한 할인권 정보입니다.");
            else
                return getErrorResponseEntity(HttpStatus.NO_CONTENT, customerId + "님이 보유한 할인권이 존재하지 않습니다.");
        } catch (IllegalArgumentException e) {
            return getErrorResponseEntity(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping(path = "/customers")
    public ResponseEntity<? extends Response> showCustomerList() {
        List<Customer> allCustomers = customerService.getAllCustomers();
        if (allCustomers.size() > 0)
            return getCommonResponseEntity(customerService.getAllCustomers(), "모든 고객 정보를 담은 리스트입니다.");
        else
            return getErrorResponseEntity(HttpStatus.NO_CONTENT, "현재 저장된 고객 정보가 없습니다.");
    }

    @DeleteMapping(path = "/customers/{id}")
    public void deleteCustomer(@PathVariable(value = "id") String customerId) {
        customerService.deleteCustomerById(customerId);
    }

}
