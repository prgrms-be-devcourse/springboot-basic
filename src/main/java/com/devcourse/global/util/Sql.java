package com.devcourse.global.util;

public class Sql {
    public enum Table {
        USERS, VOUCHERS;

        public String toLowerCase() {
            return this.name().toLowerCase();
        }
    }

    private static final int UNNECESSARY_STRING = 2;
    private static final String WHERE = " WHERE ";
    private static final String PARAMETER = "?";
    private static final String EQUAL = " = ";
    private static final String COMMA = ", ";

    private Sql() { }

    public static Sql.Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private static final String INSERT_INTO = "INSERT INTO ";
        private static final String SELECT = "SELECT ";
        private static final String UPDATE = "UPDATE ";
        private static final String DELETE = "DELETE FROM ";

        private final StringBuilder query;

        private Builder() {
            this.query = new StringBuilder();
        }

        public SelectBuilder select(String... columns) {
            query.append(SELECT);

            for (String column : columns) {
                query.append(column)
                     .append(COMMA);
            }

            query.setLength(query.length() - UNNECESSARY_STRING);
            return new SelectBuilder(query);
        }

        public InsertBuilder insertInto(Table table) {
            query.append(INSERT_INTO)
                 .append(table.toLowerCase());

            return new InsertBuilder(query);
        }

        public UpdateBuilder update(Table table) {
            query.append(UPDATE)
                 .append(table.toLowerCase());

            return new UpdateBuilder(query);
        }

        public DeleteBuilder deleteFrom(Table table) {
            query.append(DELETE)
                 .append(table.toLowerCase());

            return new DeleteBuilder(query);
        }
    }

    public static class InsertBuilder {
        private static final String VALUES = " VALUES ";
        private static final String LEFT_BRACKET = "(";
        private static final String RIGHT_BRACKET = ")";

        private final StringBuilder query;

        private InsertBuilder(StringBuilder query) {
            this.query = query;
        }

        public ValuesBuilder values(String... columns) {
            query.append(LEFT_BRACKET);

            for (String column : columns) {
                query.append(column).append(COMMA);
            }

            query.setLength(query.length() - UNNECESSARY_STRING);
            query.append(RIGHT_BRACKET)
                 .append(VALUES)
                 .append(LEFT_BRACKET);

            for (int i = 0; i < columns.length; i++) {
                query.append(PARAMETER).append(COMMA);
            }

            query.setLength(query.length() - UNNECESSARY_STRING);
            query.append(RIGHT_BRACKET);

            return new ValuesBuilder(query);
        }
    }

    public static class SelectBuilder {
        private static final String FROM = " FROM ";

        private final StringBuilder query;

        private SelectBuilder(StringBuilder query) {
            this.query = query;
        }

        public FromBuilder from(Table table) {
            query.append(FROM).append(table.toLowerCase());
            return new FromBuilder(query);
        }
    }

    public static class UpdateBuilder {
        private static final String SET = " SET ";

        private final StringBuilder query;

        private UpdateBuilder(StringBuilder query) {
            this.query = query;
        }

        public SetBuilder set(String... columns) {
            query.append(SET);

            for (String column : columns) {
                query.append(column)
                     .append(EQUAL)
                     .append(PARAMETER)
                     .append(COMMA);
            }

            query.setLength(query.length() - UNNECESSARY_STRING);
            return new SetBuilder(query);
        }
    }

    public static class DeleteBuilder {
        private final StringBuilder query;

        private DeleteBuilder(StringBuilder query) {
            this.query = query;
        }

        public WhereBuilder where(String condition) {
            query.append(WHERE)
                 .append(condition)
                 .append(EQUAL)
                 .append(PARAMETER);

            return new WhereBuilder(query);
        }

        public String build() {
            return query.toString();
        }
    }

    public static class SetBuilder {
        private final StringBuilder query;

        private SetBuilder(StringBuilder query) {
            this.query = query;
        }

        public WhereBuilder where(String condition) {
            query.append(WHERE)
                 .append(condition)
                 .append(EQUAL)
                 .append(PARAMETER);

            return new WhereBuilder(query);
        }
    }

    public static class FromBuilder {
        private final StringBuilder query;

        private FromBuilder(StringBuilder query) {
            this.query = query;
        }

        public WhereBuilder where(String condition) {
            query.append(WHERE)
                 .append(condition)
                 .append(EQUAL)
                 .append(PARAMETER);

            return new WhereBuilder(query);
        }

        public String build() {
            return query.toString();
        }
    }

    public static class WhereBuilder {
        private static final String AND = " AND ";
        private static final String OR = " OR ";

        private final StringBuilder query;

        private WhereBuilder(StringBuilder query) {
            this.query = query;
        }

        public WhereBuilder and(String condition) {
            query.append(AND)
                 .append(condition)
                 .append(EQUAL)
                 .append(PARAMETER);

            return this;
        }

        public WhereBuilder or(String condition) {
            query.append(OR)
                 .append(condition)
                 .append(EQUAL)
                 .append(PARAMETER);

            return this;
        }

        public String build() {
            return query.toString();
        }
    }

    public static class ValuesBuilder {
        private final StringBuilder query;

        private ValuesBuilder(StringBuilder query) {
            this.query = query;
        }

        public String build() {
            return query.toString();
        }
    }
}
