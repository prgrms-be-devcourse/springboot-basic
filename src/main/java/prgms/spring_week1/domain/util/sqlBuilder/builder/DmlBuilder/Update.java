package prgms.spring_week1.domain.util.sqlBuilder.builder.DmlBuilder;

import prgms.spring_week1.domain.util.sqlBuilder.builder.conditionBuilder.Where;
import prgms.spring_week1.domain.util.sqlBuilder.builder.tableBuilder.Set;
import prgms.spring_week1.domain.util.type.TableType;

public class Update {

    private final TableType table;
    private final Set set;
    private final Where where;

    public Update(TableType table, Set set, Where where) {
        this.table = table;
        this.set = set;
        this.where = where;
    }

    public String getQuery() {
        return generateQuery().toString();
    }

    private StringBuilder generateQuery() {
        StringBuilder sb = new StringBuilder();

        String table = this.table.name();
        String setQuery = set.getQuery();
        String whereQuery = where.getQuery();

        return sb.append("UPDATE ").append(table).append(" SET ").append(setQuery).append(whereQuery);
    }

    public static UpdateCriteria builder() {
        return new UpdateCriteria();
    }

    public static class UpdateCriteria {
        private TableType table;

        public SetCriteria update(TableType table) {
            this.table = table;
            return new SetCriteria(table);
        }
    }

    public static class SetCriteria {
        private final TableType table;
        private Set set;

        public SetCriteria(TableType table) {
            this.table = table;
        }

        public WhereCriteria set(Set set) {
            this.set = set;
            return new WhereCriteria(table, set);
        }
    }

    public static class WhereCriteria {
        private final TableType table;
        private Set set;
        private Where where;

        public WhereCriteria(TableType table, Set set) {
            this.table = table;
            this.set = set;
        }

        public Builder where(Where where) {
            this.where = where;
            return new Builder(table, set, where);
        }

        public Update build() {
            return new Update(table, set, where);
        }
    }

    public static class Builder {
        private final TableType table;
        private final Set set;
        private final Where where;

        public Builder(TableType table, Set set, Where where) {
            this.table = table;
            this.set = set;
            this.where = where;
        }

        public Update build() {
            return new Update(table, set, where);
        }
    }
}
