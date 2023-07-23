package prgms.spring_week1.domain.util.sqlBuilder.actionBuilder;

import prgms.spring_week1.domain.util.sqlBuilder.tableBuilder.FromBuilder;

import static prgms.spring_week1.domain.util.sqlBuilder.LastIndexLength.INVALID_LAST_COMMA;

public class SelectBuilder {
    private StringBuilder selectSqlBuilder = new StringBuilder();

    public SelectBuilder select(String... column) {
        selectSqlBuilder.append("SELECT ");

        for (String columnValue : column) {
            selectSqlBuilder.append(columnValue + ",");
        }

        selectSqlBuilder.setLength(selectSqlBuilder.length() - INVALID_LAST_COMMA);

        return this;
    }

    public SelectBuilder selectAll() {
        selectSqlBuilder.append("SELECT *");
        return this;
    }

    public FromBuilder from(String table) {
        return new FromBuilder(selectSqlBuilder.append(" FROM ").append(table));
    }

    public String build() {
        return selectSqlBuilder.toString();
    }
}

