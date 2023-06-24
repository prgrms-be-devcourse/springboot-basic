package com.programmers.voucher.domain;

public enum Type {
    CREATE("create"),
    LIST("list"),
    BLACK("black"),
    EXIT("exit");

    private final String string;

    Type(String string) {
        this.string = string;
    }

    public static Type validateInput(String stringType) {
        stringType = lowerStringType(stringType);
        if (CREATE.string.equals(stringType)) {
            return CREATE;
        }
        if (LIST.string.equals(stringType)) {
            return LIST;
        }
        if (BLACK.string.equals(stringType)) {
            return BLACK;
        }
        if (EXIT.string.equals(stringType)) {
            return EXIT;
        }
        throw new IllegalArgumentException("지원하지 않는 type 입니다. 다시 확인 부탁드립니다.");
    }

    private static String lowerStringType(String stringType) {
        stringType = stringType.toLowerCase();
        return stringType;
    }
}
