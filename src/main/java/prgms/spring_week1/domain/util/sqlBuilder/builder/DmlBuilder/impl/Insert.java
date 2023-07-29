package prgms.spring_week1.domain.util.sqlBuilder.builder.DmlBuilder.impl;

import prgms.spring_week1.domain.util.sqlBuilder.builder.DmlBuilder.Dml;
import prgms.spring_week1.domain.util.sqlBuilder.builder.tableBuilder.Column;
import prgms.spring_week1.domain.util.sqlBuilder.builder.tableBuilder.Values;
import prgms.spring_week1.domain.util.type.TableType;

public class Insert implements Dml {
    private StringBuilder insert;

    public Insert(StringBuilder insertBuilder) {
        this.insert = insertBuilder;
    }

    public static class InsertBuilder {
        private StringBuilder insertBuilder = new StringBuilder();

        public InsertBuilder insert(TableType table) {
            insertBuilder.append("INSERT INTO ").append(table);
            return this;
        }

        public InsertBuilder columns(Column column) {
            insertBuilder.append(column.toString());

            return this;
        }

        public InsertBuilder values(Values values) {
            insertBuilder.append(values.toString());
            return this;
        }

        public Insert build() {
            return new Insert(insertBuilder);
        }
    }

    @Override
    public String toString() {
        return insert.toString();
    }
}
