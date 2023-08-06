package prgms.spring_week1.domain.util.sqlBuilder.builder.DmlBuilder;

import prgms.spring_week1.domain.util.sqlBuilder.builder.conditionBuilder.Where;
import prgms.spring_week1.domain.util.type.TableType;

public class Delete {

    private final TableType table;
    private final Where where;

    public Delete(TableType table, Where where) {
        this.table = table;
        this.where = where;
    }

    public Delete(TableType table) {
        this(table, null);
    }

    public String getQuery() {
        return generateQuery().toString();
    }

    private StringBuilder generateQuery() {
        StringBuilder sb = new StringBuilder();

        String tableName = this.table.name();
        String whereQuery = this.where == null ? "" : where.getQuery();

        return sb.append("DELETE FROM ").append(tableName).append(whereQuery);
    }

    public static DeleteCriteria builder() {
        return new DeleteCriteria();
    }

    public static class DeleteCriteria {
        private TableType table;


        public WhereCriteria deleteFrom(TableType table) {
            this.table = table;
            return new WhereCriteria(table);
        }

        public Delete build() {
            return new Delete(table);
        }

    }

    public static class WhereCriteria {
        private final TableType table;
        private Where where;

        public WhereCriteria(TableType table) {
            this.table = table;
        }

        public Builder where(Where where) {
            this.where = where;
            return new Builder(table, where);
        }

        public Delete build() {
            return new Delete(table);
        }
    }

    public static class Builder {
        private final TableType table;
        private final Where where;

        public Builder(TableType table, Where where) {
            this.table = table;
            this.where = where;
        }

        public Delete build() {
            return new Delete(table, where);
        }
    }
}
