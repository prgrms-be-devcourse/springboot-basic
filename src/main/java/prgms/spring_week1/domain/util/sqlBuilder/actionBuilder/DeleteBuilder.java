package prgms.spring_week1.domain.util.sqlBuilder.actionBuilder;

import prgms.spring_week1.domain.util.sqlBuilder.tableBuilder.FromBuilder;

public class DeleteBuilder {
    private StringBuilder deleteSqlBuilder = new StringBuilder();

    public DeleteBuilder delete() {
        deleteSqlBuilder.append("DELETE ");
        return this;
    }

    public FromBuilder from(String table) {
        return new FromBuilder(deleteSqlBuilder.append(" FROM ").append(table));
    }

    public String build(){
        return deleteSqlBuilder.toString();
    }
}
