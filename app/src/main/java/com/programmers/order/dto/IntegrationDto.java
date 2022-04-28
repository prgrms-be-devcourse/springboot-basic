package com.programmers.order.dto;

import java.util.UUID;

public class IntegrationDto {
	public static class SaveRequestDto {
		private final String email;
		private final UUID voucher_id;

		public SaveRequestDto(String email, UUID voucher_id) {
			this.email = email;
			this.voucher_id = voucher_id;
		}
	}
}
