package org.prgrms.kdt.domain.command.componet;

import org.prgrms.kdt.domain.command.types.CommandType;
import org.prgrms.kdt.util.Output;
import org.prgrms.kdt.domain.customer.model.Customer;
import org.prgrms.kdt.domain.customer.model.CustomerType;
import org.prgrms.kdt.domain.customer.service.CustomerService;
import org.prgrms.kdt.domain.voucher.model.VoucherType;
import org.prgrms.kdt.util.Input;
import org.prgrms.kdt.domain.voucher.model.Voucher;
import org.prgrms.kdt.domain.voucher.service.VoucherService;
import org.prgrms.kdt.domain.wallet.service.WalletService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
public class CommandComponent {
    private final VoucherService voucherService;
    private final CustomerService customerService;
    private final WalletService walletService;

    public CommandComponent(VoucherService voucherService, CustomerService customerService, WalletService walletService) {
        this.voucherService = voucherService;
        this.customerService = customerService;
        this.walletService = walletService;
    }

    public void processCommand(CommandType voucherCommandType){
        switch (voucherCommandType) {
            case CREATE_CUSTOMER -> createCustomer();
            case LIST_CUSTOMER -> getAllCustomers();
            case LIST_CUSTOMER_HAS_VOUCHER ->  getCustomersByVoucherTypeAndDate();
            case BLACKLIST_CUSTOMER -> getBlackList();
            case CREATE_VOUCHER -> createVoucher();
            case LIST_VOUCHER -> getAllVouchers();
            case LIST_VOUCHER_HAS_CUSTOMER -> getVoucherByCustomer();
            case REMOVE_VOUCHER -> removeVoucher();
            case ASSIGN_VOUCHER -> assignVoucherToCustomer();
            case EXIT -> Output.printExit();
        }
    }

    private void getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        Output.printAllCustomers(customers);
    }

    private void getBlackList() {
        List<Customer> blackCustomers = customerService.getBlackCustomers();
        Output.printAllCustomers(blackCustomers);
    }

    private void createCustomer() {
        Output.printEnterCustomerType();
        CustomerType customerType = Input.inputCustomerType();
        Output.printEnterName();
        String name = Input.inputString();
        Output.printEnterEmail();
        String email = Input.inputString();
        customerService.createCustomer(name, email, customerType);
    }

    private void assignVoucherToCustomer() {
        Output.printEnterVoucherId();
        UUID voucherId = Input.inputUuid();
        Output.printEnterCustomerId();
        UUID customerId = Input.inputUuid();
        walletService.updateVoucherCustomerId(voucherId, customerId);
    }

    private void removeVoucher() {
        Output.printEnterVoucherId();
        UUID voucherId = Input.inputUuid();
        walletService.removeVoucher(voucherId);
    }

    private void getCustomersByVoucherTypeAndDate() {
        Output.printEnterVoucherType();
        VoucherType voucherType = Input.inputVoucherType();
        Output.printEnterCreatedDate();
        LocalDate date = Input.inputDate();
        List<Customer> customers = walletService.getCustomerByTypeAndDate(voucherType, date);
        Output.printAllCustomers(customers);
    }

    private void getVoucherByCustomer() {
        Output.printEnterCustomerId();
        UUID customerId = Input.inputUuid();
        List<Voucher> vouchers = walletService.getVouchersByCustomerId(customerId);
        Output.printAllVouchers(vouchers);
    }

    private void createVoucher() {
        Output.printEnterVoucherType();
        VoucherType voucherType = Input.inputVoucherType();
        Output.printEnterDiscountValue();
        long discount = Input.inputDiscountValue();
        voucherService.saveVoucher(voucherType, discount);
    }

    private void getAllVouchers() {
        List<Voucher> vouchers = voucherService.getAllVouchers();
        Output.printAllVouchers(vouchers);
    }
}
