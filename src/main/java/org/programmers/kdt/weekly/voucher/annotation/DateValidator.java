package org.programmers.kdt.weekly.voucher.annotation;

import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateValidator implements ConstraintValidator<DateCheck, LocalDate> {

	@Override
	public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {

		return localDate.isBefore(LocalDate.now()) || localDate.isEqual(LocalDate.now());
	}
}
