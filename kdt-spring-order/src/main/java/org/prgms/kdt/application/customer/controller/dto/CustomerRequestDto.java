package org.prgms.kdt.application.customer.controller.dto;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.prgms.kdt.application.customer.domain.Customer;

@Getter @Setter
public class CustomerRequestDto {
    String name;
    String email;

    public Customer getCustomer() {
        return new Customer(UUID.randomUUID(), name, email,
            LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    }
}
