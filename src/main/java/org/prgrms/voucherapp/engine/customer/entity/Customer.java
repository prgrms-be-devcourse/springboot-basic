package org.prgrms.voucherapp.engine.customer.entity;

import com.mysql.cj.exceptions.WrongArgumentException;
import lombok.Getter;
import org.prgrms.voucherapp.exception.WrongCustomerStatusException;
import org.prgrms.voucherapp.exception.WrongInputException;
import org.prgrms.voucherapp.exception.WrongParameterException;
import org.prgrms.voucherapp.global.enums.CustomerStatus;
import org.prgrms.voucherapp.global.enums.Regex;
import org.prgrms.voucherapp.global.enums.VoucherType;

import java.text.MessageFormat;
import java.util.UUID;
import java.util.regex.Pattern;

@Getter
public class Customer {
    private final UUID customerId;
    private String name;
    private final String email;
    private String status=null;

    public Customer(UUID customerId, String name, String email) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
    }

    public Customer(UUID customerId, String name, String email, String status) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.status = status;
    }

    public void changeName(String name) {
        if(!Pattern.matches(Regex.CUSTOMER_NAME.get(), name)) throw new WrongParameterException("올바르지 않은 고객 이름입니다.");
        this.name = name;
    }

    public void changeStatus(String status) {
        this.status = CustomerStatus.getStatus(status).isEmpty() ? null : status;
    }

    @Override
    public String toString() {
        return String.format("ID : %36s, STATUS : %-10s, NAME : %-20s, EMAIL : %-50s", customerId, status, name, email);
    }
}
