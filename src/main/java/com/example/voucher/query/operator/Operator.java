package com.example.voucher.query.operator;

public class Operator {

    private final String column;
    private final String operate;
    private final String value;

    protected Operator(String column, String operate, Object value) {
        this.column = column;
        this.operate = operate;
        this.value = parseToString(value);
    }

    public String getColumn() {
        return column;
    }

    public String getOperate() {
        return operate;
    }

    public String getValue() {
        return value;
    }

    private String parseToString(Object value) {
        String result;

        if (value instanceof Integer) {
            result = Integer.toString((Integer)value);
        } else if (value instanceof Long) {
            result = Long.toString((Long)value);
        } else if (value instanceof String) {
            result = (String)value;
        } else {
            throw new IllegalArgumentException("지원하지 않는 인자 값 입니다.");
        }

        return result;
    }

}
