package com.example.springbootbasic.console;

import com.example.springbootbasic.controller.customer.CustomerController;
import com.example.springbootbasic.controller.request.RequestBody;
import com.example.springbootbasic.controller.response.ResponseBody;
import com.example.springbootbasic.controller.voucher.VoucherController;
import com.example.springbootbasic.dto.customer.CustomerDto;
import com.example.springbootbasic.dto.voucher.VoucherDto;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ConsoleManager {

    private final VoucherController voucherController;
    private final CustomerController customerController;

    public ConsoleManager(VoucherController voucherController, CustomerController customerController) {
        this.voucherController = voucherController;
        this.customerController = customerController;
    }

    public ResponseBody<VoucherDto> saveVoucher(RequestBody<VoucherDto> request) {
        return voucherController.saveVoucher(request);
    }

    public ResponseBody<List<VoucherDto>> selectAllVouchers() {
        return voucherController.selectAllVouchers();
    }

    public ResponseBody<List<CustomerDto>> selectAllBlackCustomers() {
        return customerController.findAllCustomers();
    }
}
