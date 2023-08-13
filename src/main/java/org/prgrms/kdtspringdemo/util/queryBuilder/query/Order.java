package org.prgrms.kdtspringdemo.util.queryBuilder.query;

public class Order {
    private String query;

    public Order(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public static Builder builder() {
   		return new Builder();
   	}

    public static class Builder {
        private static final String ASC = " ASC";
        private static final String DESC = " DESC";
        private static final String ORDER = "ORDER BY";
        private final StringBuilder query = new StringBuilder();

        private Builder() {
        }

        public Builder asc(String column) {
            query
                    .append(column)
                    .append(ASC);

            return this;
        }

        public Builder desc(String column) {
            query
                    .append(column)
                    .append(DESC);

            return this;
        }

        public Order build() {
            return new Order(String.format(" %s %s", ORDER, query));
        }
    }
}
