package com.example.voucher.repository;

public class QueryBuilder {

    private StringBuilder query;

    public QueryBuilder(StringBuilder query) {
        this.query = query;
    }

    public String build() {
        return query.toString();
    }

}
