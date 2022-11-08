package org.prgrms.kdtspringdemo.customer;

import org.prgrms.kdtspringdemo.io.file.CsvDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private final String name;
    private final LocalDate dateOfBirth;

    public Customer(UUID customerId, String name, LocalDate dateOfBirth) {
        this.customerId = customerId;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    public CsvDto makeCsvDto(){
        List<String[]>  rowDatas = new ArrayList<>();
        String[] rowData = {customerId.toString(),name,dateOfBirth.toString()};
        rowDatas.add(rowData);
        return CsvDto.from(rowDatas);
    }
}
