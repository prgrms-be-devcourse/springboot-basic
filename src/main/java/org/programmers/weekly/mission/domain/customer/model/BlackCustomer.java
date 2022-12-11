package org.programmers.weekly.mission.domain.customer.model;

public class BlackCustomer {
    private final int customerId;
    private final String name;

    public BlackCustomer(int customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    @Override
    public String toString() {
        return "BlackCustomer{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                '}';
    }
}
