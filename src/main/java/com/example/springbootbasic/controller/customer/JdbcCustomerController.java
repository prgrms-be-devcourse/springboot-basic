package com.example.springbootbasic.controller.customer;

import com.example.springbootbasic.controller.request.RequestBody;
import com.example.springbootbasic.controller.response.ResponseBody;
import com.example.springbootbasic.domain.customer.Customer;
import com.example.springbootbasic.domain.voucher.Voucher;
import com.example.springbootbasic.domain.voucher.VoucherFactory;
import com.example.springbootbasic.dto.customer.CustomerDto;
import com.example.springbootbasic.dto.voucher.VoucherDto;
import com.example.springbootbasic.service.customer.JdbcCustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Controller
public class JdbcCustomerController {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerController.class);
    private final JdbcCustomerService customerService;

    public JdbcCustomerController(JdbcCustomerService customerService) {
        this.customerService = customerService;
    }

    public ResponseBody<List<CustomerDto>> findAllCustomers() {
        List<Customer> findAllCustomers;
        try {
            findAllCustomers = customerService.findAllCustomers();
        } catch (EmptyResultDataAccessException e) {
            logger.error("Fail - {}", e.getMessage());
            return ResponseBody.fail(Collections.emptyList());
        }
        return ResponseBody.success(findAllCustomers.stream()
                .map(CustomerDto::newInstance)
                .toList());
    }

    public ResponseBody<CustomerDto> customerSaveVoucher(RequestBody<CustomerDto> request) {
        CustomerDto customerDto = request.getData();
        Customer customer = new Customer(customerDto.getCustomerId(), customerDto.getStatus());
        List<Voucher> vouchers = customerDto.getVouchers()
                .stream()
                .map(voucherDto -> VoucherFactory.of(voucherDto.getVoucherId(), voucherDto.getDiscountValue(), voucherDto.getVoucherType(),
                        LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now().plusDays(30)))
                .toList();
        try {
            vouchers.forEach(voucher -> customerService.saveVoucher(customer, voucher));
        } catch (DataAccessException e) {
            logger.error("Fail - {}", e.getMessage());
            return ResponseBody.fail(customerDto);
        }
        return ResponseBody.success(customerDto);
    }

    public ResponseBody<List<VoucherDto>> findVouchersByCustomer(RequestBody<CustomerDto> request) {
        CustomerDto customerDto = request.getData();
        Long customerId = customerDto.getCustomerId();
        List<Voucher> findVouchers;
        try {
            findVouchers = customerService.findVouchersByCustomerId(customerId);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Fail - {}", e.getMessage());
            return ResponseBody.fail(Collections.emptyList());
        }
        return ResponseBody.success(findVouchers.stream()
                .map(VoucherDto::newInstance)
                .toList());
    }

    public ResponseBody<CustomerDto> deleteAllVoucherByCustomerOwn(RequestBody<CustomerDto> request) {
        CustomerDto customerDto = request.getData();
        Long customerId = customerDto.getCustomerId();
        try {
            customerService.deleteAllVouchersByCustomerId(customerId);
        } catch (DataAccessException e) {
            logger.error("Fail - {}", e.getMessage());
            return ResponseBody.fail(request.getData());
        }
        return ResponseBody.success(request.getData());
    }

    public ResponseBody<List<CustomerDto>> findCustomersWhoOwnVoucher(RequestBody<VoucherDto> request) {
        VoucherDto voucherDto = request.getData();
        List<Customer> findCustomers;
        try {
            findCustomers = customerService.findCustomersWhoHasSelectedVoucher(voucherDto.getVoucherId());
        } catch (EmptyResultDataAccessException e) {
            logger.error("Fail - {}", e.getMessage());
            return ResponseBody.fail(Collections.emptyList());
        }
        return ResponseBody.success(findCustomers.stream()
                .map(CustomerDto::newInstance)
                .toList());
    }
}
