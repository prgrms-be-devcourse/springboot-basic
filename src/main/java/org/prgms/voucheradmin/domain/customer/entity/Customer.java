package org.prgms.voucheradmin.domain.customer.entity;

import java.util.UUID;

/**
 * Customer entity 클래스입니다. 고객 id와 이름을 필드로 가집니다.
 */
public class Customer {
    private UUID id;
    private String name;

    public Customer(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
