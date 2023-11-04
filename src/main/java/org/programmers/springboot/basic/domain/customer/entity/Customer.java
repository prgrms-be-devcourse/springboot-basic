package org.programmers.springboot.basic.domain.customer.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.programmers.springboot.basic.domain.customer.entity.vo.Email;
import org.programmers.springboot.basic.domain.customer.exception.BlackCustomerNotFoundException;
import org.programmers.springboot.basic.domain.customer.exception.DuplicateBlackCustomerException;

import java.util.UUID;

@Slf4j
@Getter
@AllArgsConstructor
public class Customer {

    private final UUID customerId;
    private final String name;
    private final Email email;
    private CustomerType customerType;

    public int getCustomerTypeValue() {
        return this.customerType.getValue();
    }

    public boolean equalType(CustomerType type) {
        return this.customerType.equals(type);
    }

    public void setBlack() {
        if (this.equalType(CustomerType.BLACK)) {
            log.warn("customer of customerId {} is already exists from blacklist", this.customerId);
            throw new DuplicateBlackCustomerException();
        }
        this.customerType = CustomerType.BLACK;
    }

    public void setNormal() {
        if (this.equalType(CustomerType.NORMAL)) {
            log.warn("customer of customerId {} is not exists from blacklist", this.customerId);
            throw new BlackCustomerNotFoundException();
        }
        this.customerType = CustomerType.NORMAL;
    }
}
