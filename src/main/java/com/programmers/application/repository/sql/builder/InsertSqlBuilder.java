package com.programmers.application.repository.sql.builder;

public class InsertSqlBuilder {
    private StringBuilder sqlBuilder;

    public InsertSqlBuilder() {
        sqlBuilder = new StringBuilder();
    }

    public InsertSqlBuilder insertInto(String tableName) {
        sqlBuilder.append("INSERT INTO ").append(tableName);
        return this;
    }

    public InsertSqlBuilder columns(String columns) {
        sqlBuilder.append(" (").append(columns).append(")");
        return this;
    }

    public InsertSqlBuilder values(String values) {
        sqlBuilder.append(" VALUES (").append(values).append(")");
        return this;
    }

   public String build() {
        return sqlBuilder.toString();
    }
}
