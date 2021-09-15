package org.prgrms.kdtbespring.command;

import org.prgrms.kdtbespring.voucher.Voucher;
import org.prgrms.kdtbespring.voucher.VoucherService;
import org.prgrms.kdtbespring.voucher.VoucherType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;

public enum Command {
    EXIT {
        @Override
        public boolean run(VoucherService voucherService) {
            System.out.println("Program Exit");
            return false;
        }
    },
    LIST {
        @Override
        public boolean run(VoucherService voucherService) {
            List<Voucher> list = voucherService.list();
            System.out.println("=== Voucher List ===");
            list.forEach(System.out::println);
            return true;
        }
    },

    CREATE {
        @Override
        public boolean run(VoucherService voucherService) throws IOException {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("생성할 바우처를 선택하세요.");
            System.out.println("1. FixedAmountVoucher, 2. PercentDiscountVoucher");
            String select = br.readLine();
            Optional<Voucher> voucher = Optional.empty();
            switch (select) {
                // FixedAmountVoucher 생성
                case "1" -> {
                    voucher = voucherService.create(VoucherType.FixedAmountVoucher);
                }
                // PercentDiscountVoucher 생성
                case "2" -> {
                    voucher = voucherService.create(VoucherType.PercentDiscountVoucher);
                }
                default -> System.out.println("입력 실패(번호를 입력해주세요.)");
            }

            if (voucher.isPresent()){
                System.out.println(voucher.get());
                return true;
            }

            System.out.println("Voucher 생성 실패");
            return true;
        }
    };

    public abstract boolean run(VoucherService voucherService) throws IOException;
}
