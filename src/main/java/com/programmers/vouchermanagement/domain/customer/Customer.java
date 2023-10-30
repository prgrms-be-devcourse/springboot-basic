package com.programmers.vouchermanagement.domain.customer;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
@ToString
@EqualsAndHashCode
public class Customer {
    private UUID id;
    private String email;
    private boolean blacklisted;

    public Customer(String email) {
        validateEmail(email);
        this.email = email;
    }

    public Customer(String email, boolean blacklisted) {
        validateEmail(email);

        this.email = email;
        this.blacklisted = blacklisted;
    }

    public Customer(UUID id, String email, boolean blacklisted) {
        validateEmail(email);

        this.id = id;
        this.email = email;
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

        return new Customer(UUID.fromString(info.get("id")), info.get("email"), Boolean.parseBoolean(info.get("blacklisted")));
    }

    private void validateEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";

        if (!Pattern.matches(regex, email)) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }
}
