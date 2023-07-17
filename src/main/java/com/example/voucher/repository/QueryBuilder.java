package com.example.voucher.repository;

public class QueryBuilder {

    private StringBuilder query;

    public QueryBuilder() {
        this.query = new StringBuilder();
    }

    public QueryBuilder delete(String table) {
        query.append("DELETE FROM")
            .append(" ")
            .append(table);

        return this;
    }

    public QueryBuilder insertInto(String table) {
        query.append("INSERT INTO")
            .append(" ")
            .append(table);

        return this;
    }

    public QueryBuilder values(String... params) {
        query.append(" ")
            .append("VALUES")
            .append(" ")
            .append("(");

        for (String param : params) {
            query.append(":")
                .append(param)
                .append(", ");
        }

        query.delete(query.length() - 2, query.length());
        query.append(")");

        return this;
    }

    public QueryBuilder where(String colum, String operator, String param) {
        makeCondition("WHERE", colum, operator, param);

        return this;
    }

    public QueryBuilder and(String colum, String operator, String param) {
        makeCondition("AND", colum, operator, param);

        return this;
    }

    public QueryBuilder or(String colum, String operator, String param) {
        makeCondition("OR", colum, operator, param);

        return this;
    }

    public QueryBuilder select(String... colums) {
        query.append("SELECT")
            .append(" ");

        for (String colum : colums) {
            query.append(colum)
                .append(", ");
        }

        query.delete(query.length() - 2, query.length());

        return this;
    }

    public QueryBuilder from(String table) {
        query.append(" ")
            .append("FROM")
            .append(" ")
            .append(table);

        return this;
    }

    public QueryBuilder update(String table) {
        query.append("UPDATE")
            .append(" ")
            .append(table);

        return this;
    }

    public QueryBuilder set(String colum, String param) {
        query.append(" ")
            .append("SET")
            .append(" ")
            .append(colum)
            .append("=")
            .append(":")
            .append(param);

        return this;
    }

    public QueryBuilder addSet(String colum, String param) {
        query.append(",")
            .append(" ")
            .append(colum)
            .append("=")
            .append(":")
            .append(param);

        return this;
    }

    public String build() {
        return query.toString();
    }

    private void makeCondition(String condition, String colum, String operator, String param) {
        query.append(" ")
            .append(condition)
            .append(" ")
            .append(colum)
            .append(operator)
            .append(":")
            .append(param);
    }

}
