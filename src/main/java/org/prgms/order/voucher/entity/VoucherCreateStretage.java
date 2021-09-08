package org.prgms.order.voucher.entity;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class VoucherCreateStretage {
    enum Operator {
        FIXEDAMOUNTVOUCHER(VoucherIndexType.FIXED,voucherData ->
                FixedAmountVoucher.builder().
                    voucherId(voucherData.getVoucherId()).
                    amount(voucherData.getAmount()).
                    createdAt(voucherData.getCreatedAt())
                    .expiredAt(voucherData.getExpiredAt()).build()),

        PERCENTDISCOUNTVOUCHER(VoucherIndexType.PERCENT, voucherData ->
                PercentDiscountVoucher.builder().
                    voucherId(voucherData.getVoucherId()).
                    amount(voucherData.getAmount()).
                    createdAt(voucherData.getCreatedAt())
                    .expiredAt(voucherData.getExpiredAt()).build());



        private VoucherIndexType operator;
        private Function<VoucherData,Voucher> expression;

        Operator(VoucherIndexType type, Function<VoucherData, Voucher> expression){
            this.operator = type;
            this.expression = expression;
        }

        public Voucher create(VoucherData voucherData){
            return this.expression.apply(voucherData);
        }
    }

    private static Map<VoucherIndexType, Operator> operators = new HashMap<VoucherIndexType, Operator>();

    static{
        for(Operator value : Operator.values())
            operators.put(value.operator,value);
    }

    public Voucher createVoucher(VoucherIndexType type, VoucherData voucherData){
        return operators.get(type).create(voucherData);
    }
}