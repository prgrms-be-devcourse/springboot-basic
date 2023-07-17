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

    public QueryBuilder AND(String colum, String operator, String param) {
        makeCondition("AND", colum, operator, param);

        return this;
    }

    public QueryBuilder OR(String colum, String operator, String param) {
        makeCondition("OR", colum, operator, param);

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
            .append(" ")
            .append(operator)
            .append(" ")
            .append(":")
            .append(param);
    }

}
