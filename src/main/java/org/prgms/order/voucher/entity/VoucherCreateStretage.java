package org.prgms.order.voucher.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

public class VoucherCreateStretage {
    enum Operator {
        FIXEDAMOUNTVOUCHER("Fixed", (amount) -> new FixedAmountVoucher(UUID.randomUUID(),amount)),
        PERCENTDISCOUNTVOUCHER("Percent", (amount) -> new PercentDiscountVoucher(UUID.randomUUID(),amount));



        private String operator;
        private Function<Long,Voucher> expression;

        Operator(String type, Function<Long,Voucher> expression){
            this.operator = type;
            this.expression = expression;
        }

        public Voucher create(Long amount){
            return this.expression.apply(amount);
        }
    }

    private static Map<String, Operator> operators = new HashMap<String, Operator>();


    static{
        for(Operator value : Operator.values())
            operators.put(value.operator,value);
    }

    public Voucher createVoucher(String type, long amount){
        return operators.get(type).create(amount);
    }
}
