package org.promgrammers.voucher.domain.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.promgrammers.voucher.domain.Customer;
import org.promgrammers.voucher.domain.CustomerType;

import java.util.UUID;

@Getter
@ToString
@AllArgsConstructor
public class CustomerResponseDto {
    private UUID id;
    private String username;
    private CustomerType customerType;

    public CustomerResponseDto(Customer customer) {
        this.id = customer.getId();
        this.username = customer.getUsername();
        this.customerType = customer.getCustomerType();
    }

}
