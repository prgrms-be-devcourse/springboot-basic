package org.prgrms.vouchermanagement.view;

import org.prgrms.vouchermanagement.customer.domain.Customer;
import org.prgrms.vouchermanagement.voucher.domain.Voucher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConsoleOutput {

    private static final String welcome = """
                            
            === Voucher Program ===
            Type voucher to view voucher option.
            Type customer to view customer option.
            Type wallet to view wallet option.
            Type exit to exit the program.
            """;

    private static final String customerOption = """
            
            Type list to list all customers.
            Type blacklist to list all black customers.
            Type exit to exit customer option
            """;

    private static final String voucherOption = """
            
            Type create to create new voucher.
            Type list to list all vouchers.
            Type update to update voucher by voucherId.
            Type delete to delete voucher by voucherId.
            Type exit to exit customer option
            """;

    private static final String walletOption = """
            
            Type create to create new wallet.
            Type list to list all wallet.
            Type delete to delete wallet by customerId.
            Type find to find customer by voucherId.
            Type exit to exit customer option
            """;

    private static final String createVoucher = """
                            
            === CREATE VOUCHER ===
            select policy(fixed, percent)
            input amount or percent
            """;

    private static final String createVoucherComplete = """
                            
            === COMPLETE CREATE VOUCHER ===
            """;

    private static final String showVoucherLists = """
                            
            === VOUCHER LISTS ===
            """;

    private static final String showCustomerList = """
                            
            === CUSTOMER LISTS ===
            """;

    private static final String showBlackList = """
                            
            === CUSTOMER BLACKLIST ===
            """;

    private static final String updateVoucher = """
                            
            === VOUCHER UPDATE ===
            update amount or percent by voucherId
            
            input voucherId(UUID)
            input amount or percent
            """;

    private static final String updateVoucherComplete = """
                            
            === COMPLETE UPDATE VOUCHER ===
            """;

    private static final String deleteVoucher = """
                            
            === VOUCHER DELETE ===
            """;

    private static final String wallet = """
                            
            === WALLET PAGE ===
            Type create to create a new wallet
            Type list to find all vouchers by customerId
            Type delete to delete voucher by customerId
            Type find to find customer by voucherId
            Type exit to exit the program.
            """;

    private static final String createWallet = """
            
            == CREATE WALLET ==
            
            input customerId(UUID)
            input voucherId(UUID)
            """;

    private static final String findVoucherWallet = """
            
            == FIND ALL VOUCHERS BY CUSTOMER ID ==
            
            input customerId(UUID)
            """;

    private static final String deleteVoucherWallet = """
            
            == DELETE VOUCHER BY CUSTOMER ID ==
            
            input customerId(UUID)
            """;

    private static final String findCustomerWallet = """
            
            == FIND CUSTOMER BY VOUCHER ID
            
            input voucherId(UUID)
            """;


    private static final String exit = """
            
            === Bye Bye ===
            """;

    public void printMessage(String message) {
        System.out.print(message);
    }

    public void printlnMessage(String message) {
        System.out.println(message);
    }

    public void getInput() {
        System.out.print("> ");
    }

    public void printWelcomeMessage() {
        printMessage(welcome);
    }

    public void printCustomerOptionMessage() {
        printMessage(customerOption);
    }

    public void printVoucherOptionMessage() {
        printMessage(voucherOption);
    }

    public void printWalletOptionMessage() {
        printMessage(walletOption);
    }

    public void printCreateVoucherMessage() {
        printMessage(createVoucher);
    }

    public void printCreateVoucherCompleteMessage() {
        printMessage(createVoucherComplete);
    }

    public void printUpdateVoucherMessage() {
        printMessage(updateVoucher);
    }

    public void printUpdateVoucherCompleteMessage() {
        printMessage(updateVoucherComplete);
    }

    public void printDeleteVoucherMessage() {
        printMessage(deleteVoucher);
    }

    public void printWalletMessage() {
        printMessage(wallet);
    }

    public void printCreateWalletMessage() {
        printMessage(createWallet);
    }

    public void printAllVouchersByCustomerId() {
        printMessage(findVoucherWallet);
    }

    public void printDeleteVoucherByCustomerId() {
        printMessage(deleteVoucherWallet);
    }

    public void printCustomerByVoucherId() {
        printMessage(findCustomerWallet);
    }

    public void printCustomerList(List<Customer> customerList) {
        printMessage(showCustomerList);
        for (Customer customer : customerList) {
            printlnMessage("customerId : " + customer.getUserId() + ", name : " + customer.getUserName() + ", age : " + customer.getUserAge());
        }
    }

    public void printCustomer(Customer customer) {
        printlnMessage("customerId : " + customer.getUserId() + ", name : " + customer.getUserName() + ", age : " + customer.getUserAge());
    }

    public void printBlackList(List<Customer> blackList) {
        printMessage(showBlackList);
        for(Customer blackCustomer : blackList) {
            printlnMessage("customerId : " + blackCustomer.getUserId() + ", name : " + blackCustomer.getUserName() + ", age : " + blackCustomer.getUserAge());
        }
    }

    public void printVouchers(List<Voucher> vouchers) {
        printMessage(showVoucherLists);
        for(Voucher voucher : vouchers) {
            printlnMessage("voucherId : " + voucher.getVoucherId() + ", policy : " + voucher.getDiscountPolicy().getPolicyStatus() + ", amountOrPercent : " + voucher.getDiscountPolicy().getAmountOrPercent());
        }
    }

    public void printExitMessage() {
        printMessage(exit);
    }

}
