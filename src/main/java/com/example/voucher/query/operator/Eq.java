package com.example.voucher.query.operator;

public class Eq extends Operator {

    private static final String EQ = "=";

    public Eq(String column, Object value) {
        super(column, EQ, value);
    }

}
