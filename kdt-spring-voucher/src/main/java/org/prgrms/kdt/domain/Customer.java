package org.prgrms.kdt.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class Customer {

    private final UUID customerId;
    private String name;
    private String phoneNumber;
    private String address;

    @Override
    public String toString() {
        String s = customerId+","+name+","+phoneNumber+","+address;
        return s;
    }



}
