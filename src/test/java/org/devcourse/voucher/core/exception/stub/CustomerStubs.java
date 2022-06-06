package org.devcourse.voucher.core.exception.stub;

import org.devcourse.voucher.application.customer.controller.dto.CustomerRequest;
import org.devcourse.voucher.application.customer.controller.dto.CustomerResponse;
import org.devcourse.voucher.application.customer.model.Customer;
import org.devcourse.voucher.application.customer.model.Email;

import java.util.List;
import java.util.UUID;

public class CustomerStubs {

    private CustomerStubs() {}


    public static List<Customer> customerList() {
        return List.of(
                new Customer(UUID.randomUUID(), "test1", new Email("test1@test.com")),
                new Customer(UUID.randomUUID(), "test2", new Email("test2@test.com")),
                new Customer(UUID.randomUUID(), "test3", new Email("test3@test.com")),
                new Customer(UUID.randomUUID(), "test4", new Email("test4@test.com")),
                new Customer(UUID.randomUUID(), "test5", new Email("test5@test.com"))
        );
    }

    public static List<CustomerResponse> customerResponseList(List<Customer> ret) {
        return List.of(
                new CustomerResponse(ret.get(0).getCustomerId(), "test1", "test1@test.com"),
                new CustomerResponse(ret.get(1).getCustomerId(), "test2", "test2@test.com"),
                new CustomerResponse(ret.get(2).getCustomerId(), "test3", "test3@test.com"),
                new CustomerResponse(ret.get(3).getCustomerId(), "test4", "test4@test.com"),
                new CustomerResponse(ret.get(4).getCustomerId(), "test5", "test5@test.com")
        );
    }

    public static List<CustomerResponse> customerResponseList() {
        return List.of(
                new CustomerResponse(UUID.randomUUID(), "test1", "test1@test.com"),
                new CustomerResponse(UUID.randomUUID(), "test2", "test2@test.com"),
                new CustomerResponse(UUID.randomUUID(), "test3", "test3@test.com"),
                new CustomerResponse(UUID.randomUUID(), "test4", "test4@test.com"),
                new CustomerResponse(UUID.randomUUID(), "test5", "test5@test.com")
        );
    }

    public static Customer customer(UUID customerId) {
        return new Customer(customerId, "test", new Email("test@test.com"));
    }

    public static CustomerResponse customerResponse(UUID customerId) {
        return new CustomerResponse(customerId, "test", "test@test.com");
    }

    public static CustomerResponse customerResponse(UUID customerId, String name, Email email) {
        return new CustomerResponse(customerId, name, email.getAddress());

    }

    public static CustomerRequest customerRequest() {
        return new CustomerRequest("test", "test@test.com");
    }
}
