package prgms.spring_week1.domain.util.sqlBuilder.builder.DmlBuilder.impl;

import prgms.spring_week1.domain.util.sqlBuilder.builder.DmlBuilder.DmlBuilder;
import prgms.spring_week1.domain.util.sqlBuilder.builder.conditionBuilder.Where;
import prgms.spring_week1.domain.util.sqlBuilder.builder.tableBuilder.Set;
import prgms.spring_week1.domain.util.type.TableType;

public class Update implements DmlBuilder {
    private StringBuilder update;

    public Update(StringBuilder updateBuilder) {
        this.update = updateBuilder;
    }

    public static class UpdateBuilder {
        private StringBuilder updateBuilder = new StringBuilder();

        public UpdateBuilder update(TableType table) {
            updateBuilder.append("UPDATE ").append(table);
            return this;
        }

        public UpdateBuilder set(Set set) {
            updateBuilder.append(" SET ").append(set.toString());
            return this;
        }

        public UpdateBuilder where(Where where) {
            updateBuilder.append(where.toString());
            return this;
        }

        public Update build() {
            return new Update(updateBuilder);
        }
    }

    @Override
    public String toString() {
        return update.toString();
    }
}
