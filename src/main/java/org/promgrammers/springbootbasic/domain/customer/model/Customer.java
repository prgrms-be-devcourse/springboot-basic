package org.promgrammers.springbootbasic.domain.customer.model;

import java.util.UUID;

public record Customer(UUID customerId, CustomerType customerType) {

}