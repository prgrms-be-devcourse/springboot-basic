package org.prgrms.kdtspringdemo.util;

public class Query {
    private static final String INSERT = "INSERT INTO";
    private static final String SELECT = "SELECT";
    private static final String UPDATE = "UPDATE";
    private static final String DELETE = "DELETE";

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

    public static class InsertBuilder {
        private StringBuilder stringBuilder;

        public InsertBuilder() {
            this.stringBuilder = new StringBuilder();
        }

        public InsertBuilder insert(String table) {
            stringBuilder.append(INSERT)
                    .append(BLANK)
                    .append(table);

            return this;
        }

        public <T>InsertBuilder values(T ...values) {
            stringBuilder.append(BLANK)
                    .append(VALUES)
                    .append(LEFT_BRACKET);

            for (T value : values) {
                stringBuilder.append(COLON)
                        .append(value)
                        .append(COMMA);
            }

            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            stringBuilder.append(RIGHT_BRACKET);

            return this;
        }

        public InsertBuilder build() {
            return this;
        }

        public String toString() {
            return stringBuilder.toString();
        }
    }

    public static class SelectBuilder {
        private StringBuilder stringBuilder;

        public SelectBuilder() {
            this.stringBuilder = new StringBuilder();
        }

        public SelectBuilder select(String... columns) {
            stringBuilder.append(SELECT)
                    .append(BLANK);

            for (String column : columns) {
                stringBuilder.append(column)
                        .append(COMMA);
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);

            return this;
        }

        public SelectBuilder from(String table) {
            stringBuilder.append(BLANK)
                    .append(FROM)
                    .append(BLANK)
                    .append(table);

            return this;
        }

        public <T>SelectBuilder where(String column, T value) {
            stringBuilder.append(BLANK)
                    .append(WHERE)
                    .append(BLANK)
                    .append(column)
                    .append(BLANK)
                    .append(EQUAL)
                    .append(BLANK)
                    .append(COLON)
                    .append(value);

            return this;
        }

        public SelectBuilder build() {
            return this;
        }

        public String toString() {
            return stringBuilder.toString();
        }
    }

    public static class UpdateBuilder {
        private StringBuilder stringBuilder;

        public UpdateBuilder() {
            this.stringBuilder = new StringBuilder();
        }

        public UpdateBuilder update(String table) {
            stringBuilder.append(UPDATE)
                    .append(BLANK)
                    .append(table);

            return this;
        }

        public <T>UpdateBuilder set(String column, T value) {
            stringBuilder.append(BLANK)
                    .append(SET)
                    .append(BLANK)
                    .append(column)
                    .append(BLANK)
                    .append(EQUAL)
                    .append(BLANK)
                    .append(COLON)
                    .append(value);

            return this;
        }

        public <T>UpdateBuilder addSet(String column, T value) {
            stringBuilder.append(COMMA)
                    .append(BLANK)
                    .append(column)
                    .append(BLANK)
                    .append(EQUAL)
                    .append(BLANK)
                    .append(COLON)
                    .append(value);

            return this;
        }

        public UpdateBuilder build() {
            return this;
        }

        public String toString() {
            return stringBuilder.toString();
        }
    }

    public static class DeleteBuilder {
        private StringBuilder stringBuilder;

        public DeleteBuilder() {
            this.stringBuilder = new StringBuilder();
        }

        public DeleteBuilder delete(String table) {
            stringBuilder.append(DELETE)
                    .append(BLANK)
                    .append(FROM)
                    .append(BLANK)
                    .append(table);

            return this;
        }

        public <T>DeleteBuilder where(String column, T value) {
            stringBuilder.append(BLANK)
                    .append(WHERE)
                    .append(BLANK)
                    .append(column)
                    .append(BLANK)
                    .append(EQUAL)
                    .append(BLANK)
                    .append(COLON)
                    .append(value);

            return this;
        }

        public DeleteBuilder build() {
            return this;
        }

        public String toString() {
            return stringBuilder.toString();
        }
    }
}
