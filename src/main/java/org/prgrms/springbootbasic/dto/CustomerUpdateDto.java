package org.prgrms.springbootbasic.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
public class CustomerUpdateDto {
    @Setter
    private String customerId;

    @Length(min = 2, max = 28)
    @NotBlank
    private final String name;

    @Email
    @Length(min = 2, max = 50)
    @NotBlank
    private final String email;

    private final LocalDateTime createdAt;

    private final LocalDateTime lastLoginAt;

    public CustomerUpdateDto(String customerId, String name, String email, LocalDateTime createdAt, LocalDateTime lastLoginAt) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.lastLoginAt = lastLoginAt;
    }
}
