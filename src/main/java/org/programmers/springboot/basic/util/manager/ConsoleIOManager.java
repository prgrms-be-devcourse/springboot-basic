package org.programmers.springboot.basic.util.manager;

import org.programmers.springboot.basic.domain.customer.dto.CustomerResponseDto;
import org.programmers.springboot.basic.domain.customer.entity.Customer;
import org.programmers.springboot.basic.domain.voucher.dto.VoucherResponseDto;
import org.programmers.springboot.basic.domain.voucher.entity.Voucher;
import org.programmers.springboot.basic.util.exception.ConsoleIOFailureException;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Component
public class ConsoleIOManager {

    public BufferedReader getBufferedReader() {
        return new BufferedReader(new InputStreamReader(System.in));
    }

    public String getInput() {
        try {
            return getBufferedReader().readLine();
        } catch (IOException e) {
            throw new ConsoleIOFailureException("[System] 콘솔에 입력된 값을 받아오는데 실패했습니다.\n");
        }
    }

    public int getInteger() {
        return Integer.parseInt(getInput());
    }

    public Long getLong() {
        return Long.parseLong(getInput());
    }

    public void printSystem() {

        String msg = """
                === Voucher Management Program ===
                Type exit to exit the program.
                Type voucher to enter a voucher program.
                Type customer to enter a customer program.
                """;
        System.out.println(msg);
    }

    public void printVoucherSystem() {

        String msg = """
                === Voucher Program ===
                Type create to a new voucher.
                Type list to list all voucher.
                Type find to find a voucher by voucherId.
                Type customer to list all customer by voucherId.
                Type update to update voucher.
                Type delete to delete a voucher by voucherId.
                Type delete_all to delete all voucher.
                Type back to back voucherManagement program.
                """;
        System.out.println(msg);
    }

    public void printCustomerSystem() {

        String msg = """
                === Customer Program ===
                Type create to a new customer.
                Type list to list all customer.
                Type blacklist to list all blacklist.
                Type assign to add voucher in wallet by email and voucherId.
                Type wallet to list all voucher by email.
                Type remove to remove voucher from wallet by voucherId.
                Type delete to delete voucher.
                Type add_black to add customer to blacklist.
                Type delete_black to delete customer from blacklist.
                Type delete_all to delete all customer.
                Type back to back voucherManagement program
                """;
        System.out.println(msg);
    }

    public void printVoucherCreateHandler() {

        String msg = """
                === CREATE ===
                1. FixedAmountVoucher
                2. PercentAmountVoucher
                
                Q. Enter type of NUMBER you want to create:
                """;
        System.out.println(msg);
    }

    public void printFoundVoucher(VoucherResponseDto voucherResponseDto) {

        String msg = """
                === LIST ===
                """;
        System.out.println(msg);
        printVoucherInfo(voucherResponseDto);
    }

    public void printCustomerCreateHandler() {

        String msg = """
                === CREATE ===
                Q. Enter name type of STRING you want to create:
                Q. Enter email type of STRING you want to create:
                """;
        System.out.println(msg);
    }

    public void printFindHandler() {

        String msg = """
                === FIND ===
                Q. Enter voucherId type of UUID you want to find:
                """;
        System.out.println(msg);
    }

    public void printUpdateHandler() {

        String msg = """
               === UPDATE ===
               Q. Enter voucherId type of UUID you want to update:
               Q. Enter discount type of NUMBER you want to update:
                """;
        System.out.println(msg);
    }

    public void printVoucherDeleteHandler() {

        String msg = """
                === DELETE ===
                Q. Enter voucherId type of UUID you want to delete:
                """;
        System.out.println(msg);
    }

    public void printCustomerDeleteHandler() {

        String msg = """
                === DELETE ===
                Q. Enter customerId type of UUID you want to delete:
                """;
        System.out.println(msg);
    }

    public void printDeleteAllHandler() {
        String msg = """
                === DELETE ALL ===
                Q, Enter Y if you really want to delete all info (or not: any else)
                """;
        System.out.println(msg);
    }

    public void printAddBlackHandler() {

        String msg = """
                === ADD BLACKLIST ===
                Q. Enter customerId type of UUID you want to add blacklist:
                """;
        System.out.println(msg);
    }

    public void printDeleteBlackHandler() {

        String msg = """
                === DELETE BLACKLIST ===
                Q. Enter customerId type of UUID you want to delete from blacklist:
                """;
        System.out.println(msg);
    }

    public void printListByConsumerHandler() {

        String msg = """
                === Voucher LIST ABOUT Email ===
                Q. Enter email type of STRING you want to list vouchers owned by the customer:
                """;
        System.out.println(msg);
    }

    public void printListByVoucherHandler() {

        String msg = """
                === Customer LIST ABOUT VoucherId ===
                Q. Enter voucherId type of UUID you want to list customers grouped by the voucher:
                """;
        System.out.println(msg);
    }

    public void printRemoveVoucherFromWalletHandler() {

        String msg = """
                === REMOVE VOUCHER FROM WALLET ===
                Q. Enter email and voucherId (type of UUID) you want to remove voucher from wallet:
                """;
        System.out.println(msg);
    }

    public void printAssignHandler() {

        String msg = """
                === ASSIGN ===
                Q. Enter email type of STRING you want to assign:
                Q. Enter voucherId type of UUID you want to assign from wallet:
                """;
        System.out.println(msg);
    }

    public void printVoucher(List<VoucherResponseDto> responseDtos) {
        String msg = """
                === Voucher List ===
                """;
        System.out.println(msg);
        responseDtos.forEach(this::printVoucherInfo);
    }

    public void printCustomer(List<CustomerResponseDto> responseDtos) {
        String msg = """
                === Customer List ===
                """;
        System.out.println(msg);
        responseDtos.forEach(this::printCustomerInfo);
    }

    public void printBlackList(List<CustomerResponseDto> responseDtos) {
        String msg = """
                === BlackList ===
                """;
        System.out.println(msg);
        responseDtos.forEach(this::printBlackListInfo);
    }

    public void printVoucherInfo(VoucherResponseDto responseDto) {

        String msg = String.format("""
                voucherId: %s
                voucherType: %s
                discount: %d%%
                ---------------------------------
                """, responseDto.voucherId().toString(), responseDto.voucherType(), responseDto.discount());
        System.out.println(msg);
    }

    public void printCustomerInfo(CustomerResponseDto responseDto) {

        String msg = String.format("""
                customerId: %s
                email: %s
                name: %s
                customerType: %s
                ---------------------------------
                """, responseDto.getCustomerId().toString(), responseDto.getEmail(), responseDto.getName(), responseDto.getCustomerType());
        System.out.println(msg);
    }

    public void printBlackListInfo(CustomerResponseDto responseDto) {

        String msg = String.format("""
                customerId: %s
                email: %s
                name: %s
                ---------------------------------
                """, responseDto.getCustomerId().toString(), responseDto.getEmail(), responseDto.getName());
        System.out.println(msg);
    }

    public void printWalletInfoByConsumer(Customer customer, List<VoucherResponseDto> responseDtos) {

        String msg = String.format("""
                === customer Info ===
                customerId: %s
                email: %s
                name: %s
                customerType: %s
                ==========================================
                """, customer.getCustomerId().toString(), customer.getEmail(), customer.getName(), customer.getCustomerType());
        System.out.println(msg);
        printVoucher(responseDtos);
    }

    public void printWalletInfoByVoucher(Voucher voucher, List<CustomerResponseDto> responseDtos) {

        String msg = String.format("""
                === voucher Info ===
                voucherId: %s
                voucherType: %s
                discount: %d%%
                ==========================================
                """, voucher.getVoucherId().toString(), voucher.getVoucherType(), voucher.getDiscount());
        System.out.println(msg);
        printCustomer(responseDtos);
    }

    public void printBackHandler() {

        String msg = """
                [System] VoucherManagement 메뉴로 돌아갑니다.
                """;
        System.out.println(msg);
    }

    public void printRequestDiscount() {
        String msg = """
                Q. Enter discount type of NUMBER you want to create.
                """;
        System.out.println(msg);
    }

    public void printExit() {
        String msg = """
                [System] 시스템을 종료합니다.
                """;
        System.out.println(msg);
    }

    public void printErrCommand() {
        String msg = """
                [System] 잘못된 명령의 접근입니다.
                """;
        System.out.println(msg);
    }
}
