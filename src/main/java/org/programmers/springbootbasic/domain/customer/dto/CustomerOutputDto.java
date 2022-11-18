package org.programmers.springbootbasic.domain.customer.dto;

public record CustomerOutputDto(long id, String name) {

    @Override
    public String toString() {
        return "id : " + id + ", name : " + name;
    }
}
