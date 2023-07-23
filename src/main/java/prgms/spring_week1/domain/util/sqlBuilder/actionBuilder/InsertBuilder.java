package prgms.spring_week1.domain.util.sqlBuilder.actionBuilder;

import prgms.spring_week1.domain.util.sqlBuilder.tableBuilder.ColumnBuilder;
import prgms.spring_week1.domain.util.type.TableType;

import static prgms.spring_week1.domain.util.sqlBuilder.LastIndexLength.INVALID_LAST_COMMA;

public class InsertBuilder {
    private StringBuilder insertSqlBuilder = new StringBuilder();

    public InsertBuilder insert(TableType table) {
        insertSqlBuilder.append("INSERT INTO ").append(table);
        return this;
    }

    public ColumnBuilder columns(String... columns) {
        insertSqlBuilder.append("(");

        for (String column : columns) {
            insertSqlBuilder.append(column + ",");
        }

        insertSqlBuilder.setLength(insertSqlBuilder.length() - INVALID_LAST_COMMA);
        insertSqlBuilder.append(")");

        return new ColumnBuilder(insertSqlBuilder);
    }

    public String build(){
        return insertSqlBuilder.toString();
    }
}
