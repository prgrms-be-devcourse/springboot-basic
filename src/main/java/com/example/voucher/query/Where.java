package com.example.voucher.query;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.example.voucher.query.operator.Operator;

public class Where {

    private final String baseConditions;
    private final List<String> logicalConditions;
    private final String query;

    private Where(String baseConditions, List<String> logicalConditions) {
        this.baseConditions = baseConditions;
        this.logicalConditions = logicalConditions;
        query = makeQuery();
    }

    public String getQuery() {
        return query;
    }

    private String makeQuery() {
        String baseCondition = this.baseConditions;
        String logicalConditions = this.logicalConditions.stream()
            .map(condition -> " " + condition)
            .collect(Collectors.joining());

        return baseCondition.concat(logicalConditions).trim();
    }


    public static WhereCriteria builder() {
        return new WhereCriteria();
    }

    public static class WhereCriteria {

        private String baseConditions;

        public WhereCriteria() {
        }

        public LogicalConditionCriteria where(Operator operator) {
            this.baseConditions = String.format("WHERE %s %s %s", operator.getColumn(), operator.getOperate(),
                operator.getValue());

            return new LogicalConditionCriteria(baseConditions);
        }
    }

    public static class LogicalConditionCriteria {

        private String baseConditions;
        private List<String> logicalConditions = new ArrayList<>();

        public LogicalConditionCriteria(String baseConditions) {
            this.baseConditions = baseConditions;
        }

        public LogicalConditionCriteria or(Operator operator) {
            logicalConditions.add(
                String.format("OR %s %s %s", operator.getColumn(), operator.getOperate(), operator.getValue()));

            return this;
        }

        public LogicalConditionCriteria and(Operator operator) {
            logicalConditions.add(
                String.format("AND %s %s %s", operator.getColumn(), operator.getOperate(), operator.getValue()));

            return this;
        }

        public Where build() {
            return new Where(baseConditions, logicalConditions);
        }

    }


}
