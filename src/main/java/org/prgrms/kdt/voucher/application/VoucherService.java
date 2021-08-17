package org.prgrms.kdt.voucher.application;

import org.prgrms.kdt.exception.InvalidArgumentException;
import org.prgrms.kdt.order.repository.OrderRepository;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.repository.VoucherRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.prgrms.kdt.exception.InvalidArgumentException.*;

public class VoucherService {
    private final String FIXED = "Fixed";
    private final String PERCENT = "Percent";
    private final int INPUT_SIZE = 2;
    private final int TYPE_INDEX = 0;
    private final int VALUE_INDEX = 1;
    private final String SPLIT_CODE = " ";
    private final VoucherRepository voucherRepository;
    private final OrderRepository orderRepository;
    private final List<Voucher> vouchers = new ArrayList<>();

    public VoucherService(VoucherRepository voucherRepository, OrderRepository orderRepository) {
        this.voucherRepository = voucherRepository;
        this.orderRepository = orderRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository.findById(voucherId)
                .orElseThrow(() -> new RuntimeException("Can not find a voucher for" + voucherId));
    }

    public void addVoucher(Voucher voucher) {
        vouchers.add(voucher);
    }

    public List<Voucher> allVoucher() {
        return vouchers;
    }

    public void useVoucher(Voucher voucher) {
    }

    public Voucher choiceVoucher(String userInputMessage) {
        List<String> typeAndValue = typeAndValue(userInputMessage);

        if (FIXED.equals(typeAndValue.get(TYPE_INDEX))) {
            return new FixedAmountVoucher(UUID.randomUUID(), Long.parseLong(typeAndValue.get(VALUE_INDEX)));
        }

        if (PERCENT.equals(typeAndValue.get(TYPE_INDEX))) {
            return new PercentDiscountVoucher(UUID.randomUUID(), Long.parseLong(typeAndValue.get(VALUE_INDEX)));
        }

        throw new InvalidArgumentException(ErrorMessage.NOT_CORRECT_INPUT_MESSAGE);
    }

    private List<String> typeAndValue(String userInputMessage) {
        List<String> typeAndValue = Arrays.asList(userInputMessage.split(SPLIT_CODE));
        if (typeAndValue.size() != INPUT_SIZE) {
            throw new InvalidArgumentException(ErrorMessage.NOT_CORRECT_INPUT_MESSAGE);
        }
        return typeAndValue;
    }
}
