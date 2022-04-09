package org.prgms.voucheradmin.domain.customer.dto;

/**
 * Customerservice에서 Console로 블랙리스트 고객 정보를 전달하기 위한 클래스입니다.
 */
public class CustomerDto {
    private Long id;
    private String name;

    public CustomerDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
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
