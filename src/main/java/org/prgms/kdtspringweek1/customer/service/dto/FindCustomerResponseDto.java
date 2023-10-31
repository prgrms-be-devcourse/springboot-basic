package org.prgms.kdtspringweek1.customer.service.dto;

import org.prgms.kdtspringweek1.customer.entity.Customer;

import java.text.MessageFormat;
import java.util.UUID;

public class FindCustomerResponseDto {
    private final UUID customerId;
    private final String name;
    private boolean isBlackCustomer;

    public FindCustomerResponseDto(Customer customer) {
        this.customerId = customer.getCustomerId();
        this.name = customer.getName();
        this.isBlackCustomer = customer.getIsBlackCustomer();
    }

    // 기존에 Customer 클래스에서 출력 함수를 구현하였으나,
    // 뷰에 논리적으로 의존하게 되어 dto를 통해 구현
    public void printCustomerInfo() {
        System.out.println("--------------------------------------------------");
        System.out.println(MessageFormat.format("Customer Id: {0}", customerId));
        System.out.println(MessageFormat.format("Name: {0}", name));
        System.out.println(MessageFormat.format("IsBlackCustomer: {0}", isBlackCustomer));
    }
}
