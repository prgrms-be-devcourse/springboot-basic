package org.prgrms.springbootbasic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerInputDto {

    @Length(min = 2, max = 28)
    @NotBlank
    private String name;

    @Email
    @Length(min = 2, max = 50)
    @NotBlank
    private String email;
}
