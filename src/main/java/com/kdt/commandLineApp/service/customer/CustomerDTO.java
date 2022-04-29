package com.kdt.commandLineApp.service.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomerDTO {
    private long id;
    private String name;
    private int age;
    private String sex;
}
