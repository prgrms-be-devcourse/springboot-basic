package org.prgrms.kdtspringdemo.customer.domain.dto;

public class CustomerRequestDto {
    private String name;
    private boolean isBlack;

    public CustomerRequestDto(String name, boolean isBlack) {
        this.name = name;
        this.isBlack = isBlack;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBlack() {
        return isBlack;
    }

    public void setBlack(boolean black) {
        isBlack = black;
    }
}
