package com.example.voucher.query.operator;

public class Lt extends Operator {

    private static final String LT = ">=";

    public Lt(String column, Object value) {
        super(column, LT, value);
    }

}