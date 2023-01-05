package org.prgrms.springbootbasic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoucherInputDto {
    @NotBlank
    private String voucherType;

    @Min(0)
    @NotNull
    private Long discountQuantity;

    @Range(min = 0, max = 100)
    @NotNull
    private Long discountRatio;

    @Min(0)
    @NotNull
    private Long voucherCount;

    @NotBlank
    private String createdAt;

    @NotBlank
    private String endedAt;
}
