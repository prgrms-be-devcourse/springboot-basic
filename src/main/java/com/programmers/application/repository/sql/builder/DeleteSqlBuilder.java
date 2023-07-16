package com.programmers.application.repository.sql.builder;

import java.util.Objects;

public class DeleteSqlBuilder {
    private StringBuilder sqlBuilder;

    public DeleteSqlBuilder() {
        this.sqlBuilder = new StringBuilder();
    }

    public DeleteSqlBuilder deleteFrom(String tableName) {
        sqlBuilder.append("DELETE FROM ").append(tableName);
        return this;
    }

    public DeleteSqlBuilder where(String condition) {
        if (Objects.isNull(condition) || condition.isBlank()) {
            return this;
        }
        sqlBuilder.append(" WHERE ").append(condition);
        return this;
    }

    public String build() {
        return sqlBuilder.toString();
    }
}
