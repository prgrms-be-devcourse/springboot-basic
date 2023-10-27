package com.programmers.vouchermanagement.utils;

import com.programmers.vouchermanagement.customer.dto.CustomerResponseDto;
import com.programmers.vouchermanagement.voucher.dto.VoucherResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConsoleOutputManager {

    public void printCommandMenu() {
        System.out.println("=== Voucher Program === \n" +
                "Type exit to exit the program. \n" +
                "Type create to create a new voucher. \n" +
                "Type list to list all vouchers. \n" +
                "Type blacklist to list all blacklist customers. \n");
    }

    public void printVoucherTypeMenu() {
        System.out.println("=== Create A New Voucher === \n" +
                "Type fixed to create a fixed amount voucher. \n" +
                "Type percent to create a percent discount voucher. \n");
    }

    public void printDiscountRequest() {
        System.out.println("Type a discount amount. \n");
    }

    public void printSuccessCreation() {
        System.out.println("Voucher creation completed. \n");
    }

    public void printList() {
        System.out.println("=== List All Vouchers ===");
    }

    public void printVoucherInfo(List<VoucherResponseDto> voucherResponseDtos) {

        for (VoucherResponseDto voucherResponseDto : voucherResponseDtos) {
            System.out.println("voucherId : " + voucherResponseDto.getVoucherId() + " \n" +
                    "voucherType : " + voucherResponseDto.getVoucherType() + " \n" +
                    "discount : " + voucherResponseDto.getDiscount() + " \n");

            System.out.println("------------------------------ \n");
        }
    }

    public void printBlackList() {
        System.out.println("=== List All Blacklist Customers ===");
    }

    public void printCustomerInfo(List<CustomerResponseDto> customerResponseDtos) {

        for (CustomerResponseDto customerResponseDto : customerResponseDtos) {
            System.out.println("customerId : " + customerResponseDto.getCustomerId() + " \n" +
                    "name : " + customerResponseDto.getName() + "\n");

            System.out.println("------------------------------ \n");
        }
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
