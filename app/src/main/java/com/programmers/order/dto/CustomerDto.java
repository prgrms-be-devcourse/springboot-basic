package com.programmers.order.dto;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.programmers.order.domain.Customer;
import com.programmers.order.utils.TranslatorUtils;

public class CustomerDto {

	public static class SaveRequestDto {
		private static final String DEFAULT_VALUE = "";
		private static final Map<String, String> fields;
		private final String name;
		private final String email;

		static {
			fields = Arrays.stream(SaveRequestDto.class.getDeclaredFields())
					.collect(Collectors.toMap(Field::getName, field -> DEFAULT_VALUE));
		}

		public SaveRequestDto(List<String> inputs) {
			this.name = inputs.get(0);
			this.email = inputs.get(1);
		}

		public static Map<String, String> getFields() {
			return SaveRequestDto.fields;
		}

		public Customer toCustomer() {
			return new Customer(UUID.randomUUID(), this.name, this.email, LocalDateTime.now());
		}

	}

	public static class UpdateCustomer {
		private final String email;
		private final String nameToChange;

		public UpdateCustomer(String email, String nameToChange) {
			this.email = email;
			this.nameToChange = nameToChange;
		}

		public String getEmail() {
			return email;
		}

		public String getNameToChange() {
			return nameToChange;
		}
	}

	public static class RegisterVoucherDto {
		private final String email;
		private final UUID voucherId;

		public RegisterVoucherDto(List<String> inputs) {
			this.email = inputs.get(0);
			this.voucherId = UUID.fromString(inputs.get(1));
		}

		public String getEmail() {
			return email;
		}

		public UUID getVoucherId() {
			return voucherId;
		}
	}

	public static class ReponseDto {
		private final String email;
		private final String name;

		public ReponseDto(String email, String name) {
			this.email = email;
			this.name = name;
		}
	}

}
