package prgms.spring_week1.domain.util.sqlBuilder.tableBuilder;

import prgms.spring_week1.domain.util.sqlBuilder.conditionBuilder.OrderBuilder;
import prgms.spring_week1.domain.util.sqlBuilder.conditionBuilder.WhereBuilder;
import prgms.spring_week1.domain.util.type.OrderType;

public class FromBuilder {
    private StringBuilder fromBuilder;

    public FromBuilder(StringBuilder fromBuilder) {
        this.fromBuilder = fromBuilder;
    }

    public WhereBuilder where(String condition) {
        return new WhereBuilder(fromBuilder.append(" WHERE ").append(condition));
    }

    public OrderBuilder orderBy(String column, OrderType orderType){
        fromBuilder.append(" ORDER BY ").append(column).append(" ").append(orderType);
        return new OrderBuilder(fromBuilder);
    }

    public String build(){
        return fromBuilder.toString();
    }
}
