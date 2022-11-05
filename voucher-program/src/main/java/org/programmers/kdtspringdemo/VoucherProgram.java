package org.programmers.kdtspringdemo;

import org.programmers.kdtspringdemo.config.AppConfiguration;
import org.programmers.kdtspringdemo.io.Input;
import org.programmers.kdtspringdemo.type.OptionType;
import org.programmers.kdtspringdemo.type.VoucherType;
import org.programmers.kdtspringdemo.voucher.model.FixedAmountVoucher;
import org.programmers.kdtspringdemo.voucher.model.PercentDiscountVoucher;
import org.programmers.kdtspringdemo.voucher.repository.VoucherRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class VoucherProgram implements Runnable {

    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
    VoucherRepository voucherRepository = applicationContext.getBean(VoucherRepository.class);
    private final Input input = new Console();

    private Boolean isRunnable = true;

    @Override
    public void run() {

        while (isRunnable) {
            String optionDescription = "\n=== Voucher Program ===\nType exit to exit the program.\nType create to create a new voucher.\nType list to list all vouchers.\n=> ";
            String inputString = input.selectOption(optionDescription);
            Optional<OptionType> optionType = OptionType.checkType(inputString);

            if (optionType.isEmpty()) {
                throw new RuntimeException("잘못된 입력값입니다. [ exit, create, list ] 중 하나를 입력해야 합니다.");
            }

            switch (optionType.get()) {
                case EXIT -> isRunnable = false;
                case CREATE -> createVoucher(voucherRepository);
                case LIST -> getVoucherInfoList(voucherRepository);
            }
        }

    }

    private void getVoucherInfoList(VoucherRepository voucherRepository) {
        String voucherListDescription = "\n=== Voucher List ===";
        System.out.println(voucherListDescription);
        voucherRepository.getVoucherList().forEach(System.out::println);
    }

    private void createVoucher(VoucherRepository voucherRepository) {
        String voucherDescription = "\n=== Voucher 선택 ===\nFixedAmountVoucher 을 생성하려면 Fixed 를 입력하세요.\nPercentDiscountVoucher 을 생성하려면 Percent 를 입력하세요.\n=> ";
        String inputString = input.selectVoucher(voucherDescription);
        Optional<VoucherType> voucherType = VoucherType.checkVoucherType(inputString);

        if (voucherType.isEmpty()) {
            throw new RuntimeException("잘못된 입력값입니다. [ fixed, percent ] 중 하나를 입력해야 합니다.");
        }

        String voucherInfoDescription = "\n=== Voucher 의 할인 정보(할인액 또는 할인율)를 입력하세요. 단위 없이 숫자만 입력하세요. ===\n=>  ";
        Optional<Long> voucherInfo = input.getVoucherInfo(voucherInfoDescription);

        if (voucherInfo.isEmpty()) {
            throw new RuntimeException("잘못된 입력값입니다. 숫자를 입력하세요.");
        }

        switch (voucherType.get()) {
            case FIXED -> voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), voucherInfo.get()));
            case PERCENT -> voucherRepository.insert(new PercentDiscountVoucher(UUID.randomUUID(), voucherInfo.get()));
        }
    }
}
