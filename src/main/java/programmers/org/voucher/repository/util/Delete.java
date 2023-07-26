package programmers.org.voucher.repository.util;

import programmers.org.voucher.repository.util.constant.Table;

import java.util.ArrayList;
import java.util.List;

public class Delete {
    private String query;

    private Delete(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private static final String DELETE_FROM = "DELETE FROM";

        private Builder() {
        }

        private List<String> query = new ArrayList<>();

        public Builder delete(Table table) {
            String statement = String.format("%s", table);
            query.add(statement);

            return this;
        }

        public Builder where(Where where) {
            query.add(where.getQuery());

            return this;
        }

        public Delete build() {
            String queryJoin = String.join(" ", query);
            String query = String.format("%s %s", DELETE_FROM, queryJoin);
            return new Delete(query);
        }
    }
}
