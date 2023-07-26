package programmers.org.voucher.repository.util;

import programmers.org.voucher.repository.util.constant.Table;

import java.util.ArrayList;
import java.util.List;

public class Select {
    private String query;

    private Select(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final static String SELECT = "SELECT";
        private final static String FROM = "FROM";

        private List<String> query = new ArrayList<>();

        private Builder() {
        }

        public Builder select(String column) {
            String statement = String.format("%s", column);
            query.add(statement);

            return this;
        }

        public Builder from(Table table) {
            String statement = String.format("%s %s", FROM, table);
            query.add(statement);

            return this;
        }

        public Builder where(Where where) {
            query.add(where.getQuery());
            return this;
        }

        public Builder orderBy(Order order) {
            query.add(order.getQuery());
            return this;
        }

        public Select build() {
            String queryJoin = String.join(" ", query);
            String query = String.format("%s %s", SELECT, queryJoin);
            return new Select(query);
        }
    }
}
