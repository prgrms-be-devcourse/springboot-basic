package org.prgrms.kdtspringdemo.util.queryBuilder.query;

import org.prgrms.kdtspringdemo.util.queryBuilder.constant.Column;
import org.prgrms.kdtspringdemo.util.queryBuilder.constant.Table;

import static org.prgrms.kdtspringdemo.util.queryBuilder.constant.Symbol.BLANK;
import static org.prgrms.kdtspringdemo.util.queryBuilder.constant.Symbol.COLON;
import static org.prgrms.kdtspringdemo.util.queryBuilder.constant.Symbol.COMMA;
import static org.prgrms.kdtspringdemo.util.queryBuilder.constant.Symbol.LEFT_BRACKET;
import static org.prgrms.kdtspringdemo.util.queryBuilder.constant.Symbol.RIGHT_BRACKET;

public class InsertBuilder {
    private static final String INSERT = "INSERT INTO";
    private static final String VALUES = "VALUES";
    private StringBuilder stringBuilder;

    public InsertBuilder() {
        this.stringBuilder = new StringBuilder();
    }

    public InsertBuilder insert(Table table) {
        stringBuilder.append(INSERT)
                .append(BLANK.getValue())
                .append(table.getTable());

        return this;
    }

    public InsertBuilder values(Column... columns) {
        stringBuilder.append(BLANK.getValue())
                .append(VALUES)
                .append(LEFT_BRACKET.getValue());

        for (Column column : columns) {
            stringBuilder.append(COLON.getValue())
                    .append(column.getColumn())
                    .append(COMMA.getValue());
        }

        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append(RIGHT_BRACKET.getValue());

        return this;
    }

    public InsertBuilder build() {
        return this;
    }

    public String toString() {
        return stringBuilder.toString();
    }
}
