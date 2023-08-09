package org.prgrms.kdtspringdemo.util.queryBuilder.query;

import org.prgrms.kdtspringdemo.util.queryBuilder.constant.Operator;

public class Where {
    private String query;

    public Where(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public static Builder builder(String column, Operator operator, Object value) {
        return new Builder(column, operator, value);
    }

    public static class Builder {
        private static final String AND = " AND ";
        private static final String OR = " OR ";
        private static final String WHERE = "WHERE";
        private final StringBuilder query = new StringBuilder();

        private Builder(String column, Operator operator, Object value) {
            query.append(column)
                    .append(" ")
                    .append(operator.getSymbol())
                    .append(" ")
                    .append(value);
        }

        public Builder and(String column, Operator operator, Object value) {
            query
                    .append(AND)
                    .append(column)
                    .append(" ")
                    .append(operator.getSymbol())
                    .append(" ")
                    .append(value);

            return this;
        }

        public Builder or(String column, Operator operator, Object value) {
            query
                    .append(OR)
                    .append(column)
                    .append(" ")
                    .append(operator.getSymbol())
                    .append(" ")
                    .append(value);

            return this;
        }

        public Where build() {
            return new Where(String.format(" %s %s", WHERE, query));
        }
    }
}
