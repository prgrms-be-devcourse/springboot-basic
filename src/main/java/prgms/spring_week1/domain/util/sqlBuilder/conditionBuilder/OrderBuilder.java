package prgms.spring_week1.domain.util.sqlBuilder.conditionBuilder;

import prgms.spring_week1.domain.util.type.OrderType;

public class OrderBuilder {
    private StringBuilder orderBuilder;

    public OrderBuilder(StringBuilder orderBuilder) {
        this.orderBuilder = orderBuilder;
    }

    public OrderBuilder orderBy(String column, OrderType orderType){
        orderBuilder.append(",").append(column).append(" ").append(orderType);
        return this;
    }



    public String build(){
        return orderBuilder.toString();
    }
}
