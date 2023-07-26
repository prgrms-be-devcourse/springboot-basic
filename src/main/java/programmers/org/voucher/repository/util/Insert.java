package programmers.org.voucher.repository.util;

import programmers.org.voucher.repository.util.constant.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Insert {

    private String query;

    private Insert(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private static final String INSERT_INTO = "INSERT INTO";
        private static final String VALUES = "VALUES";

        private Builder() {
        }

        private List<String> query = new ArrayList<>();
        private List<String> valueQuery = new ArrayList<>();

        public Builder insert(Table table) {
            String statement = String.format("%s", table);
            query.add(statement);

            return this;
        }

        public Builder values(Map<String, Object> map) {
            int i = 0;

            query.add("(");
            valueQuery.add("(");

            for (Map.Entry<String, Object> entry : map.entrySet()){
                String entryString = (i == map.size() - 1) ? "%s" : "%s,";

                query.add(entryString.formatted(entry.getKey()));
                valueQuery.add(entryString.formatted(entry.getValue()));
                i++;
            }

            query.add(")");
            valueQuery.add(")");

            return this;
        }

        public Insert build() {
            String queryJoin = String.join(" ", query);
            String valueQueryJoin = String.join(" ", valueQuery);
            return new Insert("%s %s %s %s".formatted(INSERT_INTO, queryJoin, VALUES, valueQueryJoin));
        }
    }
}
