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
        private static final String WHERE = "WHERE";
        private final StringBuilder query = new StringBuilder();

        private Builder(String column, Operator operator, Object value) {
            String formatting = String.format("%s %s %s", column, operator.getSymbol(), value);
            query.append(formatting);
        }

        public Builder and(String column, Operator operator, Object value) {
            String formatting = String.format(" AND %s %s %s", column, operator.getSymbol(), value);
            query.append(formatting);

            return this;
        }

        public Builder or(String column, Operator operator, Object value) {
            String formatting = String.format(" OR %s %s %s", column, operator.getSymbol(), value);
            query.append(formatting);

            return this;
        }

        public Where build() {
            return new Where(String.format(" %s %s", WHERE, query));
        }
    }
}
