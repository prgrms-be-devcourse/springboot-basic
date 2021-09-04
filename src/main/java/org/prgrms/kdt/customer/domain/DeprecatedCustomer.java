package org.prgrms.kdt.customer.domain;

import java.util.UUID;

public class DeprecatedCustomer {
    private UUID uuid;
    private String name;
    private boolean isBanned;

    public DeprecatedCustomer(String[] customerInfo) {
        this.uuid = UUID.randomUUID();
        this.name = customerInfo[0];
        this.isBanned = Boolean.parseBoolean(customerInfo[1]);
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public boolean isBanned() { return isBanned; }
    public String toString() { return "회원 번호 : " + this.getUuid() + " | " +
            " 회원 이름 : " + this.getName() + " | " +
            " 회원 분류 : " + (this.isBanned ? "black customer" : "customer");
    }
}
