package com.programmers.springbootbasic.consolestarter;

import com.programmers.springbootbasic.domain.VoucherType;
import com.programmers.springbootbasic.dto.CustomerDTO;
import com.programmers.springbootbasic.dto.StatusDTO;
import com.programmers.springbootbasic.dto.VoucherDTO;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class OutputConsolePrinter {

    public static final String DIVISION_LINE = "--------------------------------------------------------------------------------------------------------------------";

    public static void printNewCustomer(CustomerDTO customerDTO) {
        System.out.println("새로운 고객이 생성되었습니다.");
        System.out.println("생성된 고객: " +
                "[회원 ID: " + customerDTO.getCustomerId() + "], " +
                "[회원이름: " + customerDTO.getName() + "]");
    }

    public static void printAllocationAcknowledgement(StatusDTO statusDTO) {
        System.out.println("[회원 ID: " + statusDTO.getCustomerId() +
                "] 님에게 [할인권 ID: " + statusDTO.getVoucherId() +
                "] 을 정상적으로 할당하였습니다.");
    }

    public static void printVouchersOfCustomer(String customerId, List<VoucherDTO> vouchers) {
        if (vouchers.size() > 0) {
            System.out.println("[회원 ID: " +  customerId + "] 님이 보유하고 있는 할인권은 다음과 같습니다.");
            printAllVouchersFormatted(vouchers);
        }
        else {
            System.out.println("현재 [회원 ID:" +  customerId + "] 님이 소유하고 있는 할인권이 없습니다.");
        }

    }

    public static void printFoundCustomer(CustomerDTO customerDTO) {
        System.out.println("요청하신 고객의 정보는 다음과 같습니다.");
        System.out.println("검색된 고객: " + customerDTO);
    }

    public static void printCustomerByVoucherId(CustomerDTO customerDTO) {
        System.out.println("요청하신 고객의 정보는 다음과 같습니다.");
        System.out.println("검색된 고객: " +
                "[회원 ID: " + customerDTO.getCustomerId() + "], " +
                "[회원이름: " + customerDTO.getName() + "], " +
                "[할인권 소유일: " + customerDTO.getRegistrationDate() + "]");
    }

    public static void printDeletedCustomer(CustomerDTO customerDTO) {
        System.out.println("고객의 정보가 삭제되었습니다.");
        System.out.println("삭제된 고객: " + customerDTO);
    }

    public static void printAllCustomers(List<CustomerDTO> customers) {
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

    public static void printNewVoucher(VoucherDTO voucherDTO) {
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

    public static void printFoundVoucher(VoucherDTO voucherDTO) {
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

    public static void printAvailableVouchers(List<VoucherDTO> vouchers) {
        if (vouchers.size() > 0) {
            System.out.println("이용 가능한 할인권의 정보는 다음과 같습니다.");
            printAllVouchersFormatted(vouchers);
        }
        else {
            System.out.println("현재 이용 가능한 할인권이 없습니다.");
        }
    }

    public static void printAllVouchers(List<VoucherDTO> vouchers) {
        if (vouchers.size() > 0) {
            System.out.println("현재 저장된 모든 할인권의 정보는 다음과 같습니다.");
            printAllVouchersFormatted(vouchers);
        }
        else {
            System.out.println("현재 저장된 할인권이 없습니다.");
        }
    }

    private static void printAllVouchersFormatted(List<VoucherDTO> vouchers) {
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

    public static void printDeletedVoucher(VoucherDTO voucherDTO) {
        System.out.println("할인권이 삭제되었습니다.");
        System.out.println("삭제된 할인권: " + "[할인권 ID: " + voucherDTO.getVoucherId() + "]");
    }

}
