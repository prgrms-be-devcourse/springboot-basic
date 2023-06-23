package org.promgrammers.springbootbasic.domain;

import java.util.UUID;

public record Customer(UUID customerId, CustomerType customerType) {

}