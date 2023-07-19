package org.prgrms.kdtspringdemo.util;

public class Query {
    private static final String INSERT = "INSERT INTO";
    private static final String SELECT = "SELECT";
    private static final String UPDATE = "UPDATE";
    private static final String DELETE = "DELETE FROM";

    private static final String VALUES = "VALUES";
    private static final String FROM = "FROM";
    private static final String WHERE = "WHERE";
    private static final String SET = "SET";

    private static final String BLANK = " ";
    private static final String EQUAL = "=";
    private static final String COMMA = ",";
    private static final String COLON = ":";
    private static final String LEFT_BRACKET = "(";
    private static final String RIGHT_BRACKET = ")";

    public static class QueryBuilder {
        private StringBuilder stringBuilder;

        public QueryBuilder() {
            this.stringBuilder = new StringBuilder();
        }

        public QueryBuilder insertBuilder(String table) {
            stringBuilder.append(INSERT)
                    .append(BLANK)
                    .append(table);

            return this;
        }

        public QueryBuilder selectBuilder(String... columns) {
            stringBuilder.append(SELECT)
                    .append(BLANK);

            for (String colum : columns) {
                stringBuilder.append(colum)
                        .append(COMMA);
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);

            return this;
        }

        public QueryBuilder updateBuilder(String table) {
            stringBuilder.append(UPDATE)
                    .append(BLANK)
                    .append(table);

            return this;
        }

        public QueryBuilder deleteBuilder(String table) {
            stringBuilder.append(DELETE)
                    .append(BLANK)
                    .append(table);

            return this;
        }

        public QueryBuilder valuesBuilder(String ...values) {
            stringBuilder.append(BLANK)
                    .append(VALUES)
                    .append(LEFT_BRACKET);

            for (String value : values) {
                stringBuilder.append(COLON)
                        .append(value)
                        .append(COMMA);
            }

            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            stringBuilder.append(RIGHT_BRACKET);

            return this;
        }

        public QueryBuilder fromBuilder(String table) {
            stringBuilder.append(BLANK)
                    .append(FROM)
                    .append(BLANK)
                    .append(table);

            return this;
        }

        public QueryBuilder whereCommonBuilder(String column) {
            stringBuilder.append(BLANK)
                    .append(WHERE)
                    .append(BLANK)
                    .append(column)
                    .append(BLANK)
                    .append(EQUAL)
                    .append(BLANK)
                    .append(COLON)
                    .append(column);

            return this;
        }

        public QueryBuilder setBuilder(String column) {
            stringBuilder.append(BLANK)
                    .append(SET)
                    .append(BLANK)
                    .append(column)
                    .append(BLANK)
                    .append(EQUAL)
                    .append(BLANK)
                    .append(COLON)
                    .append(column);

            return this;
        }

        public QueryBuilder addSetBuilder(String column) {
            stringBuilder.append(COMMA)
                    .append(BLANK)
                    .append(column)
                    .append(BLANK)
                    .append(EQUAL)
                    .append(BLANK)
                    .append(COLON)
                    .append(column);

            return this;
        }

        public String build() {
            return stringBuilder.toString();
        }
    }
}
