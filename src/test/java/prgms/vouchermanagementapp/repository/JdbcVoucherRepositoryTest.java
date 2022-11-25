package prgms.vouchermanagementapp.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import prgms.vouchermanagementapp.domain.Customer;
import prgms.vouchermanagementapp.domain.Voucher;
import prgms.vouchermanagementapp.domain.VoucherEntity;
import prgms.vouchermanagementapp.domain.value.Amount;
import prgms.vouchermanagementapp.domain.value.Ratio;
import prgms.vouchermanagementapp.repository.util.EntityMapper;
import prgms.vouchermanagementapp.service.CustomerService;
import prgms.vouchermanagementapp.service.VoucherFactory;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JdbcVoucherRepositoryTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private JdbcVoucherRepository jdbcVoucherRepository;

    private Customer savedCustomer;

    @BeforeEach
    void beforeEach() {
        savedCustomer = customerService.save(new Customer("customer"));
    }

    @DisplayName("고객에게 할당된 바우처를 저장할 수 있다.")
    @Test
    void save() {
        // given
        Voucher voucher1 = VoucherFactory.createVoucher(new Amount(3000));
        Voucher voucher2 = VoucherFactory.createVoucher(new Ratio(50));
        VoucherEntity voucherEntity1 = EntityMapper.toVoucher(voucher1, savedCustomer);
        VoucherEntity voucherEntity2 = EntityMapper.toVoucher(voucher2, savedCustomer);

        // when
        jdbcVoucherRepository.save(voucherEntity1);
        jdbcVoucherRepository.save(voucherEntity2);
        List<VoucherEntity> voucherEntities = jdbcVoucherRepository.findAll();

        // then
        assertThat(voucherEntities.size()).isEqualTo(2);
    }

    @DisplayName("고객이 가진 고정 금액 할인 바우처의 금액을 변경할 수 있다.")
    @Test
    void updateFixedAmountVoucherById() {
        // given
        long originalAmount = 1000;
        Voucher originalVoucher = VoucherFactory.createVoucher(new Amount(originalAmount));
        VoucherEntity originalVoucherEntity = EntityMapper.toVoucher(originalVoucher, savedCustomer);
        jdbcVoucherRepository.save(originalVoucherEntity);

        // when
        long changedAmount = 2000;
        jdbcVoucherRepository.updateFixedAmountVoucherById(originalVoucherEntity.getId(), changedAmount);
        Optional<VoucherEntity> foundVoucherEntity = jdbcVoucherRepository.findVoucherEntityById(originalVoucherEntity.getId());
        assert foundVoucherEntity.isPresent();

        // then
        assertThat(foundVoucherEntity.get().getAmount()).isEqualTo(changedAmount);
    }

    @DisplayName("고객이 가진 고정 비율 할인 바우처의 비율을 변경할 수 있다.")
    @Test
    void updateRatioByCustomerName() {
        // given
        long originalRatio = 50;
        Voucher originalVoucher = VoucherFactory.createVoucher(new Ratio(originalRatio));
        VoucherEntity originalVoucherEntity = EntityMapper.toVoucher(originalVoucher, savedCustomer);
        jdbcVoucherRepository.save(originalVoucherEntity);

        // when
        long changedRatio = 100;
        jdbcVoucherRepository.updatePercentDiscountVoucherById(originalVoucherEntity.getId(), changedRatio);
        Optional<VoucherEntity> foundVoucherEntity = jdbcVoucherRepository.findVoucherEntityById(originalVoucherEntity.getId());
        assert foundVoucherEntity.isPresent();

        // then
        assertThat(foundVoucherEntity.get().getRatio()).isEqualTo(changedRatio);
    }

    @DisplayName("모든 바우처를 삭제할 수 있다.")
    @Test
    void testMethodNameHere() {
        // given
        Voucher voucher1 = VoucherFactory.createVoucher(new Amount(3000));
        Voucher voucher2 = VoucherFactory.createVoucher(new Ratio(50));
        VoucherEntity voucherEntity1 = EntityMapper.toVoucher(voucher1, savedCustomer);
        VoucherEntity voucherEntity2 = EntityMapper.toVoucher(voucher2, savedCustomer);
        jdbcVoucherRepository.save(voucherEntity1);
        jdbcVoucherRepository.save(voucherEntity2);

        // when
        jdbcVoucherRepository.deleteAll();

        // then
        assertThat(jdbcVoucherRepository.findAll()).isEmpty();
    }
}