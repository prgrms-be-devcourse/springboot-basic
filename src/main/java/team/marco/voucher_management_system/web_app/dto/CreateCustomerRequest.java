package team.marco.voucher_management_system.web_app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateCustomerRequest(
        @NotBlank
        String name,
        @Email
        String email) {
}
