package programmers.org.voucher.repository.util;

import java.util.ArrayList;
import java.util.List;

import static programmers.org.voucher.repository.util.Order.Type.ASC;
import static programmers.org.voucher.repository.util.Order.Type.DESC;

public class Order {
    public enum Type {
        ASC, DESC
    }

    private String query;

    private Order(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<String> query = new ArrayList<>();
        private static final String ORDER_BY = "ORDER BY";

        private Builder() {
        }

        public Builder asc(String column) {
            String statement = String.format("%s %s", column, ASC);
            query.add(statement);

            return this;
        }

        public Builder desc(String column) {
            String statement = String.format("%s %s", column, DESC);
            query.add(statement);

            return this;
        }

        public Order build() {
            String queryJoin = String.join(" ", query);
            String query = String.format("%s %s", ORDER_BY, queryJoin);
            return new Order(query);
        }
    }
}
