package prgms.spring_week1.domain.util.sqlBuilder.conditionBuilder;

import prgms.spring_week1.domain.util.type.OrderType;

public class WhereBuilder {
    private StringBuilder whereBuilder;

    public WhereBuilder(StringBuilder whereBuilder) {
        this.whereBuilder = whereBuilder;
    }

    public WhereBuilder AND(String condition){
        whereBuilder.append(" AND ").append(condition);
        return this;
    }

    public WhereBuilder OR(String condition){
        whereBuilder.append(" OR ").append(condition);
        return this;
    }

    public OrderBuilder orderBy(String column, OrderType orderType){
        whereBuilder.append(" ORDER BY ").append(column).append(" ").append(orderType);
        return new OrderBuilder(whereBuilder);
    }

    public String build(){
        return whereBuilder.toString();
    }
}
