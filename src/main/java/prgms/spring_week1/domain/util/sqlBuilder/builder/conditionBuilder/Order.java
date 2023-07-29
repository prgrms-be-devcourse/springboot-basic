package prgms.spring_week1.domain.util.sqlBuilder.builder.conditionBuilder;

import prgms.spring_week1.domain.util.type.OrderType;

public class Order {
    private StringBuilder order;

    public Order(StringBuilder orderBuilder) {
        this.order = orderBuilder;
    }

    public static class OrderBuilder {
        private StringBuilder orderBuilder = new StringBuilder();

        public OrderBuilder orderBy(String column, OrderType orderType) {
            orderBuilder.append(" ORDER BY ").append(column).append(" ").append(orderType);
            return this;
        }

        public OrderBuilder and(String column, OrderType orderType) {
            orderBuilder.append(",").append(column).append(" ").append(orderType);
            return this;
        }

        public Order build() {
            return new Order(orderBuilder);
        }
    }

    public String toString() {
        return order.toString();
    }
}
