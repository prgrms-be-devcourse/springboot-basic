package prgms.spring_week1.domain.util.sqlBuilder.tableBuilder;

import prgms.spring_week1.domain.util.sqlBuilder.conditionBuilder.WhereBuilder;

import static prgms.spring_week1.domain.util.sqlBuilder.LastIndexLength.INVALID_LAST_AND;

public class SetBuilder {
    private StringBuilder setBuilder;

    public SetBuilder(StringBuilder setBuilder) {
        this.setBuilder = setBuilder;
    }

    public WhereBuilder where(String... condition) {
        setBuilder.append(" WHERE ");

        for (String conditionValue : condition) {
            setBuilder.append(conditionValue + " AND ");
        }

        setBuilder.setLength(setBuilder.length() - INVALID_LAST_AND);
        return new WhereBuilder(setBuilder);
    }

    public String build(){
        return setBuilder.toString();
    }
}
