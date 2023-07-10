package com.programmers.application.repository.sql.builder;

public class UpdateSqlBuilder {
    private StringBuilder sqlBuilder;

    public UpdateSqlBuilder() {
        this.sqlBuilder = new StringBuilder();
    }

    public UpdateSqlBuilder update(String tableName) {
        sqlBuilder.append("UPDATE ").append(tableName);
        return this;
    }

    public UpdateSqlBuilder set(String column) {
        sqlBuilder.append(" SET ").append(column);
        return this;
    }

    public UpdateSqlBuilder where(String condition) {
        sqlBuilder.append(" WHERE ").append(condition);
        return this;
    }

    public String build() {
        return sqlBuilder.toString();
    }
}
