package programmers.org.voucher.repository.util;

import java.util.ArrayList;
import java.util.List;

public class Delete {
    private static final List<String> sql = new ArrayList<>();

    private String query;

    private Delete(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public static DeleteBuilder builder() {
        return new DeleteBuilder();
    }

    public static class DeleteBuilder {
        private DeleteBuilder() {

        }

        public WhereBuilder delete(Class table) {
            String statement = String.format("%s", table.getSimpleName());
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

        private static final String DELETE_FROM = "DELETE FROM";

        private Builder() {
        }

        public Delete build() {
            String join = String.join(" ", sql);
            return new Delete("%s %s".formatted(DELETE_FROM, join));
        }
    }
}
