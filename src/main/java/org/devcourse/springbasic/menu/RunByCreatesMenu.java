package org.devcourse.springbasic.menu;

import org.devcourse.springbasic.io.IODevice;
import org.devcourse.springbasic.voucher.*;
import org.devcourse.springbasic.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
public class RunByCreatesMenu implements RunByMenu {

    private final IODevice ioDevice;
    private final Map<String, Voucher> voucherBeanMap;
    private final VoucherRepository voucherRepository;
    private static final Logger logger = LoggerFactory.getLogger(RunByCreatesMenu.class);


    public RunByCreatesMenu(IODevice ioDevice, Map<String, Voucher> voucherBeanMap, VoucherRepository voucherRepository) {
        this.ioDevice = ioDevice;
        this.voucherBeanMap = voucherBeanMap;
        this.voucherRepository = voucherRepository;
    }

    @Override
    public void run() {

        UUID newVoucherId = UUID.randomUUID();

        // fixed or percent -> 선택
        ioDevice.outputVoucherMenus();
        VoucherType voucherType = ioDevice.inputVoucherMenu();

        String beanName = voucherType.getBeanName();
        Voucher voucherBean = voucherBeanMap.get(beanName);

        // voucher 저장
        if (voucherBean instanceof FixedAmountVoucher) {
            Voucher newVoucher = new FixedAmountVoucher(UUID.randomUUID(), voucherBean.getDiscountRate());
            newVoucherId = voucherRepository.create(newVoucher);

        } else if (voucherBean instanceof PercentDiscountVoucher) {
            Voucher newVoucher = new PercentDiscountVoucher(UUID.randomUUID(), voucherBean.getDiscountRate());
            newVoucherId = voucherRepository.create(newVoucher);
        }

        // voucher 출력
        Optional<Voucher> voucher = voucherRepository.findById(newVoucherId);
        if (voucher.isEmpty()) {
            logger.debug("findById()를 통한 바우처 조회 실패 => ID: {}", newVoucherId);
            throw new IllegalArgumentException("바우처 저장 중, 문제 발생");
        }

        ioDevice.outputVoucher(voucher.get());

    }
}

