package org.programmers.springbootbasic.customer.domain;

import org.programmers.springbootbasic.voucher.domain.Voucher;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//TODO: PR 포인트: guest 주문 등을 Customer 자식 클래스로 삼을 지 고민, 개인적으로는 CUSTOMER 정보가 null 인 ORDER 기반 조회가 낫지 않나 싶었음, 요구사항에 대해 고민됨
public class AbstractCustomer implements Customer {

    public AbstractCustomer(String name, String email, LocalDateTime signedUpAt) {
        this.name = name;
        this.email = email;
        this.signedUpAt = signedUpAt;
    }

    private Long customerId;
    private final String name;
    private final String email;
    private LocalDateTime lastLoginAt;
    private final LocalDateTime signedUpAt;
    private final List<Voucher> vouchers = new ArrayList<>();

}