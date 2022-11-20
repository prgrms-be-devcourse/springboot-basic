package prgms.vouchermanagementapp.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import prgms.vouchermanagementapp.domain.Customer;
import prgms.vouchermanagementapp.domain.Voucher;
import prgms.vouchermanagementapp.domain.VoucherEntity;
import prgms.vouchermanagementapp.domain.value.Amount;
import prgms.vouchermanagementapp.domain.value.Ratio;
import prgms.vouchermanagementapp.repository.util.EntityMapper;
import prgms.vouchermanagementapp.service.CustomerManager;
import prgms.vouchermanagementapp.service.VoucherCreationFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JdbcVouchersTest {

    @Autowired
    private CustomerManager customerManager;

    @Autowired
    private JdbcVouchers jdbcVouchers;

    private Customer savedCustomer;

    private static Stream<Arguments> provideSingleFixedAmountVoucherOf3000() {
        return Stream.of(Arguments.of(VoucherCreationFactory.createVoucher(new Amount(3000))));
    }

    private static Stream<Arguments> provideSinglePercentDiscountVoucherOf50() {
        return Stream.of(Arguments.of(VoucherCreationFactory.createVoucher(new Ratio(50))));
    }

    @BeforeEach
    void beforeEach() {
        savedCustomer = customerManager.save(new Customer("customer"));
    }

    @DisplayName("고객에게 할당된 바우처를 저장할 수 있다.")
    @Test
    void save() {
        // given
        Voucher voucher1 = VoucherCreationFactory.createVoucher(new Amount(3000));
        Voucher voucher2 = VoucherCreationFactory.createVoucher(new Ratio(50));
        VoucherEntity voucherEntity1 = EntityMapper.toVoucher(voucher1, savedCustomer);
        VoucherEntity voucherEntity2 = EntityMapper.toVoucher(voucher2, savedCustomer);

        // when
        jdbcVouchers.save(voucherEntity1);
        jdbcVouchers.save(voucherEntity2);
        List<VoucherEntity> voucherEntities = jdbcVouchers.findAll();

        // then
        assertThat(voucherEntities.size()).isEqualTo(2);
    }

    @DisplayName("고객이 가진 고정 금액 할인 바우처의 금액을 변경할 수 있다.")
    @Test
    void updateFixedAmountVoucherById() {
        // given
        long originalAmount = 1000;
        Voucher originalVoucher = VoucherCreationFactory.createVoucher(new Amount(originalAmount));
        VoucherEntity originalVoucherEntity = EntityMapper.toVoucher(originalVoucher, savedCustomer);
        jdbcVouchers.save(originalVoucherEntity);

        // when
        long changedAmount = 2000;
        jdbcVouchers.updateFixedAmountVoucherById(originalVoucherEntity.getId(), changedAmount);
        Optional<VoucherEntity> foundVoucherEntity = jdbcVouchers.findVoucherEntityById(originalVoucherEntity.getId());
        assert foundVoucherEntity.isPresent();

        // then
        assertThat(foundVoucherEntity.get().getAmount()).isEqualTo(changedAmount);
    }

    @DisplayName("고객이 가진 고정 비율 할인 바우처의 비율을 변경할 수 있다.")
    @Test
    void updateRatioByCustomerName() {
        // given
        long originalRatio = 50;
        Voucher originalVoucher = VoucherCreationFactory.createVoucher(new Ratio(originalRatio));
        VoucherEntity originalVoucherEntity = EntityMapper.toVoucher(originalVoucher, savedCustomer);
        jdbcVouchers.save(originalVoucherEntity);

        // when
        long changedRatio = 100;
        jdbcVouchers.updatePercentDiscountVoucherById(originalVoucherEntity.getId(), changedRatio);
        Optional<VoucherEntity> foundVoucherEntity = jdbcVouchers.findVoucherEntityById(originalVoucherEntity.getId());
        assert foundVoucherEntity.isPresent();

        // then
        assertThat(foundVoucherEntity.get().getRatio()).isEqualTo(changedRatio);
    }

    @DisplayName("모든 바우처를 삭제할 수 있다.")
    @Test
    void testMethodNameHere() {
        // given
        Voucher voucher1 = VoucherCreationFactory.createVoucher(new Amount(3000));
        Voucher voucher2 = VoucherCreationFactory.createVoucher(new Ratio(50));
        VoucherEntity voucherEntity1 = EntityMapper.toVoucher(voucher1, savedCustomer);
        VoucherEntity voucherEntity2 = EntityMapper.toVoucher(voucher2, savedCustomer);
        jdbcVouchers.save(voucherEntity1);
        jdbcVouchers.save(voucherEntity2);

        // when
        jdbcVouchers.deleteAll();

        // then
        assertThat(jdbcVouchers.findAll()).isEmpty();
    }
}