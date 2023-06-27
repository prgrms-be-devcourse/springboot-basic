package me.kimihiqq.vouchermanagement.dto;

public class CustomerDto {
    private String customerId;
    private String name;
    private String status;

    public CustomerDto(String customerId, String name, String status) {
        this.customerId = customerId;
        this.name = name;
        this.status = status;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }
}
