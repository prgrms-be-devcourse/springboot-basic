package com.example.voucher.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import javax.validation.constraints.NotEmpty;


public class CustomerRequest {
	@NotEmpty(message = "이름은 필수 입력입니다.")
	private String name;

	@JsonCreator
	public CustomerRequest(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
