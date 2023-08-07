package org.prgrms.kdtspringdemo.util.queryBuilder.query;

import org.prgrms.kdtspringdemo.util.queryBuilder.constant.Column;
import org.prgrms.kdtspringdemo.util.queryBuilder.constant.Table;

import static org.prgrms.kdtspringdemo.util.queryBuilder.constant.Symbol.BLANK;
import static org.prgrms.kdtspringdemo.util.queryBuilder.constant.Symbol.COLON;
import static org.prgrms.kdtspringdemo.util.queryBuilder.constant.Symbol.COMMA;
import static org.prgrms.kdtspringdemo.util.queryBuilder.constant.Symbol.EQUAL;

public class UpdateBuilder {
    private static final String UPDATE = "UPDATE";
    private static final String SET = "SET";

    private StringBuilder stringBuilder;

    public UpdateBuilder() {
        this.stringBuilder = new StringBuilder();
    }

    public UpdateBuilder update(Table table) {
        stringBuilder.append(UPDATE)
                .append(BLANK.getValue())
                .append(table.getTable());

        return this;
    }

    public UpdateBuilder set(Column... columns) {
        stringBuilder.append(BLANK.getValue())
                .append(SET)
                .append(BLANK.getValue());

        for(Column column : columns){
            stringBuilder.append(column.getColumn())
                    .append(BLANK.getValue())
                    .append(EQUAL.getValue())
                    .append(BLANK.getValue())
                    .append(COLON.getValue())
                    .append(column.getColumn())
                    .append(COMMA.getValue());
        }

        stringBuilder.deleteCharAt(stringBuilder.length() - 1);

        return this;
    }

    public UpdateBuilder where(WhereBuilder whereBuilder) {
        stringBuilder.append(whereBuilder.getStringBuilder());

        return this;
    }

    public UpdateBuilder build() {
        return this;
    }

    public String toString() {
        return stringBuilder.toString();
    }
}
