package programmers.org.voucher.repository.util;

import java.util.ArrayList;
import java.util.List;

public class Update {
    private static final List<String> sql = new ArrayList<>();

    private String query;

    private Update(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public static UpdateBuilder builder() {
        return new UpdateBuilder();
    }

    public static class UpdateBuilder {
        private UpdateBuilder() {
        }

        public SetBuilder update(Class table) {
            sql.add(table.getSimpleName());

            return new SetBuilder();
        }
    }

    public static class SetBuilder {
        private static final String SET = "SET";

        private SetBuilder() {

        }

        public WhereBuilder set(String column, Object value) {
            String statement = String.format("%s %s = %s", SET, column, value);
            sql.add(statement);

            return new WhereBuilder();
        }
    }

    public static class WhereBuilder {

        private WhereBuilder() {
        }

        public Builder where(Where where) {
            sql.add(where.getQuery());
            return new Builder();
        }
    }

    public static class Builder {

        private static final String UPDATE = "UPDATE";

        private Builder() {

        }

        public Update build() {
            String join = String.join(" ", sql);
            sql.clear();
            return new Update("%s %s".formatted(UPDATE, join));
        }
    }
}
