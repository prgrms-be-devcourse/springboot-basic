package org.programmers.springboot.basic.domain.customer.dto;

import lombok.Builder;
import lombok.Getter;
import org.programmers.springboot.basic.domain.customer.entity.CustomerType;
import org.programmers.springboot.basic.domain.customer.entity.vo.Email;

import java.util.UUID;

@Builder
@Getter
public class CustomerResponseDto {

    private final UUID customerId;
    private final String name;
    private final Email email;
    private final CustomerType customerType;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public String printCustomerInfo() {
        return String.format("""
                customerId: %s
                email: %s
                name: %s
                customerType: %s
                ---------------------------------
                """, customerId.toString(), email, name, customerType);
    }
}
