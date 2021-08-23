package org.prgrms.kdt.voucher.service;

import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherType;
import org.prgrms.kdt.voucher.factory.VoucherFactory;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

/**
 * 자신이 직접 어떠한 VoucherRepository 를 쓸지 선택하지 않고 VoucherService 또한 직접 생성하지 않음.
 */
@Service
public class VoucherService {

    // 스프링은 어플리케이션 Context에 등록된 Bean을 코드에서 직접(생성자) 주입하지않고 자동으로 의존관계를 형성해주는 기능이 있습니다.
    // == 자동주입, @AutoWired 이라고 합니다
    // 생성자를 통해 주입이 되었는데 없애고, @AutoWired를 달아줍니다.
    // 그러면 VoucherRepository가 IoC Container에 의해서 자동으로 주입이 됩니다.
    // 필드를 통해서 줄 수 있고, Setter를 통해서 Autowired를 줄 수 도 있습니다.
    // @Autowired
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    // 스프링 4.3 Autowired 안붙여도 자동으로 생성자가 default로 @Autowired 붙여짐
    // 만약 생성자가 2개라면 스프링은 어떤걸로 Bean을 생성해야할지 몰라서 명시적으로 @AutoWired를 걸어야 한다.
    // 생성자 기반 의존관계 주입을 선택해야 하는 이유
    // - 초기화시에 필요한 모든 의존관계가 형성되기 때문에 안전
    // - 잘못된 패턴을 찾을 수 있게 도와줍니다.
    // - 테스트를 쉽게 해줍니다.
    // - 불변성을 확보합니다.
    // - 스프링 공식문서에도 스프링 팀에서 조차 생성자 주입을 옹호합니다.
    public void createVoucher(VoucherType type, UUID voucherId, long policyValue) {
        Voucher voucher = VoucherFactory.createVoucher(type, voucherId, policyValue);
        voucherRepository.insert(voucher);
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException(MessageFormat.format("Can not find a voucher for {0}", voucherId)));
    }

    public List<Voucher> getAllVoucher() {
        return voucherRepository.find();
    }

    public void useVoucher(Voucher voucher) {
    }
}
