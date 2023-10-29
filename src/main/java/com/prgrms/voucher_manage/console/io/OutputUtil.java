package com.prgrms.voucher_manage.console.io;

import com.prgrms.voucher_manage.domain.customer.entity.Customer;
import com.prgrms.voucher_manage.domain.voucher.entity.Voucher;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class OutputUtil {
    public void printMenu() {
        System.out.println("""
                
                === Voucher Program ===
                Type saveVoucher to save voucher.
                Type voucherList to get all voucher.
                Type findVoucher to find voucher by id.
                Type updateVoucher to update voucher.
                
                Type saveCustomer to save customer.
                Type allCustomers to get all customers.
                Type blackCustomers to get black customers.
                Type findCustomer to find customer by name.
                Type updateCustomer to update customer.
                
                Type saveWallet to save wallet.
                Type findWalletVouchers to find vouchers of wallet.
                Type findWalletCustomers to find customers of wallet.
                Type deleteWallet to delete wallet.
                
                Type exit to exit the program.
                """);
    }

    public void printVoucherSelect() {
        System.out.println("""
                === Voucher Program ===
                Type fixed to create fixed amount voucher.
                Type percent to create percent amount voucher.
                """);
    }

    public void printCustomerSelect() {
        System.out.println("""
                === Voucher Program ===
                Type black to create black customer.
                Type normal to create normal customer.
                """);
    }

    public void requestDiscountPercentInfo() {
        System.out.println("\nType discount percent\n");
    }

    public void requestDiscountPriceInfo() {
        System.out.println("\nType discount price\n");
    }

    public void printFixedVoucherInfo(Voucher voucher) {
        System.out.println(MessageFormat.format("\nVoucher id: {0} \nVoucher discount price: {1}\n"
                , voucher.getId(), voucher.getDiscountAmount()));
    }

    public void printPercentVoucherInfo(Voucher voucher) {
        System.out.println(MessageFormat.format("\nVoucher id: {0} \nVoucher discount percent: {1}%\n"
                , voucher.getId(), voucher.getDiscountAmount()));
    }

    public void printCustomerInfo(Customer customer) {
        System.out.println(MessageFormat.format("\nCustomer id: {0} \nCustomer name: {1}\nCustomer type: {2}"
                , customer.getId(), customer.getName(), customer.getType().getLabel()));
    }

    public void printMessage(String message) {
        System.out.println(MessageFormat.format("\n[System] {0}\n", message));
    }
}
