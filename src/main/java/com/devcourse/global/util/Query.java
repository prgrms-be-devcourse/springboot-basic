package com.devcourse.global.util;

public class Query {
    public enum Table { USERS, VOUCHERS }
    private static final String LEFT_BRACKET = "(";
    private static final String RIGHT_BRACKET = ")";
    private static final String COMMA = ",";
    private static final String INSERT_INTO = "INSERT INTO ";
    private static final String SELECT = "SELECT ";
    private static final String DELETE = "DELETE FROM ";
    private static final String FROM = " FROM ";
    private static final String UPDATE = "UPDATE ";
    private static final String VALUES = " VALUES (";
    private static final String SET = " SET ";
    private static final String WHERE = " WHERE ";
    private static final String PARAMETER = "?";
    private static final String EQUAL = " = ";

    private Query() { }

    public static Query.Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final StringBuilder query;

        Builder() {
            this.query = new StringBuilder();
        }

        public Builder insertInto(Table table) {
            query.append(INSERT_INTO).append(table);
            return this;
        }

        public Builder select(String... columns) {
            query.append(SELECT);

            for (String column : columns) {
                query.append(column).append(COMMA);
            }

            query.setLength(query.length() - 1);
            return this;
        }

        public Builder update(Table table) {
            query.append(UPDATE).append(table);
            return this;
        }

        public Builder set(String... columns) {
            query.append(SET);

            for (String column : columns) {
                query.append(column).append(EQUAL).append(PARAMETER).append(COMMA);
            }

            query.setLength(query.length() - 1);
            return this;
        }

        public Builder deleteFrom(Table table) {
            query.append(DELETE).append(table);
            return this;
        }

        public Builder from(Table table) {
            query.append(FROM).append(table);
            return this;
        }

        public Builder where(String condition) {
            query.append(WHERE).append(condition).append(EQUAL).append(PARAMETER);
            return this;
        }

        public Builder values(String... columns) {
            query.append(LEFT_BRACKET);

            for (String column : columns) {
                query.append(column).append(COMMA);
            }

            query.setLength(query.length() - 1);
            query.append(RIGHT_BRACKET);

            query.append(VALUES);
            for (int i = 0; i < columns.length; i++) {
                query.append(PARAMETER).append(COMMA);
            }

            query.setLength(query.length() - 1);
            query.append(RIGHT_BRACKET);
            return this;
        }

        public String build() {
            return query.toString();
        }
    }
}
