package com.programmers.order.controller.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;

public class CustomerDto {

	@Builder
	public record Create(
			String email,
			String name
	) {

	}

	@Builder
	public record Response(
			UUID customerId,
			String email,
			String name,
			LocalDateTime createdAt,
			LocalDateTime updatedAt
	) {

	}
}
