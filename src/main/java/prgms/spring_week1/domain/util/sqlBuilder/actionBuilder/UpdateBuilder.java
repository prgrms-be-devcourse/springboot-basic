package prgms.spring_week1.domain.util.sqlBuilder.actionBuilder;

import prgms.spring_week1.domain.util.sqlBuilder.tableBuilder.SetBuilder;
import prgms.spring_week1.domain.util.type.TableType;

import static prgms.spring_week1.domain.util.sqlBuilder.LastIndexLength.INVALID_LAST_COMMA;

public class UpdateBuilder {
    private StringBuilder updateSqlBuilder = new StringBuilder();

    public UpdateBuilder update(TableType table) {
        updateSqlBuilder.append("UPDATE ").append(table);
        return this;
    }

    public SetBuilder set(String... column) {
        updateSqlBuilder.append(" SET ");

        for (String columnValue : column) {
            updateSqlBuilder.append(columnValue + ",");
        }

        updateSqlBuilder.setLength(updateSqlBuilder.length() - INVALID_LAST_COMMA);

        return new SetBuilder(updateSqlBuilder);
    }

    public String build() {
        return updateSqlBuilder.toString();
    }
}
