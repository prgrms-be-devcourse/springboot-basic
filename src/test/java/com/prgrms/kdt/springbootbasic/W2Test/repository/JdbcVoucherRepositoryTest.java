package com.prgrms.kdt.springbootbasic.W2Test.repository;

import com.prgrms.kdt.springbootbasic.VoucherList;
import com.prgrms.kdt.springbootbasic.W2Test.Config;
import com.prgrms.kdt.springbootbasic.voucher.entity.FixedAmountVoucher;
import com.prgrms.kdt.springbootbasic.voucher.entity.PercentDiscountVoucher;
import com.prgrms.kdt.springbootbasic.voucher.entity.Voucher;
import com.prgrms.kdt.springbootbasic.voucher.repository.VoucherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(Config.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("jdbc")
class JdbcVoucherRepositoryTest {

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    DataSource dataSource;

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(),100);
    Voucher percentAmountVoucher = new PercentDiscountVoucher(UUID.randomUUID(),10);

    @BeforeEach
    void setup() {
        jdbcTemplate.update("SET foreign_key_checks = 0;", Collections.emptyMap());
        jdbcTemplate.update("truncate wallet;", Collections.emptyMap());
        jdbcTemplate.update("truncate customers;", Collections.emptyMap());
        jdbcTemplate.update("truncate vouchers;", Collections.emptyMap());
        jdbcTemplate.update("SET foreign_key_checks = 1;", Collections.emptyMap());

        voucherRepository.saveVoucher(fixedAmountVoucher);
        voucherRepository.saveVoucher(percentAmountVoucher);
    }

    @Test
    void saveVoucher(){
        var newFixed = new FixedAmountVoucher(UUID.randomUUID(),90);
        var newPercent = new PercentDiscountVoucher(UUID.randomUUID(),9);
        var insertFixed = voucherRepository.saveVoucher(newFixed);
        var insertPercent = voucherRepository.saveVoucher(newPercent);

        assertThat(insertFixed.get()).as("Voucher").isEqualToComparingFieldByField(newFixed);
        assertThat(insertPercent.get()).as("Voucher").isEqualToComparingFieldByField(newPercent);
    }


    @Test
    void findById(){
        var findFixed = voucherRepository.findById(fixedAmountVoucher.getVoucherId());
        var findPercent = voucherRepository.findById(percentAmountVoucher.getVoucherId());

        assertThat(findFixed.get()).as("Voucher").isEqualToComparingFieldByField(fixedAmountVoucher);
        assertThat(findPercent.get()).as("Voucher").isEqualToComparingFieldByField(percentAmountVoucher);
    }

    @Test
    void findByIdNotExist(){
        var findVoucher = voucherRepository.findById(UUID.randomUUID());

        assertThat(findVoucher.isEmpty()).isTrue();
    }

    @Test
    void getAllVouchers(){
        List<Voucher> assertList = new ArrayList<>();
        assertList.add(fixedAmountVoucher);
        assertList.add(percentAmountVoucher);

        List<Voucher> foundList = voucherRepository.getAllVouchers();
        assertThat(foundList).usingRecursiveFieldByFieldElementComparator()
                .hasSameElementsAs(assertList);
    }

    @Test
    void updateVoucherAmount(){
        fixedAmountVoucher.setAmount(40);

        var updatedVoucher = voucherRepository.updateVoucherAmount(fixedAmountVoucher);
        assertThat(updatedVoucher.get()).as("Voucher").isEqualToComparingFieldByField(fixedAmountVoucher);
    }

    @Test
    void deleteVoucher(){
        var deletedResult = voucherRepository.deleteVoucher(fixedAmountVoucher);
        assertThat(deletedResult).isTrue();

        var findResult = voucherRepository.findById(fixedAmountVoucher.getVoucherId());
        assertThat(findResult.isEmpty()).isTrue();
    }

    @Test
    void findByTypeAndAmount(){
        var foundResult = voucherRepository.findByTypeAndAmount(fixedAmountVoucher.getVoucherType(), fixedAmountVoucher.getDiscountAmount());
        assertThat(foundResult.isEmpty()).isFalse();
        assertThat(foundResult.get()).as("Voucher").isEqualToComparingFieldByField(fixedAmountVoucher);
    }

    @Test
    void findByType(){
        List<Voucher> fixedList = new ArrayList<>();
        fixedList.add(fixedAmountVoucher);
        for(int i = 0; i<5; i++){
            Voucher newVoucher = new FixedAmountVoucher(UUID.randomUUID(), i * 100);
            fixedList.add(newVoucher);
            voucherRepository.saveVoucher(newVoucher);
        }

        List<Voucher> percentList = new ArrayList<>();
        percentList.add(percentAmountVoucher);
        for(int i = 0; i<5; i++){
            Voucher newVoucher = new PercentDiscountVoucher(UUID.randomUUID(), i * 10);
            percentList.add(newVoucher);
            voucherRepository.saveVoucher(newVoucher);
        }

        List<Voucher> fixedFound = voucherRepository.findByType(VoucherList.FIXED_AMOUNT_VOUCHER.getClassName());
        List<Voucher> percentFound = voucherRepository.findByType(VoucherList.PERCENT_DISCOUNT_VOUCHER.getClassName());

        assertThat(fixedFound).usingRecursiveFieldByFieldElementComparator()
                .hasSameElementsAs(fixedList);
        assertThat(percentFound).usingRecursiveFieldByFieldElementComparator()
                .hasSameElementsAs(percentList);
    }

    @Test
    void findOrderByCreatedAt(){
        List<Voucher> orderedList = new ArrayList<>();
        orderedList.add(fixedAmountVoucher);
        orderedList.add(percentAmountVoucher);

        for(int i = 0; i<5; i++){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Voucher newVoucher = new FixedAmountVoucher(UUID.randomUUID(), i * 100);
            orderedList.add(newVoucher);
            voucherRepository.saveVoucher(newVoucher);
        }

        List<Voucher> orderedFound = voucherRepository.findOrderByCreatedAt();

        assertThat(orderedFound).usingRecursiveFieldByFieldElementComparator()
                .hasSameElementsAs(orderedList);
    }

}