package com.programmers.vouchermanagement.voucher.controller;

import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

import com.programmers.vouchermanagement.consoleapp.io.ConsoleManager;
import com.programmers.vouchermanagement.voucher.dto.VoucherCustomerRequest;
import com.programmers.vouchermanagement.voucher.dto.CreateVoucherRequest;
import com.programmers.vouchermanagement.voucher.dto.UpdateVoucherRequest;
import com.programmers.vouchermanagement.voucher.dto.VoucherResponse;
import com.programmers.vouchermanagement.voucher.service.VoucherService;

@Controller
public class VoucherController {
    private final VoucherService voucherService;
    private final ConsoleManager consoleManager;

    public VoucherController(VoucherService voucherService, ConsoleManager consoleManager) {
        this.voucherService = voucherService;
        this.consoleManager = consoleManager;
    }

    public void create(CreateVoucherRequest createVoucherRequest) {
        VoucherResponse voucherResponse = voucherService.create(createVoucherRequest);
        consoleManager.printSaveVoucherResult(voucherResponse);
    }

    public void readAllVouchers() {
         List<VoucherResponse> voucherResponses = voucherService.readAllVouchers();
         consoleManager.printReadAllVouchers(voucherResponses);
    }

    public void findById(UUID voucherId) {
        VoucherResponse voucherResponse = voucherService.findById(voucherId);
        consoleManager.printReadVoucher(voucherResponse);
    }

    public void update(UpdateVoucherRequest request) {
        VoucherResponse voucherResponse = voucherService.update(request);
        consoleManager.printSaveVoucherResult(voucherResponse);
    }

    public void deleteById(UUID voucherId) {
        voucherService.deleteById(voucherId);
        consoleManager.printDeleteResult();
    }

    public void grantToCustomer(VoucherCustomerRequest request) {
        voucherService.grantToCustomer(request);
        consoleManager.printGranted(request);
    }

    public void searchOwnedVouchers(UUID customerId) {
        List<VoucherResponse> vouchers = voucherService.findByCustomerId(customerId);
        consoleManager.printOwnedVouchers(customerId, vouchers);
    }

    public void removeVoucherFromUser(VoucherCustomerRequest request) {
        voucherService.releaseFromCustomer(request);
        consoleManager.printDeleteResult();
    }
}
