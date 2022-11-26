package com.program.commandLine.controller;

import com.program.commandLine.model.customer.Customer;
import com.program.commandLine.io.Console;
import com.program.commandLine.service.CustomerService;
import com.program.commandLine.service.VoucherService;
import com.program.commandLine.model.voucher.Voucher;
import com.program.commandLine.service.VoucherWalletService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component(value = "voucherWalletController")
public class VoucherWalletController {

    private final VoucherWalletService voucherWalletService;
    private final VoucherService voucherService;
    private final CustomerService customerService;
    private final Console console;

    public VoucherWalletController(VoucherWalletService voucherWalletService, VoucherService voucherService, CustomerService customerService, Console console) {
        this.voucherWalletService = voucherWalletService;
        this.voucherService = voucherService;
        this.customerService = customerService;
        this.console = console;
    }

    public void assignCustomer() {
        List<Voucher> notAssignedVoucher = voucherWalletService.getNotAssignedVoucher();
        console.voucherListView(notAssignedVoucher);
        if(notAssignedVoucher.isEmpty()) return;
        String voucherIndex = console.input("할당할 바우처 번호를 입력하세요 : ");
        Voucher selectVoucher =  notAssignedVoucher.get(Integer.parseInt(voucherIndex)-1);

        String customerName = console.input("할당할 고객 이름을 입력하세요 : ");

        voucherWalletService.assignCustomer(selectVoucher, customerService.getCustomerByName(customerName));
        console.successMessageView("바우처가 정상적으로 할당되었습니다.");
    }

    public void lookupAssignedVouchersByCustomer() {
        String customerName = console.input("조회할 고객 이름을 입력하세요 :");
        Customer selectCustomer = customerService.getCustomerByName(customerName);

        List<Voucher> vouchers = voucherService.getVouchersByWallet(selectCustomer.getVoucherWallets());
        console.voucherListView(vouchers);
    }

    public void retrieveVoucher() {
        String customerName = console.input("고객 이름을 입력하세요 :");
        Customer selectCustomer = customerService.getCustomerByName(customerName);

        List<Voucher> vouchers =  voucherService.getVouchersByWallet(selectCustomer.getVoucherWallets());
        console.voucherListView(vouchers);
        if(vouchers.isEmpty()) return;

        String voucherIndex = console.input("제거할 바우처 번호를 입력하세요 : ");
        Voucher selectVoucher = vouchers.get(Integer.parseInt(voucherIndex)-1);

        voucherWalletService.retrieveVoucher(selectVoucher);
        console.successMessageView("바우처가 정상적으로 회수되었습니다.");
    }

    public void lookupCustomerWithVoucher() {
        List<Voucher> allVoucher = voucherService.getAllVoucher();
        console.voucherListView(allVoucher);
        if(allVoucher.isEmpty()) return;

        String voucherIndex = console.input("검색할 바우처 번호를 입력하세요 : ");
        Voucher selectVoucher =  allVoucher.get(Integer.parseInt(voucherIndex)-1);

        UUID customerId = voucherWalletService.getAssignedCustomer(selectVoucher);
        Customer customer = customerService.getCustomerById(customerId);

        console.customerView(customer);
    }

}
