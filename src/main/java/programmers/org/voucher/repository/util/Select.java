package programmers.org.voucher.repository.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Select {
    private static final List<String> sql = new ArrayList<>();

    private String query;

    private Select(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public static SelectBuilder builder() {
        return new SelectBuilder();
    }

    public static class SelectBuilder {
        private SelectBuilder() {

        }

        public FromBuilder select(Class column) {

            List<String> subQuery = new ArrayList<>();
            Field[] columns = column.getDeclaredFields();

            for (Field field : columns) {
                String statement = String.format("%s", field.getName());
                subQuery.add(statement);
            }

            String join = String.join(", ", subQuery);
            sql.add(join);

            return new FromBuilder();
        }

        public FromBuilder select() {
            sql.add("*");
            return new FromBuilder();
        }
    }

    public static class FromBuilder {
        private final static String FROM = "FROM";

        private FromBuilder() {

        }

        public WhereBuilder from(Class table) {
            String statement = String.format("%s %s", FROM, table.getSimpleName());
            sql.add(statement);
            return new WhereBuilder();
        }
    }

    public static class WhereBuilder {
        private WhereBuilder() {
        }

        public OrderBuilder where(Where where) {
            sql.add(where.getQuery());
            return new OrderBuilder();
        }

        public Select build() {
            return new Builder().build();
        }
    }

    public static class OrderBuilder {
        private OrderBuilder() {
        }

        public Builder orderBy(Order order) {
            sql.add(order.getQuery());
            return new Builder();
        }

        public Select build() {
            return new Builder().build();
        }
    }

    public static class Builder {

        private final static String SELECT = "SELECT";

        private Builder() {
        }

        public Select build() {
            String join = String.join(" ", sql);
            sql.clear();
            return new Select("%s %s".formatted(SELECT, join));
        }
    }
}
