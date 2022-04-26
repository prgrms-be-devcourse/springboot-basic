package org.prgrms.kdtspringdemo.domain.voucher;

import com.wix.mysql.EmbeddedMysql;
import org.junit.jupiter.api.*;
import org.prgrms.kdtspringdemo.TestConfiguration;
import org.prgrms.kdtspringdemo.domain.customer.data.Customer;
import org.prgrms.kdtspringdemo.domain.voucher.data.FixedAmountVoucher;
import org.prgrms.kdtspringdemo.domain.voucher.data.PercentDiscountVoucher;
import org.prgrms.kdtspringdemo.domain.voucher.data.Voucher;
import org.prgrms.kdtspringdemo.domain.voucher.repository.VoucherRepository;
import org.prgrms.kdtspringdemo.domain.voucher.type.VoucherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.samePropertyValuesAs;


@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VoucherServiceTest {


    EmbeddedMysql embeddedMysql;

    Customer newCustomer;

    Voucher newPercentVoucher;
    Voucher newFixedVoucher;

    @BeforeAll
    void setup() {
        newFixedVoucher = new FixedAmountVoucher(UUID.randomUUID(), 500, UUID.nameUUIDFromBytes("null".getBytes()));
        newPercentVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 50, UUID.nameUUIDFromBytes("null".getBytes()));

        voucherRepository.insert(newFixedVoucher);
        voucherRepository.insert(newPercentVoucher);
        newCustomer = new Customer(UUID.randomUUID(), "test", "test@gmail.com", LocalDateTime.now(), LocalDateTime.now());
        TestConfiguration.clean(embeddedMysql);
    }
    @AfterEach
    void clean() {
        voucherRepository.deleteAll();
    }

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    VoucherService voucherService;



    @Test
    @Order(1)
    @DisplayName("repository 의 모든 voucher 를 보여주는 리스트 체크")
    public void showVoucherListTest () throws Exception{
        // given
        List<Voucher> vouchers = voucherService.showVoucherList();

        // when

        // then
        assertThat(vouchers.get(0), samePropertyValuesAs(newFixedVoucher));
        assertThat(vouchers.get(1), samePropertyValuesAs(newPercentVoucher));

    }

    @Test
    @Order(2)
    @DisplayName("repository 의 모든 voucher 를 보여주는 리스트 체크")
    public void saveVoucherInDBTest () throws Exception{
        // given

        // when
        voucherService.saveVoucherInDB(100, VoucherType.FIXED);
        voucherService.saveVoucherInDB(50, VoucherType.PERCENT);

        // then
        List<Voucher> vouchers = voucherService.showVoucherList();
        assertThat(vouchers.get(2).getAmount(), is(100));
        assertThat(vouchers.get(3), is(50));
    }

    @Test
    @Order(3)
    @DisplayName("repository 의 모든 voucher 를 보여주는 리스트 에러 체크")
    public void saveVoucherInDBErrorTest () throws Exception{
        // given

        // when
        voucherService.saveVoucherInDB(-100, VoucherType.FIXED);
        voucherService.saveVoucherInDB(150, VoucherType.PERCENT);

        // then
        List<Voucher> vouchers = voucherService.showVoucherList();
        assertThat(vouchers.size(), is(4));
    }

    @Test
    @Order(4)
    @DisplayName("repository 의 모든 voucher 개수 count 를 부여주는 테스트")
    public void countTest () throws Exception{
        // given

        // when
        String count = voucherService.count();

        // then
        assertThat(count, is(4));
    }

    @Test
    @Order(5)
    @DisplayName("repository 의 모든 voucher 를 제거하는 delete 테스트")
    public void deleteAllTest () throws Exception{
        // given

        // when
        voucherService.deleteAll();

        // then
        assertThat(voucherService.count(), is(0));
    }

}