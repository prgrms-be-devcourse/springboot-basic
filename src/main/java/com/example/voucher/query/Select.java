package com.example.voucher.query;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import com.example.voucher.query.marker.Entity;

public class Select {

    private final List<String> columns;
    private final Class<? extends Entity> table;
    private final Where where;
    private final Order order;
    private final String query;

    private Select(List<String> columns, Class<? extends Entity> table) {
        this(columns, table, null, null);
    }

    private Select(List<String> columns, Class<? extends Entity> table, Where where) {
        this(columns, table, where, null);
    }

    private Select(List<String> columns, Class<? extends Entity> table, Where where, Order order) {
        this.columns = columns;
        this.table = table;
        this.where = where;
        this.order = order;
        this.query = makeQuery();
    }

    public String getQuery() {
        return query;
    }

    public List<String> getColumns() {
        if (!columns.isEmpty())
            return this.columns;

        return Arrays.stream(table.getDeclaredFields())
            .map(Field::getName)
            .toList();
    }

    private String makeQuery() {

        String columns = String.join(", ", getColumns());
        String table = this.table.getSimpleName().toUpperCase();
        String where = this.where == null ? "" : this.where.getQuery();
        String order = this.order == null ? "" : this.order.getQuery();

        return String.format("SELECT %s FROM %s %s %s", columns, table, where, order).trim();
    }

    public static SelectCriteria builder() {
        return new SelectCriteria();
    }

    public static class SelectCriteria {

        private List<String> columns;

        public SelectCriteria() {
        }

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

        private List<String> columns;
        private Class<? extends Entity> table;

        public FromCriteria(List<String> columns) {
            this.columns = columns;
        }

        public WhereCriteria from(Class<? extends Entity> tables) {
            this.table = tables;
            return new WhereCriteria(columns, table);
        }

        public Select build() {
            return new Select(columns, table);
        }

    }

    public static class WhereCriteria {

        private List<String> columns;
        private Class<? extends Entity> table;
        private Where where;

        public WhereCriteria(List<String> columns, Class<? extends Entity> table) {
            this.columns = columns;
            this.table = table;
        }

        public OrderCriteria where(Where where) {
            this.where = where;

            return new OrderCriteria(columns, table, where);
        }

        public Select build() {
            return new Select(columns, table, where);
        }

    }

    public static class OrderCriteria {

        private List<String> columns;
        private Class<? extends Entity> table;
        private Where where;
        private Order order;

        public OrderCriteria(List<String> columns, Class<? extends Entity> table, Where where) {
            this.columns = columns;
            this.table = table;
            this.where = where;
        }

        public Builder orderBy(Order order) {
            this.order = order;

            return new Builder(columns, table, where, order);
        }

        public Select build() {
            return new Select(columns, table, where, order);
        }

    }

    public static class Builder {

        private List<String> columns;
        private Class<? extends Entity> table;
        private Where where;
        private Order order;

        public Builder(List<String> columns, Class<? extends Entity> table, Where where, Order order) {
            this.columns = columns;
            this.table = table;
            this.where = where;
            this.order = order;
        }

        public Select build() {
            return new Select(columns, table, where, order);
        }

    }

}


