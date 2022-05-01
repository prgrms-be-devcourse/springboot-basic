package org.prgrms.kdt.blacklist.domain;

public class Blacklist {
    private final String customerId;
    private final String name;

    public Blacklist(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    @Override
    public String toString() {
        return "ID : " + customerId + " / name : " + name;
    }

}
