package org.prgrms.kdtspringdemo.util.queryBuilder.query;

import org.prgrms.kdtspringdemo.util.queryBuilder.constant.Column;

import static org.prgrms.kdtspringdemo.util.queryBuilder.constant.Symbol.BLANK;
import static org.prgrms.kdtspringdemo.util.queryBuilder.constant.Symbol.COLON;
import static org.prgrms.kdtspringdemo.util.queryBuilder.constant.Symbol.EQUAL;

public class WhereBuilder {
    private static final String WHERE = "WHERE";
    private static final String AND = "AND";
    private static final String OR = "OR";

    private StringBuilder stringBuilder;

    public WhereBuilder() {
        this.stringBuilder = new StringBuilder();
    }

    public StringBuilder getStringBuilder() {
        return stringBuilder;
    }

    public WhereBuilder equal(Column column) {
        stringBuilder.append(BLANK.getValue())
                .append(WHERE)
                .append(BLANK.getValue())
                .append(column.getColumn())
                .append(BLANK.getValue())
                .append(EQUAL.getValue())
                .append(BLANK.getValue())
                .append(COLON.getValue())
                .append(column.getColumn());

        return this;
    }

    public WhereBuilder and() {
        stringBuilder.append(BLANK.getValue())
                .append(AND)
                .append(BLANK.getValue());

        return this;
    }

    public WhereBuilder or() {
        stringBuilder.append(BLANK.getValue())
                .append(OR)
                .append(BLANK.getValue());

        return this;
    }

    public WhereBuilder build() {
        return this;
    }

    public String toString() {
        return stringBuilder.toString();
    }
}
