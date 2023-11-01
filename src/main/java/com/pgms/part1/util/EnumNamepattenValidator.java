package com.pgms.part1.util;

import com.pgms.part1.exception.ErrorCode;
import com.pgms.part1.exception.VoucherApplicationException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class EnumNamepattenValidator implements ConstraintValidator<EnumNamePattern, Enum<?>> {

    private final Logger log = LoggerFactory.getLogger(EnumNamepattenValidator.class);
    private Pattern pattern;

    @Override
    public void initialize(EnumNamePattern constraintAnnotation) {
        log.info("validator >>>>>>>>>>>");
        try{
            pattern = Pattern.compile(constraintAnnotation.regexp());
        } catch (PatternSyntaxException e){
            throw new VoucherApplicationException(ErrorCode.INVALID_INPUT_DATA);
        }
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        if(value == null) {
            throw new VoucherApplicationException(ErrorCode.INVALID_INPUT_DATA);
        }
        Matcher matcher = pattern.matcher(value.name());
        return matcher.matches();
    }
}
