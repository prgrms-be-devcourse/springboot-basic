package org.prgrms.kdtspringorder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public enum Command {
    CREATE("create", voucherService -> {
        System.out.println("선택하세요 :");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("1. Fixed, 2. Percent");
        String select = null;
        try {
            select = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        VoucherType voucherTypeEnum = VoucherType.from(select);
        Voucher voucher = voucherTypeEnum.accept();
        voucherService.addVoucher(voucher);

//        if (select.equals("1")) {
//
//            Voucher voucher = voucherService.createVoucher(VoucherType.FixedAmountVoucher);
//            voucherService.addVoucher(voucher);
//            System.out.println("생성이 완료 되었습니다.");
//        } else if (select.equals("2")) {
//            Voucher voucher = voucherService.createVoucher(VoucherType.PercentDiscountVoucher);
//            voucherService.addVoucher(voucher);
//            System.out.println("생성이 완료 되었습니다.");
//        } else {
//            System.out.println("다시입력하세요");
//                }
    }),

    LIST("list", voucherService -> { Map<UUID, Voucher> list = voucherService.getAllVouchers();
        int i =1;
        for(Map.Entry<UUID,Voucher> entry : list.entrySet()){
            System.out.println("["+i+"]" + entry.getValue());
            i++;
        }}),
    EXIT("exit", aa -> {
        System.out.println("종료");
        System.exit(0);} );

    private String value;

    private Consumer<VoucherService> consumer;

    public String value(){
        return value;
    }

    Command(String value, Consumer<VoucherService> consumer){
        this.value = value;
        this.consumer = consumer;
    }

    public static Command from(String value){
        return Command.valueOf(value.toUpperCase());
    }

    public void accept(VoucherService voucherService) {
        this.consumer.accept(voucherService);
    }

}
