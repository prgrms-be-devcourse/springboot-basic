package prgms.spring_week1.domain.util.sqlBuilder.builder.DmlBuilder.impl;

import prgms.spring_week1.domain.util.sqlBuilder.builder.DmlBuilder.DmlBuilder;
import prgms.spring_week1.domain.util.sqlBuilder.builder.conditionBuilder.Where;
import prgms.spring_week1.domain.util.type.TableType;

public class Delete implements DmlBuilder {
    private StringBuilder delete;

    public Delete(StringBuilder deleteSqlBuilder) {
        this.delete = deleteSqlBuilder;
    }

    public static class DeleteBuilder {
        private StringBuilder deleteBuilder = new StringBuilder();

        public DeleteBuilder delete(TableType table) {
            deleteBuilder.append("DELETE FROM ").append(table.name());
            return this;
        }

        public DeleteBuilder where(Where where) {
            deleteBuilder.append(where.toString());
            return this;
        }

        public Delete build() {
            return new Delete(deleteBuilder);
        }
    }

    @Override
    public String toString() {
        return delete.toString();
    }
}
