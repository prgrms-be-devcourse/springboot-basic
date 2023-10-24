package com.programmers.vouchermanagement.domain.customer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
@ToString
public class Customer {
    private UUID id;
    private final String name;
    private final boolean blacklisted;

    public Customer(String name, boolean blacklisted) {
        this.name = name;
        this.blacklisted = blacklisted;
    }

    private Customer(UUID id, String name, boolean blacklisted) {
        this.id = id;
        this.name = name;
        this.blacklisted = blacklisted;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public static Customer parseCsvLine(String header, String line) {
        String[] headers = header.split(",");
        String[] values = line.split(",");

        Map<String, String> info = IntStream.range(0, headers.length)
                .boxed()
                .collect(Collectors.toMap(i -> headers[i], i -> values[i]));

        return new Customer(UUID.fromString(info.get("id")), info.get("name"), Boolean.parseBoolean(info.get("blacklisted")));
    }

    public static Customer fixture() {
        return new Customer("customer", false);
    }

    public static Customer fixture(String name, boolean blacklisted) {
        return new Customer(name, blacklisted);
    }
}
