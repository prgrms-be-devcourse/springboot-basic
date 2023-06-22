package kr.co.springbootweeklymission.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.BinaryOperator;

@AllArgsConstructor
@Getter
public enum VoucherPolicy {
    FIXED_DISCOUNT((price, amount) -> price - amount),
    PERCENT_DISCOUNT((price, amount) -> price - price * amount / 100);

    private final BinaryOperator<Integer> discount;
}
