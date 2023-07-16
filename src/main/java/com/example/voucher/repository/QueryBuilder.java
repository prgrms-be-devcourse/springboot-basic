package com.example.voucher.repository;

public class QueryBuilder {

    private StringBuilder query;

    public QueryBuilder() {
        this.query = new StringBuilder();
    }

    public QueryBuilder delete(String table) {
        query.append("DELETE FROM ")
            .append(table);

        return this;
    }

    public String build() {
        return query.toString();
    }

}
