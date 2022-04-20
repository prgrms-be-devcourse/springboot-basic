package org.prgrms.voucherapp.global;

import org.prgrms.voucherapp.engine.FixedAmountVoucher;
import org.prgrms.voucherapp.engine.PercentDiscountVoucher;
import org.prgrms.voucherapp.engine.Voucher;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;


/*
* VoucherType : 바우처 타입을 ENUM으로 관리합니다.
* Q. voucherType의 createVoucher를 voucherService에서만 접근 가능하도록 할 수 있나요?
* Service에서 VoucherType의 createVoucher를 사용하는데,VoucherType의 createVoucher 메소드가 public이다보니 Navigator에서도 접근이 가능합니다.
* Navigator에서 VoucherType의 createVoucher에 접근 못하도록 강제할 수 있는 방법이 있을까요? 아니면 접근 되도 상관 없는 문제인가요? Layer를 지키고 싶은 느낌이 듭니다.
* Q. Yaml 파일로 VoucherType의 enum 값들을 설정하도록 하고 싶은데 좋은 방향일까요?
* Q. maxDiscountAmount가 원래는 Voucher 구현체 안에서 static final 변수로 있었는데, 클래스마다 다른 '상수'인 것 같아 enum으로 추출했습니다만, 찜찜합니다.
* static final 안으로 두면 클래스를 정의할 때마다 작성해야하는 코드 중복이 생겨서 enum에 정의한 것인데, 괜찮은 판단일까요?
* */
public enum VoucherType {

    FIX("1", FixedAmountVoucher::new, 10000),
    PERCENT("2", PercentDiscountVoucher::new, 30);

    private final String option;
    private final BiFunction<UUID, Long, Voucher> createInstance;
    private final long maxDiscountAmount;

    VoucherType(String option, BiFunction<UUID, Long, Voucher> createInstance, long maxDiscountAmount) {
        this.option = option;
        this.createInstance = createInstance;
        this.maxDiscountAmount = maxDiscountAmount;
    }

    public static Voucher createVoucher(VoucherType type, UUID uuid, long amount) {
        return type.createInstance.apply(uuid, amount);
    }

    public static Optional<VoucherType> getType(String option) {
        return Arrays.stream(values())
                .filter(o -> o.option.equals(option))
                .findFirst();
    }

    public String getOption() {
        return option;
    }

    public long getMaxDiscountAmount() {
        return maxDiscountAmount;
    }
}
