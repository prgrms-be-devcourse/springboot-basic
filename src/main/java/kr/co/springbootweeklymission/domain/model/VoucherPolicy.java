package kr.co.springbootweeklymission.domain.model;

import kr.co.springbootweeklymission.global.error.exception.NotFoundException;
import kr.co.springbootweeklymission.global.error.model.ResponseStatus;
import kr.co.springbootweeklymission.global.view.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public enum VoucherPolicy {
    FIXED_DISCOUNT((price, amount) -> price - amount, 1, "고정 할인"),
    PERCENT_DISCOUNT((price, amount) -> price - price * amount / 100, 2, "퍼센트 할인");

    private final BinaryOperator<Integer> discount;
    private final int type;
    private final String policy;

    private static final Map<Integer, VoucherPolicy> types =
            Collections.unmodifiableMap(Arrays.stream(values())
                    .collect(Collectors.toMap(VoucherPolicy::getType, Function.identity())));

    public static VoucherPolicy from(int type) {
        return Optional.ofNullable(types.get(type))
                .orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_NOT_FOUND_VOUCHER_POLICY));
    }
}
