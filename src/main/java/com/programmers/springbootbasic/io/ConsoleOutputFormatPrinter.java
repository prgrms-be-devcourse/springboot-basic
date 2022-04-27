package com.programmers.springbootbasic.io;

import com.programmers.springbootbasic.domain.VoucherType;
import com.programmers.springbootbasic.domain.Customer;
import com.programmers.springbootbasic.domain.CustomerVoucherLog;
import com.programmers.springbootbasic.domain.Voucher;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class ConsoleOutputFormatPrinter implements StandardOutput {

    public static final String DIVISION_LINE = "-------------------------------------------------------------------------------------------------------------------------";

    @Override
    public void write(String message) {
        System.out.print(message);
    }

    @Override
    public void writeln(String message) {
        System.out.println(message);
    }

    @Override
    public void printNewCustomer(Customer customer) {
        System.out.println("새로운 고객이 생성되었습니다.");
        System.out.println("생성된 고객: " +
                "[회원 ID: " + customer.getCustomerId() + "], " +
                "[회원이름: " + customer.getName() + "]");
    }

    @Override
    public void printAllocationAcknowledgement(CustomerVoucherLog statusDTO) {
        System.out.println("[회원 ID: " + statusDTO.getCustomerId() +
                "] 님에게 [할인권 ID: " + statusDTO.getVoucherId() +
                "] 을 정상적으로 할당하였습니다.");
    }

    @Override
    public void printVouchersOfCustomer(String customerId, List<Voucher> vouchers) {
        if (vouchers.size() > 0) {
            System.out.println("[회원 ID: " +  customerId + "] 님이 보유하고 있는 할인권은 다음과 같습니다.");
            printAllVouchersFormatted(vouchers);
        }
        else {
            System.out.println("현재 [회원 ID:" +  customerId + "] 님이 보유하고 있는 할인권이 없습니다.");
        }
    }

    @Override
    public void printFoundCustomer(Customer customer) {
        System.out.println("요청하신 고객의 정보는 다음과 같습니다.");
        System.out.println("검색된 고객: " + customer);
    }

    @Override
    public void printCustomerByVoucherId(Customer customer) {
        System.out.println("요청하신 고객의 정보는 다음과 같습니다.");
        System.out.println("검색된 고객: " +
                "[회원 ID: " + customer.getCustomerId() + "], " +
                "[회원이름: " + customer.getName() + "], " +
                "[할인권 취득일: " + customer.getRegistrationDate() + "]");
    }

    @Override
    public void printDeletedCustomer(Customer customer) {
        System.out.println("고객의 정보가 삭제되었습니다.");
        System.out.println("삭제된 고객: " + customer);
    }

    @Override
    public void printAllCustomers(List<Customer> customers) {
        if (customers.size() > 0) {
            System.out.println("현재 저장된 모든 고객의 정보는 다음과 같습니다.");
            System.out.println(DIVISION_LINE);
            System.out.printf("%15s%20s%20s%n", "가입일", "아이디", "이름");
            System.out.println(DIVISION_LINE);
            customers.forEach(c -> System.out.printf("%20s%20s%20s\n",
                    c.getRegistrationDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")),
                    c.getCustomerId(),
                    c.getName()));
            System.out.println(DIVISION_LINE);
        }
        else {
            System.out.println("현재 저장된 고객이 없습니다.");
        }
    }

    @Override
    public void printNewVoucher(Voucher voucherDTO) {
        String benefit = "";
        benefit = (voucherDTO.getFixedAmount() == null ? benefit :
                "[할인 금액: " + voucherDTO.getFixedAmount() + "원]");
        benefit = (voucherDTO.getDiscountPercent() == null ? benefit :
                "[할인율: " + voucherDTO.getDiscountPercent() + "%]");

        System.out.println("새로운 할인권이 생성되었습니다.");
        System.out.print("생성된 할인권: " +
                "[할인권 ID: " + voucherDTO.getVoucherId() + "], " +
                "[할인권 유형: " + VoucherType.getFormalName(voucherDTO.getType()) + "], " +
                benefit);
    }

    @Override
    public void printFoundVoucher(Voucher voucherDTO) {
        String benefit = "";
        benefit = (voucherDTO.getFixedAmount() == null ? benefit :
                "[할인 금액: " + voucherDTO.getFixedAmount() + "원]");
        benefit = (voucherDTO.getDiscountPercent() == null ? benefit :
                "[할인율: " + voucherDTO.getDiscountPercent() + "%]");

        System.out.println("요청하신 할인권의 정보는 다음과 같습니다.");
        System.out.print("검색된 할인권: " +
                "[생성일: " + voucherDTO.getCreatedAt() + "], " +
                "[할인권 유형: " + VoucherType.getFormalName(voucherDTO.getType()) + "], " +
                benefit);
    }

    @Override
    public void printAvailableVouchers(List<Voucher> vouchers) {
        if (vouchers.size() > 0) {
            System.out.println("이용 가능한 할인권의 정보는 다음과 같습니다.");
            printAllVouchersFormatted(vouchers);
        }
        else {
            System.out.println("현재 이용 가능한 할인권이 없습니다.");
        }
    }

    @Override
    public void printAllVouchers(List<Voucher> vouchers) {
        if (vouchers.size() > 0) {
            System.out.println("현재 저장된 모든 할인권의 정보는 다음과 같습니다.");
            printAllVouchersFormatted(vouchers);
        }
        else {
            System.out.println("현재 저장된 할인권이 없습니다.");
        }
    }

    @Override
    public void printAllVouchersFormatted(List<Voucher> vouchers) {
        System.out.println(DIVISION_LINE);
        System.out.printf("%15s%35s%30s%16s%8s%n", "생성일", "할인권 No.", "할인권 유형", "할인 금액", "할인율");
        System.out.println(DIVISION_LINE);
        vouchers.forEach(v -> System.out.printf("%20s%47s%20s%14s%10s\n",
                v.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")),
                v.getVoucherId(),
                (VoucherType.getFormalName(v.getType())),
                (v.getFixedAmount() == null ? "N/A" : String.valueOf(v.getFixedAmount()).concat("원")),
                (v.getDiscountPercent() == null ? "N/A" : String.valueOf(v.getDiscountPercent()).concat("%"))));
        System.out.println(DIVISION_LINE);
    }

    @Override
    public void printDeletedVoucher(Voucher voucherDTO) {
        System.out.println("할인권이 삭제되었습니다.");
        System.out.println("삭제된 할인권: " + "[할인권 ID: " + voucherDTO.getVoucherId() + "]");
    }

}
