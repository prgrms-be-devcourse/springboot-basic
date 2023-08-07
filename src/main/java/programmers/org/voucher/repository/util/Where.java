package programmers.org.voucher.repository.util;

import programmers.org.voucher.repository.util.constant.Operator;

import java.util.ArrayList;
import java.util.List;

public class Where {
    private String query;

    private Where(String query) {
        this.query = query;
    }

    String getQuery() {
        return query;
    }

    public static Builder builder(String column, Operator operator, Object value) {
        return new Builder(column, operator, value);
    }

    public static class Builder {
        private static final String WHERE = "WHERE";

        private List<String> query = new ArrayList<>();

        private Builder() {
        }

        Builder(String column, Operator operator, Object value) {
            query.add(generateCondition(column, operator, value));
        }

        public Builder and(String column, Operator operator, Object value) {
            String statement = String.format("AND %s", generateCondition(column, operator, value));
            query.add(statement);

            return this;
        }

        public Builder or(String column, Operator operator, Object value) {
            String statement = String.format("OR %s %s %s", column, operator.getSymbol(), value);
            query.add(statement);

            return this;
        }

        public Where build() {
            String queryJoin = String.join(" ", query);
            String query = String.format("%s %s", WHERE, queryJoin);
            return new Where(query);
        }

        private String generateCondition(String column, Operator operator, Object value) {
            return String.format("%s %s %s", column, operator.getSymbol(), value);
        }
    }
}
