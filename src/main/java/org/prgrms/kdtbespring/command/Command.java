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

            Optional<VoucherType> selectResult = selectVoucherType(select);
            selectResult.ifPresentOrElse(
                    voucherType -> {
                        Optional<Voucher> voucher = voucherService.create(voucherType);
                        voucher.ifPresent(System.out::println);
                    },
                    () -> {
                        System.out.println("입력 실패(번호를 입력해주세요.)");
                        System.out.println("Voucher 생성 실패");
                    }
            );

            return true;
        }
    };

    Optional<VoucherType> selectVoucherType(String select) {
        return switch (select) {
            case "1" -> Optional.of(VoucherType.FixedAmountVoucher);
            case "2" -> Optional.of(VoucherType.PercentDiscountVoucher);
            default -> Optional.empty();
        };
    }

    public abstract boolean run(VoucherService voucherService) throws IOException;
}
