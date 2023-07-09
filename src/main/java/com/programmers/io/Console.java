package com.programmers.io;

import com.programmers.customer.domain.Customer;
import com.programmers.voucher.domain.Voucher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Console implements Input, Output {

    private static final String MENU_MESSAGE =
            "=== Voucher Program ===\n" +
            "Type 'exit' or '1' to exit the program.\n" +
            "Type 'create' or '2' to create a new voucher or a new customer.\n" +
            "Type 'list' or '3' to list all vouchers or all customers.\n" +
            "Type 'update' or '4' to update a voucher or a customer.\n" +
            "Type 'delete' or '5' to delete a voucher or a customer.\n" +
            "Type 'wallet' or '6' to use wallet function.";
    private static final String VOUCHER_TYPE_MESSAGE =
            "\n=== Voucher Type ===\n" +
            "Type voucher name or number to create.\n" +
            "1. Fixed Amount Voucher\n" +
            "2. Percent Discount Voucher";
    private static final String DISCOUNT_VALUE_MESSAGE = "\n=== Type discount amount or rate ===";
    private static final String VOUCHER_NAME_MESSAGE = "\n=== Type voucher name ===";
    private static final String VOUCHER_CREATED_MESSAGE = "--- Voucher Created !! ---\n";
    private static final String VOUCHER_LIST_TITLE_MESSAGE = "\n=== Voucher List ===";
    private static final String VOUCHER_LIST_EMPTY_MESSAGE = "\n(There is no voucher.)\n";
    private static final String BLACKLIST_MESSAGE = "\n=== Blacklist ===";
    private static final String CREATE_MESSAGE =
            "\n=== Create ===\n" +
            "Type '1' or '2' to create item.\n" +
            "1. voucher\n" +
            "2. customer";
    private static final String CUSTOMER_NAME_MESSAGE = "\n=== Type customer name ===";
    private static final String CUSTOMER_CREATED_MESSAGE = "--- Customer Created !! ---\n";
    private static final String LIST_MESSAGE =
            "\n=== List ===\n" +
            "Type '1' or '2' to list items.\n" +
            "1. voucher\n" +
            "2. customer";
    private static final String CUSTOMER_LIST_MESSAGE =
            "\n=== Customer List ===\n" +
            "Type '1' or '2' to choose Customer Type.\n" +
            "1. normal customer\n" +
            "2. blacklist";
    private static final String NORMAL_CUSTOMER_LIST_TITLE_MESSAGE = "\n=== Normal Customer List ===";
    private static final String NORMAL_CUSTOMER_LIST_EMPTY_MESSAGE = "\n(There is no normal customer.)\n";
    private static final String UPDATE_MESSAGE =
            "\n=== Update ===\n" +
            "Type '1' or '2' to update item.\n" +
            "1. voucher\n" +
            "2. normal customer";
    private static final String UPDATE_VOUCHER_ID_MESSAGE = "=== Type voucher id to update ===";
    private static final String UPDATE_NEW_VOUCHER_NAME_MESSAGE = "\n=== Type new voucher name ===";
    private static final String UPDATE_NEW_VOUCHER_VALUE_MESSAGE = "\n=== Type new voucher value ===";
    private static final String UPDATE_VOUCHER_COMPLETE_MESSAGE = "--- The voucher updated successfully !! ---\n";
    private static final String UPDATE_CUSTOMER_ID_MESSAGE = "=== Type customer id to update ===";
    private static final String UPDATE_NEW_CUSTOMER_NAME_MESSAGE = "\n=== Type new customer name ===";
    private static final String UPDATE_CUSTOMER_COMPLETE_MESSAGE = "--- The customer updated successfully !! ---\n";
    private static final String DELETE_MESSAGE =
            "\n=== Delete ===\n" +
            "Type '1' or '2' to delete item.\n" +
            "1. voucher\n" +
            "2. normal customer";
    private static final String DELETE_VOUCHER_ID_MESSAGE = "\n=== Type voucher id to delete ===";
    private static final String DELETE_TYPE_VOUCHER_SELECTION_MESSAGE =
            "=== Delete ===\n" +
            "Type '1' or '2' to delete type.\n" +
            "1. Delete one voucher.\n" +
            "2. Delete all vouchers.";
    private static final String DELETE_VOUCHER_COMPLETE_MESSAGE = "--- The voucher deleted successfully !! ---\n";
    private static final String DELETE_ALL_VOUCHERS_COMPLETE_MESSAGE = "--- All vouchers deleted successfully !! ---\n";
    private static final String DELETE_CUSTOMER_ID_MESSAGE = "\n=== Type customer id to delete ===";
    private static final String DELETE_TYPE_CUSTOMER_SELECTION_MESSAGE =
            "\n=== Delete ===\n" +
            "Type '1' or '2' to delete type.\n" +
            "1. Delete one customer.\n" +
            "2. Delete all customers.";
    private static final String DELETE_CUSTOMER_COMPLETE_MESSAGE = "--- The customer deleted successfully !! ---\n";
    private static final String DELETE_ALL_CUSTOMERS_COMPLETE_MESSAGE = "--- All customers deleted successfully !! ---\n";
    private static final String WALLET_MESSAGE =
            "\n=== Wallet ===\n" +
            "Type '1 / 2 / 3 / 4' to use function.\n" +
            "1. Assign a voucher.\n" +
            "2. Search for a customer. (Search for a customer to find out the customer's voucher.)\n" +
            "3. Search for a voucher. (Search for a voucher to find the assigned customer.)\n" +
            "4. Delete the voucher assigned to the customer.";
    private static final String WALLET_ASSIGN_TITLE_MESSAGE = "\n=== Assign a voucher. ===";
    private static final String WALLET_ASSIGN_VOUCHER_ID_MESSAGE = "\n=== Type voucher id to assign. ===";
    private static final String WALLET_ASSIGN_CUSTOMER_ID_MESSAGE = "\n=== Type customer id to assign. ===";
    private static final String WALLET_ASSIGN_COMPLETE_MESSAGE = "--- The voucher assigned successfully !! ---\n";
    private static final String WALLET_SEARCH_CUSTOMER_TITLE_MESSAGE = "\n=== Search for a customer. ===";
    private static final String WALLET_SEARCH_CUSTOMER_ID_MESSAGE = "\n=== Type customer id to search. ===";
    private static final String WALLET_SEARCH_VOUCHER_TITLE_MESSAGE = "\n=== Search for a voucher. ===";
    private static final String WALLET_SEARCH_VOUCHER_ID_MESSAGE = "\n=== Type voucher id to search. ===";

    @Override
    public void printMenu() {
        System.out.println(MENU_MESSAGE);
    }

    @Override
    public String readInput() {
        return new Scanner(System.in).nextLine();
    }

    @Override
    public void printVoucherType() {
        System.out.println(VOUCHER_TYPE_MESSAGE);
    }

    public void printDiscountValueInput() {
        System.out.println(DISCOUNT_VALUE_MESSAGE);
    }

    public void printVoucherNameInput() {
        System.out.println(VOUCHER_NAME_MESSAGE);
    }

    public void printVoucherCreated() {
        System.out.println(VOUCHER_CREATED_MESSAGE);
    }

    public void printVoucherListTitle() {
        System.out.println(VOUCHER_LIST_TITLE_MESSAGE);
    }

    public void printVoucherListEmptyMessage() {
        System.out.println(VOUCHER_LIST_EMPTY_MESSAGE);
    }

    public void printVouchers(List<Voucher> vouchers) {
        vouchers.forEach(System.out::println);
        System.out.println();
    }

    public void printBlacklistTitle() {
        System.out.println(BLACKLIST_MESSAGE);
    }

    public void printBlacklist(List<String> blacklist) {
        blacklist.forEach(System.out::println);
        System.out.println();
    }

    @Override
    public void printCreateMessage() {
        System.out.println(CREATE_MESSAGE);
    }

    public void printCustomerNameInput() {
        System.out.println(CUSTOMER_NAME_MESSAGE);
    }

    public void printCustomerCreated() {
        System.out.println(CUSTOMER_CREATED_MESSAGE);
    }

    @Override
    public void printListMessage() {
        System.out.println(LIST_MESSAGE);
    }

    @Override
    public void printCustomerListMessage() {
        System.out.println(CUSTOMER_LIST_MESSAGE);
    }

    public void printNormalCustomerListTitle() {
        System.out.println(NORMAL_CUSTOMER_LIST_TITLE_MESSAGE);
    }

    public void printNormalCustomerListEmptyMessage() {
        System.out.println(NORMAL_CUSTOMER_LIST_EMPTY_MESSAGE);
    }

    public void printCustomers(List<Customer> customers) {
        customers.forEach(System.out::println);
        System.out.println();
    }

    @Override
    public void printUpdateMessage() {
        System.out.println(UPDATE_MESSAGE);
    }

    public void printUpdateVoucherIdMessage() {
        System.out.println(UPDATE_VOUCHER_ID_MESSAGE);
    }

    public void printUpdateNewVoucherValueMessage() {
        System.out.println(UPDATE_NEW_VOUCHER_VALUE_MESSAGE);
    }

    public void printUpdateNewVoucherNameMessage() {
        System.out.println(UPDATE_NEW_VOUCHER_NAME_MESSAGE);
    }

    public void printUpdateVoucherCompleteMessage() {
        System.out.println(UPDATE_VOUCHER_COMPLETE_MESSAGE);
    }

    public void printUpdateCustomerIdMessage() {
        System.out.println(UPDATE_CUSTOMER_ID_MESSAGE);
    }

    public void printUpdateNewCustomerNameMessage() {
        System.out.println(UPDATE_NEW_CUSTOMER_NAME_MESSAGE);
    }

    public void printUpdateCustomerCompleteMessage() {
        System.out.println(UPDATE_CUSTOMER_COMPLETE_MESSAGE);
    }

    @Override
    public void printDeleteMessage() {
        System.out.println(DELETE_MESSAGE);
    }

    public void printDeleteVoucherIdMessage() {
        System.out.println(DELETE_VOUCHER_ID_MESSAGE);
    }

    @Override
    public void printDeleteTypeVoucherSelectionMessage() {
        System.out.println(DELETE_TYPE_VOUCHER_SELECTION_MESSAGE);
    }

    public void printDeleteVoucherCompleteMessage() {
        System.out.println(DELETE_VOUCHER_COMPLETE_MESSAGE);
    }

    public void printDeleteAllVouchersCompleteMessage() {
        System.out.println(DELETE_ALL_VOUCHERS_COMPLETE_MESSAGE);
    }

    public void printDeleteCustomerIdMessage() {
        System.out.println(DELETE_CUSTOMER_ID_MESSAGE);
    }

    @Override
    public void printDeleteTypeCustomerSelectionMessage() {
        System.out.println(DELETE_TYPE_CUSTOMER_SELECTION_MESSAGE);
    }

    public void printDeleteCustomerCompleteMessage() {
        System.out.println(DELETE_CUSTOMER_COMPLETE_MESSAGE);
    }

    public void printDeleteAllCustomersCompleteMessage() {
        System.out.println(DELETE_ALL_CUSTOMERS_COMPLETE_MESSAGE);
    }

    @Override
    public void printWalletMessage() {
        System.out.println(WALLET_MESSAGE);
    }

    public void printWalletAssignTitleMessage() {
        System.out.println(WALLET_ASSIGN_TITLE_MESSAGE);
    }

    public void printWalletAssignVoucherIdMessage() {
        System.out.println(WALLET_ASSIGN_VOUCHER_ID_MESSAGE);
    }

    public void printWalletAssignCustomerIdMessage() {
        System.out.println(WALLET_ASSIGN_CUSTOMER_ID_MESSAGE);
    }

    public void printWalletAssignCompleteMessage() {
        System.out.println(WALLET_ASSIGN_COMPLETE_MESSAGE);
    }

    public void printWalletSearchCustomerTitleMessage() {
        System.out.println(WALLET_SEARCH_CUSTOMER_TITLE_MESSAGE);
    }

    public void printWalletSearchCustomerIdMessage() {
        System.out.println(WALLET_SEARCH_CUSTOMER_ID_MESSAGE);
    }

    public void printWalletSearchVoucherTitleMessage() {
        System.out.println(WALLET_SEARCH_VOUCHER_TITLE_MESSAGE);
    }

    public void printWalletSearchVoucherIdMessage() {
        System.out.println(WALLET_SEARCH_VOUCHER_ID_MESSAGE);
    }

    public void printCustomer(Customer customer) {
        System.out.println(customer + "\n");
    }
}
