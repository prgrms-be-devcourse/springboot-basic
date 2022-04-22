package org.prgrms.kdtspringdemo.domain.voucher;

import org.prgrms.kdtspringdemo.domain.voucher.data.Voucher;
import org.prgrms.kdtspringdemo.domain.voucher.data.FixedAmountVoucher;
import org.prgrms.kdtspringdemo.domain.voucher.data.PercentDiscountVoucher;
import org.prgrms.kdtspringdemo.domain.voucher.repository.VoucherRepository;
import org.prgrms.kdtspringdemo.domain.voucher.storage.VoucherStorage;
import org.prgrms.kdtspringdemo.domain.voucher.type.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class VoucherService {
    private static final Logger logger = LoggerFactory.getLogger(VoucherService.class);
    private final VoucherStorage voucherStorage;
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherStorage voucherStorage, VoucherRepository voucherRepository) {
        this.voucherStorage = voucherStorage;
        this.voucherRepository = voucherRepository;
    }


    public List<Voucher> showVoucherList() {
        List<Voucher> all = voucherRepository.findAll();
        return all;
    }

    public void saveVoucherInDB(int amount, VoucherType type) {
        Voucher voucher = null;

        switch (type) {
            case FIXED -> {
                try {
                    voucher = new FixedAmountVoucher(UUID.randomUUID(), amount);
                } catch (IllegalArgumentException e) {
                    logger.error("Fixed 입력 할인 금액은 범위에 맞지 않은 값을 입력하였습니다.");
                    return;
                }
            }
            case PERCENT -> {
                try {
                    voucher = new PercentDiscountVoucher(UUID.randomUUID(), amount);
                } catch (IllegalArgumentException e) {
                    logger.error("Fixed 입력 할인 금액은 범위에 맞지 않은 값을 입력하였습니다.");
                    return;
                }
            }
        }
        voucherRepository.insert(voucher);
    }


    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }

    public void deleteAll() {
        voucherRepository.deleteAll();
    }

    public String count() {
        return String.valueOf(voucherRepository.count());
    }

    public void updateAmount(Voucher willUpdateVoucher, VoucherType voucherType, int amount) {
        Voucher voucher = willUpdateVoucher.changeTypeAndAmount(voucherType, amount);
        voucherRepository.update(voucher);
    }

    public void updateCustomerId(Voucher willUpdateVoucher, UUID customeId) {
        Voucher voucher;
        if (willUpdateVoucher.getType().equals("FIXED")) {
            voucher = new FixedAmountVoucher(willUpdateVoucher.getVoucherId(), willUpdateVoucher.getAmount(), customeId);
        }else{
            voucher = new PercentDiscountVoucher(willUpdateVoucher.getVoucherId(), willUpdateVoucher.getAmount(), customeId);
        }
        voucherRepository.update(voucher);
    }


    public Voucher findByOrder(int index) {
        List<Voucher> allVoucher = voucherRepository.findAll();
        return allVoucher.get(index);
    }

    public Optional<Voucher> findVoucherListByCustomerId(UUID customerId) {
        return voucherRepository.findByCustomerId(customerId);
    }
}
