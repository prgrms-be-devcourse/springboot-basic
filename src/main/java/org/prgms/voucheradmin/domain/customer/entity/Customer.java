package org.prgms.voucheradmin.domain.customer.entity;

/**
 * Customer entity 클래스입니다. 고객 id와 이름을 필드로 가집니다.
 */
public class Customer {
    private Long id;
    private String name;

    public Customer(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
