package org.prgrms.kdt.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public record CustomerDto(
    @NotBlank @Pattern(regexp = "[a-zA-Z]") @Size(min = 2, max = 20) String name,
    @NotBlank @Email @Size(max = 50) String email) {

}