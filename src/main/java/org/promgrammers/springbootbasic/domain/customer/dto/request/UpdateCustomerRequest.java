package org.promgrammers.springbootbasic.domain.customer.dto.request;

import org.promgrammers.springbootbasic.domain.customer.model.CustomerType;

import java.util.UUID;

public record UpdateCustomerRequest(UUID customerId, String username, CustomerType customerType) {

}
