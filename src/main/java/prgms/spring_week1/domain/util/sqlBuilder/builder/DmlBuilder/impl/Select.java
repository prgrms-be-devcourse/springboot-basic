package prgms.spring_week1.domain.util.sqlBuilder.builder.DmlBuilder.impl;

import prgms.spring_week1.domain.util.sqlBuilder.builder.DmlBuilder.Dml;
import prgms.spring_week1.domain.util.sqlBuilder.builder.conditionBuilder.Order;
import prgms.spring_week1.domain.util.sqlBuilder.builder.conditionBuilder.Where;
import prgms.spring_week1.domain.util.type.TableType;

import static prgms.spring_week1.domain.util.sqlBuilder.LastIndexLength.INVALID_LAST_COMMA;

public class Select implements Dml {
    private StringBuilder select;

    public Select(StringBuilder selectBuilder) {
        this.select = selectBuilder;
    }

    public static class SelectBuilder {
        private StringBuilder selectBuilder = new StringBuilder();

        public SelectBuilder select(String... column) {
            selectBuilder.append("SELECT ");

            for (String value : column) {
                selectBuilder.append(value).append(",");
            }

            selectBuilder.setLength(selectBuilder.length() - INVALID_LAST_COMMA);

            return this;
        }

        public SelectBuilder selectAll(String... column) {
            selectBuilder.append("SELECT *");
            return this;
        }

        public SelectBuilder from(TableType table) {
            selectBuilder.append(" FROM ").append(table);
            return this;
        }

        public SelectBuilder where(Where where) {
            selectBuilder.append(where.toString());
            return this;
        }

        public SelectBuilder orderBy(Order order) {
            selectBuilder.append(order.toString());
            return this;
        }

        public Select build() {
            return new Select(selectBuilder);
        }
    }

    @Override
    public String toString() {
        return select.toString();
    }
}

