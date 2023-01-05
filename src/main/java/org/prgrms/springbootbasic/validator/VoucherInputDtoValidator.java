package org.prgrms.springbootbasic.validator;

import org.prgrms.springbootbasic.dto.VoucherInputDto;
import org.prgrms.springbootbasic.exception.InValidVoucherDateException;
import org.prgrms.springbootbasic.exception.NotValidVoucherTypeException;
import org.prgrms.springbootbasic.type.VoucherType;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;


@Component
public class VoucherInputDtoValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return VoucherInputDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        VoucherInputDto voucherInputDto = (VoucherInputDto) target;

        if (!VoucherType.isValidType(voucherInputDto.getVoucherType())) {
            throw new NotValidVoucherTypeException();
        }

        LocalDate createdAt = LocalDate.parse(voucherInputDto.getCreatedAt());
        LocalDate endedAt = LocalDate.parse(voucherInputDto.getEndedAt());
        if (createdAt.isAfter(endedAt)) {
            throw new InValidVoucherDateException();
        }

    }
}
