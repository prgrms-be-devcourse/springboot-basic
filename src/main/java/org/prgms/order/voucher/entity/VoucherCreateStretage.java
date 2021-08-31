package org.prgms.order.voucher.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

public class VoucherCreateStretage {
    enum Operator {
        FIXEDAMOUNTVOUCHER("Fixed", FixedAmountVoucher::new),
        PERCENTDISCOUNTVOUCHER("Percent", PercentDiscountVoucher::new);



        private String operator;
        private BiFunction<UUID,Long,Voucher> expression;

        Operator(String type, BiFunction<UUID, Long,Voucher> expression){
            this.operator = type;
            this.expression = expression;
        }

        public Voucher create(UUID customerId, Long amount){
            return this.expression.apply(customerId, amount);
        }
    }

    private static Map<String, Operator> operators = new HashMap<String, Operator>();


    static{
        for(Operator value : Operator.values())
            operators.put(value.operator,value);
    }

    public Voucher createVoucher(String type, UUID customerId, long amount){
        return operators.get(type).create(customerId, amount);
    }
}
