package com.example.voucher.query;

public class Order {

    public static enum Sort {
        ASC,
        DESC;
    }

    private final String columName;
    private final Sort sort;
    private final String query;

    private Order(String columName, Sort sort) {
        this.columName = columName;
        this.sort = sort;
        this.query = makeQuery();
    }

    public String getQuery() {
        return query;
    }

    private String makeQuery() {
        String columnName = this.columName;
        String sort = this.sort == null ? "" : this.sort.name();

        return String.format("ORDER BY %s %s", columnName, sort);
    }

    public static OrderCriteria builder() {
        return new OrderCriteria();
    }

    public static class OrderCriteria {

        private String columName;

        public SortCriteria orderBy(String colum) {
            this.columName = colum;

            return new SortCriteria(columName);
        }

    }

    public static class SortCriteria {

        private String columName;
        private Sort sort;

        public SortCriteria(String columName) {
            this.columName = columName;
        }

        public Builder setSort(Sort sort) {
            this.sort = sort;

            return new Builder(columName, sort);
        }

        public Order build() {
            return new Order(columName, sort);
        }

    }

    public static class Builder {

        private String columName;
        private Sort sort;

        public Builder(String columName, Sort sort) {
            this.columName = columName;
            this.sort = sort;
        }

        public Order build() {
            return new Order(columName, sort);
        }

    }

}
