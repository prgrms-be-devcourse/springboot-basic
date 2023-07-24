package org.prgrms.kdt.model.entity;

import java.time.LocalDateTime;

public record CustomerEntity(Long customerId, String name, String email, LocalDateTime createdAt) {

}
