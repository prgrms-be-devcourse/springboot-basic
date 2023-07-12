package com.programmers.customer.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record CustomerUpdateRequest(UUID customerId, String name, LocalDateTime modifiedAt){
}
