package com.example.voucher.util;

public class QueryBuilder {

    private final int REMOVE_SIZE = 2;

    private StringBuilder query;

    public QueryBuilder() {
        this.query = new StringBuilder();
    }

    public DeleteBuilder delete(String table) {
        query.append("DELETE FROM")
            .append(" ")
            .append(table);

        return new DeleteBuilder(query);
    }

    public SelectBuilder select(String... colums) {
        query.append("SELECT")
            .append(" ");

        for (String colum : colums) {
            query.append(colum)
                .append(", ");
        }

        query.delete(query.length() - REMOVE_SIZE, query.length());

        return new SelectBuilder(query);
    }

    public SaveBuilder insertInto(String table) {
        query.append("INSERT INTO")
            .append(" ")
            .append(table);

        return new SaveBuilder(query);
    }

    public UpdateBuilder update(String table) {
        query.append("UPDATE")
            .append(" ")
            .append(table);

        return new UpdateBuilder(query);
    }

    public static class SaveBuilder {
        private StringBuilder query;

        public SaveBuilder(StringBuilder query) {
            this.query = query;
        }

        public SaveBuilder values(String... params) {
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

        public String build() {
            return query.toString();
        }

    }

    public static class SelectBuilder {
        private StringBuilder query;

        public SelectBuilder(StringBuilder query) {
            this.query = query;
        }

        public WhereBuilder from(String table) {
            query.append(" ")
                .append("FROM")
                .append(" ")
                .append(table);

            return new WhereBuilder(query);
        }

        public String build() {
            return query.toString();
        }
    }

    public static class DeleteBuilder {

        private StringBuilder query;

        public DeleteBuilder(StringBuilder query) {
            this.query = query;
        }

        public WhereBuilder where(String colum, String operator, String param) {
            query.append(" ")
                .append("WHERE")
                .append(" ")
                .append(colum)
                .append(operator)
                .append(":")
                .append(param);

            return new WhereBuilder(query);
        }

        public String build() {
            return query.toString();
        }
    }

    public static class WhereBuilder {

        private StringBuilder query;

        public WhereBuilder(StringBuilder query) {
            this.query = query;
        }

        public WhereBuilder where(String colum, String operator, String param) {
            query.append(" ")
                .append("WHERE")
                .append(" ")
                .append(colum)
                .append(operator)
                .append(":")
                .append(param);

            return this;
        }

        public WhereBuilder or(String colum, String operator, String param) {
            query.append(" ")
                .append("OR")
                .append(" ")
                .append(colum)
                .append(operator)
                .append(":")
                .append(param);

            return this;
        }

        public WhereBuilder and(String colum, String operator, String param) {
            query.append(" ")
                .append("AND")
                .append(" ")
                .append(colum)
                .append(operator)
                .append(":")
                .append(param);

            return this;
        }

        public WhereBuilder addSet(String colum, String param) {
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

    }

    public static class UpdateBuilder {

        private StringBuilder query;

        public UpdateBuilder(StringBuilder query) {
            this.query = query;
        }

        public WhereBuilder set(String colum, String param) {
            query.append(" ")
                .append("SET")
                .append(" ")
                .append(colum)
                .append("=")
                .append(":")
                .append(param);

            return new WhereBuilder(query);
        }

    }

}
