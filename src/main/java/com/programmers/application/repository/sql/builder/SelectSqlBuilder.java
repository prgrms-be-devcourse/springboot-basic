package com.programmers.application.repository.sql.builder;

import java.util.Objects;

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
        if (Objects.isNull(condition) || condition.isBlank()) {
            return this;
        }
        sqlBuilder.append(" WHERE ").append(condition);
        return this;
    }

    public SelectSqlBuilder and(String condition) {
        if (Objects.isNull(condition) || condition.isBlank()) {
            return this;
        }
        sqlBuilder.append(" AND ").append(condition);
        return this;
    }

    public SelectSqlBuilder or(String condition) {
        if (Objects.isNull(condition) || condition.isBlank()) {
            return this;
        }
        sqlBuilder.append(" OR ").append(condition);
        return this;
    }

    public String build() {
        return sqlBuilder.toString();
    }
}
