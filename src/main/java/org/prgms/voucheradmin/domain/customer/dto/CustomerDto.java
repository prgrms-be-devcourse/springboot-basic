package org.prgms.voucheradmin.domain.customer.dto;

import java.util.UUID;

/**
 * Customerservice에서 Console로 블랙리스트 고객 정보를 전달하기 위한 클래스입니다.
 */
public class CustomerDto {
    private UUID id;
    private String name;

    public CustomerDto(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return id+"\t"+name;
    }
}
