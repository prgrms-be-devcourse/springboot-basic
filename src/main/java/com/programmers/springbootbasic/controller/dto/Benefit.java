package com.programmers.springbootbasic.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class Benefit {

    private int type;
    private Long fixedAmount;
    private Integer percentDiscount;

    public int getType() {
        return type;
    }

    public Optional<Long> getFixedAmount() {
        return Optional.ofNullable(fixedAmount);
    }

    public Optional<Integer> getPercentDiscount() {
        return Optional.ofNullable(percentDiscount);
    }

}
