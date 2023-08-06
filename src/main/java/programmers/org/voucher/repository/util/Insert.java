package programmers.org.voucher.repository.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Insert {

    private static final List<String> sql = new ArrayList<>();

    private String query;

    private Insert(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public static InsertBuilder builder() {
        return new InsertBuilder();
    }

    public static class InsertBuilder {

        private InsertBuilder() {

        }

        public ValueBuilder insert(Class table) {
            String statement = String.format("%s", table.getSimpleName());
            sql.add(statement);

            return new ValueBuilder();
        }
    }

    public static class ValueBuilder {

        private static final String VALUES = "VALUES";

        private List<String> valueQuery = new ArrayList<>();

        private ValueBuilder() {

        }

        public Builder values(Map<String, Object> map) {
            int i = 0;

            sql.add("(");
            valueQuery.add("(");

            for (Map.Entry<String, Object> entry : map.entrySet()){
                String format = (i == map.size() - 1) ? "%s" : "%s,";

                sql.add(format.formatted(entry.getKey()));
                valueQuery.add(format.formatted(entry.getValue()));
                i++;
            }

            sql.add(")");
            sql.add(VALUES);
            valueQuery.add(")");

            String join = String.join(" ", valueQuery);
            sql.add(join);

            return new Builder();
        }
    }

    public static class Builder {

        private static final String INSERT_INTO = "INSERT INTO";

        private Builder() {

        }

        public Insert build() {
            String join = String.join(" ", sql);
            return new Insert("%s %s".formatted(INSERT_INTO, join));
        }
    }
}
