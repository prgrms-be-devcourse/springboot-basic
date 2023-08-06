package prgms.spring_week1.domain.util.sqlBuilder.builder.DmlBuilder;

import prgms.spring_week1.domain.util.sqlBuilder.builder.conditionBuilder.Order;
import prgms.spring_week1.domain.util.sqlBuilder.builder.conditionBuilder.Where;
import prgms.spring_week1.domain.util.sqlBuilder.builder.tableBuilder.Column;
import prgms.spring_week1.domain.util.type.TableType;

public class Select {
    private final Column columns;
    private final TableType table;
    private final Where where;
    private final Order order;

    public Select(Column columns, TableType table, Where where, Order order) {
        this.columns = columns;
        this.table = table;
        this.where = where;
        this.order = order;
    }

    public Select(Column columns, TableType table) {
        this(columns, table, null, null);
    }

    public Select(Column columns, TableType table, Where where) {
        this(columns, table, where, null);
    }

    public String getQuery() {
        return generateQuery().toString();
    }

    private StringBuilder generateQuery() {
        StringBuilder sb = new StringBuilder();

        String columns = this.columns == null ? "*" : this.columns.getQuery();
        String tableName = this.table.name();
        String whereQuery = this.where == null ? "" : where.getQuery();
        String orderQuery = this.order == null ? "" : order.getQuery();

        return sb.append("SELECT ").append(columns).append(" FROM ").append(tableName).append(whereQuery).append(orderQuery);
    }

    public static SelectCriteria builder() {
        return new SelectCriteria();
    }

    public static class SelectCriteria {
        private StringBuilder selectBuilder = new StringBuilder();

        public FromCriteria select(Column column) {
            return new FromCriteria(column);
        }

        public FromCriteria selectAll() {
            return new FromCriteria(null);
        }

    }

    public static class FromCriteria {
        private final Column columns;

        public FromCriteria(Column columns) {
            this.columns = columns;
        }

        public WhereCriteria from(TableType table) {
            return new WhereCriteria(columns, table);
        }

    }

    public static class WhereCriteria {
        private final Column columns;
        private final TableType table;
        private Where where;

        public WhereCriteria(Column columns, TableType table) {
            this.columns = columns;
            this.table = table;
        }

        public OrderCriteria where(Where where) {
            this.where = where;
            return new OrderCriteria(columns, table, where);
        }

        public Select build() {
            return new Select(columns, table);
        }
    }

    public static class OrderCriteria {
        private final Column columns;
        private final TableType table;
        private final Where where;
        private Order order;

        public OrderCriteria(Column columns, TableType table, Where where) {
            this.columns = columns;
            this.table = table;
            this.where = where;
        }

        public Builder order(Order order) {
            this.order = order;
            return new Builder(columns, table, where, order);
        }

        public Select build(Order order) {
            return new Select(columns, table, where, order);
        }

        public Select build() {
            return new Select(columns, table, where);
        }
    }

    public static class Builder {
        private final Column column;
        private final TableType from;
        private final Where where;
        private final Order order;

        public Builder(Column column, TableType from, Where where, Order order) {
            this.column = column;
            this.from = from;
            this.where = where;
            this.order = order;
        }

        public Select build() {
            return new Select(column, from, where, order);
        }
    }
}

