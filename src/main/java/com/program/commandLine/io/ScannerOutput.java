package com.program.commandLine.io;

import com.program.commandLine.model.customer.Customer;
import com.program.commandLine.model.voucher.Voucher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScannerOutput implements Output {

    static final String NEWLINE = System.lineSeparator();

    @Override
    public void menuView(MenuType menuType) {
        switch (menuType){
            case MAIN -> {
                System.out.println("=== Voucher Program ===" + NEWLINE +
                        "exit 프로그램 종료" + NEWLINE +
                        "customer 고객관리" + NEWLINE +
                        "voucher 바우처 서비스 이용" );
            }
            case CUSTOMER -> {
                System.out.println("=== Customer Management  ===" + NEWLINE +
                        "create 고객 추가" + NEWLINE +
                        "search 고객 검색" + NEWLINE +
                        "blacklist 블랙 리스트 고객 조회" + NEWLINE +
                        "delete 전체고객 삭제" );
            }
            case VOUCHER -> {
                System.out.println("=== Voucher Service ===" + NEWLINE +
                        "create 바우처 생성" + NEWLINE +
                        "allocate 바우처 할당" + NEWLINE +
                        "retrieve 바우처 회수"+ NEWLINE +
                        "list 고객별 바우처 조회"+ NEWLINE +
                        "customer 바우처 보유 고객 조회");
            }
        }

    }

    @Override
    public void messageView(String message) {
        System.out.println(message + "\n");
    }


    @Override
    public void allVoucherView(List<Voucher> vouchers) {
        vouchers.forEach(value -> {
            String voucher = value.getVoucherType().getString() + " voucher - " + value.getVoucherDiscount() ;
            System.out.println((vouchers.indexOf(value)+1) +". " +value.getVoucherId() + " : " + voucher);
        });
    }

    @Override
    public void customerBlackListView(List<Customer> blackList) {
        System.out.println("----------------- black list ------------------");
        blackList.forEach(customer -> {
            System.out.println(customer.getCustomerId() + " : " + customer.getName());
        });
        System.out.println("-----------------------------------------------");
    }

    @Override
    public void customerView(Customer customer) {
        System.out.println(customer.getName() +" : " +customer.getEmail() + " " + customer.getCustomerType() + " " +customer.getLastLoginAt());
        System.out.println();
    }
}
