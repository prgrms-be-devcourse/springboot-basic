package org.prgrms.kdtspringdemo.util.queryBuilder.query;

import org.prgrms.kdtspringdemo.util.queryBuilder.constant.Column;
import org.prgrms.kdtspringdemo.util.queryBuilder.constant.Table;

import static org.prgrms.kdtspringdemo.util.queryBuilder.constant.Symbol.BLANK;
import static org.prgrms.kdtspringdemo.util.queryBuilder.constant.Symbol.COMMA;

public class SelectBuilder {
    private static final String SELECT = "SELECT";
    private static final String FROM = "FROM";

    private StringBuilder stringBuilder;

    public SelectBuilder() {
        this.stringBuilder = new StringBuilder();
    }

    public SelectBuilder select(Column... columns) {
        stringBuilder.append(SELECT)
                .append(BLANK.getValue());

        for (Column column : columns) {
            stringBuilder.append(column.getColumn())
                    .append(COMMA.getValue());
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);

        return this;
    }

    public SelectBuilder from(Table table) {
        stringBuilder.append(BLANK.getValue())
                .append(FROM)
                .append(BLANK.getValue())
                .append(table.getTable());

        return this;
    }

    public SelectBuilder where(WhereBuilder whereBuilder) {
        stringBuilder.append(whereBuilder.getStringBuilder());

        return this;
    }

    public SelectBuilder build() {
        return this;
    }

    public String toString() {
        return stringBuilder.toString();
    }
}
