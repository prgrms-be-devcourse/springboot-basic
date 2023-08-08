package builder;


import builder.builderEntity.Entity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Select {
    private final List<String> columns;
    private final Class<? extends Entity> table;
    private final Where where;
    private final Order order;
    private final String query;

    private Select(List<String> columns, Class<? extends Entity> table, Where where, Order order) {
        this.columns = columns;
        this.table = table;
        this.where = where;
        this.order = order;
        this.query = generateQuery();
    }

    private Select(List<String> columns, Class<? extends Entity> table, Where where) {
        this(columns, table, where, null);
    }

    private Select(List<String> columns, Class<? extends Entity> table) {
        this(columns, table, null, null);
    }

    public static SelectCriteria builder() {
        return new SelectCriteria();
    }

    private String generateQuery() {
        List<String> columnList = getColumns();

        String column = String.join(", ", columnList);
        String tableName = this.table.getSimpleName().toLowerCase();
        String whereQuery = this.where == null ? "" : String.format(" WHERE %s", this.where.getQuery());
        String orderQuery = this.order == null ? "" : String.format(" ORDER BY %s", this.order.getQuery());

        return "SELECT %s FROM %s".formatted(column, tableName) + whereQuery + orderQuery;
    }

    private List<String> getColumns() {
        List<String> tableColumns = Arrays.stream(this.table.getDeclaredFields())
                .map(Field::getName)
                .toList();

        return this.columns.isEmpty() ? tableColumns : this.columns;
    }

    public String getQuery() {
        return this.query;
    }

    public static class SelectCriteria {
        private List<String> columns = new ArrayList<>();

        public FromCriteria select(String... columns) {
            this.columns = List.of(columns);
            return new FromCriteria(this.columns);
        }

        public FromCriteria select(Class dto) {
            this.columns = Arrays.stream(dto.getDeclaredFields())
                    .map(Field::getName)
                    .toList();

            return new FromCriteria(this.columns);
        }
    }

    public static class FromCriteria {
        private final List<String> columns;
        private Class<? extends Entity> table;

        private FromCriteria(List<String> columns) {
            this.columns = columns;
        }

        public WhereCriteria from(Class<? extends Entity> entity) {
            this.table = entity;
            return new WhereCriteria(this.columns, this.table);
        }
    }

    public static class WhereCriteria {
        private final List<String> columns;
        private final Class<? extends Entity> table;
        private Where where;

        private WhereCriteria(List<String> columns, Class<? extends Entity> table) {
            this.columns = columns;
            this.table = table;
        }

        public OrderCriteria where(Where condition) {
            this.where = condition;
            return new OrderCriteria(this.columns, this.table, this.where);
        }

        public Select build() {
            return new Select(columns, table);
        }
    }

    public static class OrderCriteria {
        private final List<String> columns;
        private final Class<? extends Entity> table;
        private final Where where;
        private Order order;

        private OrderCriteria(List<String> columns, Class<? extends Entity> table, Where where) {
            this.columns = columns;
            this.table = table;
            this.where = where;
        }

        public Builder order(Order order) {
            this.order = order;
            return new Builder(this.columns, this.table, this.where, this.order);
        }

        public Select build() {
            return new Select(columns, table, where, order);
        }
    }

    public static class Builder {
        private final List<String> columns;
        private final Class<? extends Entity> table;
        private final Where where;
        private final Order order;

        private Builder(List<String> columns, Class<? extends Entity> table, Where where, Order order) {
            this.columns = columns;
            this.table = table;
            this.where = where;
            this.order = order;
        }

        public Select build() {
            return new Select(this.columns, this.table, this.where, this.order);
        }
    }
}
