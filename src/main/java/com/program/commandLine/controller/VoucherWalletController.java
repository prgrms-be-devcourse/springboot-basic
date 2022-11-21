package com.program.commandLine.controller;

import com.program.commandLine.customer.Customer;
import com.program.commandLine.io.Console;
import com.program.commandLine.service.CustomerService;
import com.program.commandLine.service.VoucherService;
import com.program.commandLine.voucher.Voucher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "voucherWalletController")
public class VoucherWalletController {

    private final VoucherService voucherService;
    private final CustomerService customerService;
    private final Console console;

    public VoucherWalletController(VoucherService voucherService, CustomerService customerService, Console console) {
        this.voucherService = voucherService;
        this.customerService = customerService;
        this.console = console;
    }

    public void assignCustomer() {
        List<Voucher> allVoucher = voucherService.getAssignedVouchersByCustomer(null);
        console.voucherListView(allVoucher);
        String voucherIndex = console.input("할당할 바우처 번호를 입력하세요 : ");
        Voucher selectVoucher =  allVoucher.get(Integer.parseInt(voucherIndex)-1);
        String customerName = console.input("할당할 고객 이름을 입력하세요 : ");

        voucherService.assignCustomer(selectVoucher, customerService.getCustomerByName(customerName).getCustomerId());
        console.successMessageView("바우처가 정상적으로 할당되었습니다.");
    }

    public void lookupAssignedVouchersByCustomer() {
        String customerName = console.input("조회할 고객 이름을 입력하세요 :");
        List<Voucher> vouchers = voucherService.getAssignedVouchersByCustomer(
                customerService.getCustomerByName(customerName).getCustomerId());
        console.voucherListView(vouchers);
    }

    public void retrieveVoucher() {
        String customerName = console.input("고객 이름을 입력하세요 :");
        List<Voucher> vouchers = voucherService.getAssignedVouchersByCustomer(
                customerService.getCustomerByName(customerName).getCustomerId());
        console.voucherListView(vouchers);
        String voucherIndex = console.input("제거할 바우처 번호를 입력하세요 : ");
        Voucher selectVoucher = vouchers.get(Integer.parseInt(voucherIndex)-1);
        voucherService.retrieveVoucher(selectVoucher);
        console.successMessageView("바우처가 정상적으로 회수되었습니다.");

    }

    public void lookupCustomerWithVoucher() {
        List<Voucher> allVoucher = voucherService.getAllVoucher();
        console.voucherListView(allVoucher);
        String voucherIndex = console.input("선택할 바우처 번호를 입력하세요 : ");
        Voucher selectVoucher =  allVoucher.get(Integer.parseInt(voucherIndex)-1);
        if (voucherService.getAssignedCustomer(selectVoucher).isPresent()) {
            Customer customer = customerService.getCustomerById(voucherService.getAssignedCustomer(selectVoucher).get());
            console.customerView(customer);
        } else {
            console.errorMessageView("할당되지 않은 바우처입니다.");
        }

    }

}
