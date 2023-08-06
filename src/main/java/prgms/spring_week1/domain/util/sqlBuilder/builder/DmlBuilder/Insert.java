package prgms.spring_week1.domain.util.sqlBuilder.builder.DmlBuilder;

import prgms.spring_week1.domain.util.sqlBuilder.builder.tableBuilder.Column;
import prgms.spring_week1.domain.util.sqlBuilder.builder.tableBuilder.Values;
import prgms.spring_week1.domain.util.type.TableType;

public class Insert {
    private final TableType table;
    private final Column columns;
    private final Values values;

    public Insert(TableType table, Column columns, Values values) {
        this.table = table;
        this.columns = columns;
        this.values = values;
    }

    public String getQuery() {
        return generateQuery().toString();
    }

    private StringBuilder generateQuery() {
        StringBuilder sb = new StringBuilder();
        String tableName = table.name();
        String columnQuery = columns.getQuery();
        String valueQuery = this.values.getQuery();

        return sb.append("INSERT INTO ").append(tableName).append("(").append(columnQuery).append(")").append(valueQuery);
    }

    public static InsertCriteria builder() {
        return new InsertCriteria();
    }

    public static class InsertCriteria {
        public IntoCriteria insert(TableType table) {
            return new IntoCriteria(table);
        }

    }

    public static class IntoCriteria {
        private final TableType table;

        public IntoCriteria(TableType table) {
            this.table = table;
        }

        public ValuesCriteria into(Column column) {
            return new ValuesCriteria(table, column);
        }
    }

    public static class ValuesCriteria {
        private final TableType table;
        private final Column columns;
        private Values values;

        public ValuesCriteria(TableType table, Column columns) {
            this.table = table;
            this.columns = columns;
        }

        public Builder values(Values values) {
            this.values = values;
            return new Builder(table, columns, values);
        }
    }

    public static class Builder {
        private final TableType table;
        private final Column columns;
        private final Values values;

        public Builder(TableType table, Column columns, Values values) {
            this.table = table;
            this.columns = columns;
            this.values = values;
        }

        public Insert build() {
            return new Insert(table, columns, values);
        }
    }
}
