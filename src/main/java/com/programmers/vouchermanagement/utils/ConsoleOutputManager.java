package com.programmers.vouchermanagement.utils;

import com.programmers.vouchermanagement.customer.dto.CustomerResponseDto;
import com.programmers.vouchermanagement.voucher.dto.VoucherResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConsoleOutputManager {

    public void printCommandMenu() {
        System.out.println("=== Voucher Program === \n" +
                "Type 1 to create a new voucher. \n" +
                "Type 2 to list all vouchers. \n" +
                "Type 3 to find a voucher by id. \n" +
                "Type 4 to update a voucher. \n" +
                "Type 5 to delete all vouchers. \n" +
                "Type 6 to list all blacklist customers. \n" +
                "Type 7 to create a new wallet. \n" +
                "Type 8 to find customer's vouchers. \n" +
                "Type 9 to find voucher's customers. \n" +
                "Type 10 to delete wallets by customer id. \n" +
                "Type 11 to exit the program. \n");
    }

    public void printCreateVoucher() {
        System.out.println("=== Create A New Voucher ===");
    }

    public void printVoucherTypeMenu() {
        System.out.println("Type fixed to create a fixed amount voucher. \n" +
                "Type percent to create a percent discount voucher. \n");
    }

    public void printDiscountRequest() {
        System.out.println("Type a discount amount. \n");
    }

    public void printSuccessCreation() {
        System.out.println("Creation completed. \n");
    }

    public void printList() {
        System.out.println("=== List All Vouchers === \n");
    }

    public void printVoucherInfo(List<VoucherResponseDto> voucherResponseDtos) {

        for (VoucherResponseDto voucherResponseDto : voucherResponseDtos) {
            System.out.println("voucherId : " + voucherResponseDto.getVoucherId() + " \n" +
                    "voucherType : " + voucherResponseDto.getVoucherType() + " \n" +
                    "discount : " + voucherResponseDto.getDiscount() + " \n");

            System.out.println("------------------------------ \n");
        }
    }

    public void printReadVoucherById() {
        System.out.println("=== Search Voucher By Id ===");
    }

    public void printUpdateVoucher() {
        System.out.println("=== Update Voucher By Id ===");
    }

    public void printSuccessUpdate() {
        System.out.println("Update completed. \n");
    }

    public void printRemoveVoucher() {
        System.out.println("=== Delete All Vouchers ===");
    }

    public void printGetVoucherId() {
        System.out.println("Type a voucher id. \n");
    }

    public void printBlackList() {
        System.out.println("=== List All Blacklist Customers === \n");
    }

    public void printCustomerInfo(List<CustomerResponseDto> customerResponseDtos) {

        for (CustomerResponseDto customerResponseDto : customerResponseDtos) {
            System.out.println("customerId : " + customerResponseDto.getCustomerId() + " \n" +
                    "name : " + customerResponseDto.getName() + "\n");

            System.out.println("------------------------------ \n");
        }
    }

    public void printGetCustomerId() {
        System.out.println("Type a customer id. \n");
    }

    public void printCreateWallet() {
        System.out.println("=== Create A New Wallet ===");
    }

    public void printReadVouchersByCustomer() {
        System.out.println("=== Search Customer's Vouchers ===");
    }

    public void printReadCustomersByVoucher() {
        System.out.println("=== Search Voucher's Customers ===");
    }

    public void printRemoveWallet() {
        System.out.println("=== Delete Customer's Vouchers ===");
    }

    public void printExit() {
        System.out.println("You have typed exit. Exit the program. \n");
    }

    public void printWrongInputLong() {
        System.out.println("Only integers can be entered. Please enter it again. \n");
    }

    public void printEnterAgain(String message) {
        System.out.println(message + "Please enter it again. \n");
    }

    public void printReturnMain(String message) {
        System.out.println(message + "Return to main menu. \n");
    }
}
