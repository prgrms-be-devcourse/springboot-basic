package org.prgrms.kdt.domain.customer;

import java.text.MessageFormat;
import java.util.UUID;

public class BadCustomer implements Customer{
    private final UUID customerId;
    private String name;
    private final int age;
    private boolean isBlacklisted = false;

    public BadCustomer(UUID customerId, String name, int age) {
        this.customerId = UUID.randomUUID();
        this.name = name;
        this.age = age;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public void getName(String name) {
        this.name = name;
    }

    public void setBlacklisted(boolean blacklisted) {
        this.isBlacklisted = blacklisted;
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0},\" {1}\",\" {2}", customerId, name, age);
    }

}
