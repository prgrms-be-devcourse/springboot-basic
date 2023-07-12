package com.programmers.springbootbasic.customer.dto;

import java.util.UUID;

public record CustomerUpdateRequestDto(UUID id, String name) {

}
