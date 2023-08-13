package org.prgrms.kdtspringdemo.util.queryBuilder.query;

import org.prgrms.kdtspringdemo.util.JdbcUtils;
import org.prgrms.kdtspringdemo.util.queryBuilder.constant.Operator;

import java.util.Arrays;
import java.util.UUID;

import static org.prgrms.kdtspringdemo.util.JdbcUtils.uuidToBin;

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
            if (value instanceof UUID uuid) {
                String formatting = String.format("%s %s %s", column, operator.getSymbol(), uuidToBin(uuid));
                query.append(formatting);

                return;
            }
            String formatting = String.format("%s %s '%s'", column, operator.getSymbol(), value);
            query.append(formatting);
        }

        public Builder and(String column, Operator operator, Object value) {
            if (value instanceof UUID uuid) {
                String formatting = String.format(" AND %s %s %s", column, operator.getSymbol(), uuidToBin(uuid));
                query.append(formatting);

                return this;
            }

            String formatting = String.format(" AND %s %s '%s'", column, operator.getSymbol(), value);
            query.append(formatting);

            return this;
        }

        public Builder or(String column, Operator operator, Object value) {
            if (value instanceof UUID uuid) {
                String formatting = String.format(" OR %s %s %s", column, operator.getSymbol(), uuidToBin(uuid));
                query.append(formatting);

                return this;
            }

            String formatting = String.format(" OR %s %s '%s'", column, operator.getSymbol(), value);
            query.append(formatting);

            return this;
        }

        public Where build() {
            return new Where(String.format(" %s %s", WHERE, query));
        }
    }
}
