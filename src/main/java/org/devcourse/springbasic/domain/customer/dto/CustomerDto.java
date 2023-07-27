package org.devcourse.springbasic.domain.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.devcourse.springbasic.domain.customer.domain.Customer;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CustomerDto {

    private static final String ONLY_CHARACTER = "^[^\\d\\s!@#$%^&*(),.?\":{}|<>]+$";

    @AllArgsConstructor
    @Getter
    public static class SaveRequest {
        private final UUID customerId = UUID.randomUUID();
        @NotBlank(message = "이름을 입력하세요.")
        @Pattern(regexp = ONLY_CHARACTER, message = "이름은 문자로 구성.")
        private final String name;

        @NotBlank(message = "이메일을 입력하세요")
        @Email(message = "이메일 형식을 확인하세요.")
        private final String email;
        private final LocalDateTime createdAt = LocalDateTime.now();
    }

    @AllArgsConstructor
    @Getter
    public static class Request {
        private final String name;
        private final String email;
    }

    @AllArgsConstructor
    @Getter
    public static class UpdateRequest {
        private final UUID customerId;
        private final String name;
    }

    @AllArgsConstructor
    @Getter
    public static class Response {
        private final UUID customerId;
        private final String name;
        private final String email;
        private final LocalDateTime lastLoginAt;
        private final LocalDateTime createdAt;

        public static Response fromEntity(Customer customer) {
            return new Response(
                    customer.getCustomerId(),
                    customer.getName(),
                    customer.getEmail(),
                    customer.getLastLoginAt(),
                    customer.getCreatedAt());
        }

        public static List<Response> fromEntities(List<Customer> customers) {
            return customers.stream()
                    .map(Response::fromEntity)
                    .collect(Collectors.toList());
        }
    }
}