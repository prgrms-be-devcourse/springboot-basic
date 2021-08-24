package com.org.prgrms.mission.model;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Data
@ToString
public class Customer {

    @CsvBindByPosition(position = 0)
    private final UUID customerID;
    @CsvBindByPosition(position = 1)
    private final String name;
    @CsvBindByPosition(position = 2)
    private final int age;
    @CsvBindByPosition(position = 3)
    private ConsumerType consumerType;

    public Customer(UUID customerID, String name, int age, ConsumerType consumerType) {
        this.customerID = customerID;
        this.name = name;
        this.age = age;
        this.consumerType = consumerType;
    }
}
