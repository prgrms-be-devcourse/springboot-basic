package org.programmers.springboot.basic.util.manager;

import org.programmers.springboot.basic.domain.customer.dto.CustomerResponseDto;
import org.programmers.springboot.basic.domain.voucher.dto.VoucherResponseDto;
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

        System.out.println("=== Voucher Program ===");
        System.out.println("Type exit to exit the program.");
        System.out.println("Type create to a new voucher.");
        System.out.println("Type list to list all voucher.");
        System.out.println("Type blacklist to list all blacklist.");
    }

    public void printCreateHandler() {

        System.out.println("=== CREATE ===");
        System.out.println("1. FixedAmountVoucher");
        System.out.println("2. PercentAmountVoucher");
    }

    public void printListHandler() {

        System.out.println("=== LIST ===");
    }

    public void printBlackListHandler() {

        System.out.println("=== BLACKLIST ===");
    }

    public void printVoucher(List<VoucherResponseDto> responseDtos) {
        responseDtos.forEach(this::printVoucherInfo);
    }

    public void printBlackList(List<CustomerResponseDto> responseDtos) {
        responseDtos.forEach(this::printBlackListInfo);
    }

    public void printVoucherInfo(VoucherResponseDto responseDto) {

        System.out.println("voucherId: " + responseDto.voucherId());
        System.out.println("voucherType: " + responseDto.voucherType());
        System.out.println("discount: " + responseDto.discount());
        System.out.println("---------------------------------\n");
    }

    public void printBlackListInfo(CustomerResponseDto responseDto) {

        System.out.println("customerId: " + responseDto.customerId());
        System.out.println("name: " + responseDto.name());
        System.out.println("customerType: " + responseDto.customerType());
        System.out.println("---------------------------------\n");
    }

    public void printDiscount() {
        System.out.println("Q. Discount를 입력하세요.");
    }

    public void printExit() {
        System.out.println("[System] 시스템을 종료합니다.");
    }

    public void printErrCommand() {
        System.out.println("[System] 잘못된 명령의 접근입니다.");
    }
}
