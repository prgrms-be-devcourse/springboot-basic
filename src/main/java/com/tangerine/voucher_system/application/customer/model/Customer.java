package com.tangerine.voucher_system.application.customer.model;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

public record Customer(
        UUID customerId,
        Name name,
        boolean isBlack
) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return isBlack == customer.isBlack && Objects.equals(customerId, customer.customerId) && Objects.equals(name.getValue(), customer.name.getValue());
    }

    @Override
    public String toString() {
        return MessageFormat.format("Customer(id: {0}, name: {1}, isBlack: {2})", customerId, name.getValue(), isBlack);
    }

}
