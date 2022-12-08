package org.prgrms.springbootbasic.validator;

import org.prgrms.springbootbasic.dto.VoucherInputDto;
import org.prgrms.springbootbasic.type.VoucherType;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;


@Component
public class VoucherInputDtoViewValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return VoucherInputDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        VoucherInputDto voucherInputDto = (VoucherInputDto) target;
        boolean dateIsBlank = false;

        if (!VoucherType.isValidType(voucherInputDto.getVoucherType())) {
            errors.rejectValue("voucherType", "errors.voucher.type");
        }

        if (voucherInputDto.getCreatedAt() == null || voucherInputDto.getCreatedAt().isBlank()) {
            dateIsBlank = true;
        }

        if (voucherInputDto.getEndedAt() == null || voucherInputDto.getEndedAt().isBlank()) {
            dateIsBlank = true;
        }

        if (dateIsBlank) {
            return ;
        }

        LocalDate createdAt = LocalDate.parse(voucherInputDto.getCreatedAt());
        LocalDate endedAt = LocalDate.parse(voucherInputDto.getEndedAt());
        if (createdAt.isAfter(endedAt)) {
            errors.reject("errors.date", new Object[]{}, null);
        }
    }
}
