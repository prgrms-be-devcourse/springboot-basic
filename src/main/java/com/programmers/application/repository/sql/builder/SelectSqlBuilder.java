package com.programmers.application.repository.sql.builder;

public class SelectSqlBuilder {
    private StringBuilder sqlBuilder;

    public SelectSqlBuilder() {
        sqlBuilder = new StringBuilder();
    }

    public SelectSqlBuilder select(String columns) {
        sqlBuilder.append("SELECT ").append(columns);
        return this;
    }

    public SelectSqlBuilder from(String tableName) {
        sqlBuilder.append(" FROM ").append(tableName);
        return this;
    }

    public SelectSqlBuilder where(String condition) {
        sqlBuilder.append(" WHERE ").append(condition);
        return this;
    }

    public SelectSqlBuilder and(String condition) {
        sqlBuilder.append(" AND ").append(condition);
        return this;
    }

    public SelectSqlBuilder or(String condition) {
        sqlBuilder.append(" OR ").append(condition);
        return this;
    }

    public String build() {
        return sqlBuilder.toString();
    }
}
