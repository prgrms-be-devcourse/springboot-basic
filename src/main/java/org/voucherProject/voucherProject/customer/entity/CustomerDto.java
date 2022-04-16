package org.voucherProject.voucherProject.customer.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class CustomerDto {

    private UUID customerId;

    private String customerName;

    private String customerEmail;

    private String password;

    @Singular
    private List<UUID> vouchers = new ArrayList<>();

}
