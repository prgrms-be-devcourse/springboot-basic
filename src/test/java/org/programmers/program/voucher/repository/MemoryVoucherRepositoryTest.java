package org.programmers.program.voucher.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.program.voucher.model.FixedAmountVoucher;
import org.programmers.program.voucher.model.PercentDiscountVoucher;
import org.programmers.program.voucher.model.Voucher;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryVoucherRepositoryTest {
    // @Autowired
    static VoucherRepository repository;

    @BeforeAll
    static void setup(){
        repository = new MemoryVoucherRepository();
    }

    @Test
    @DisplayName("데이터 삽입 테스트")
    void insertTest(){
        Voucher v1 = new PercentDiscountVoucher(UUID.randomUUID(), 30L);
        Voucher v2 = repository.insert(v1);

        assertThat(v1.getVoucherId()).isEqualTo(v2.getVoucherId());
    }

    @Test
    @DisplayName("ID로 찾기 테스트")
    void findByIdTest(){
        UUID id = UUID.randomUUID();
        repository.insert(new PercentDiscountVoucher(id, 30L));
        Voucher v1 = repository.findById(id).isPresent() ? repository.findById(id).get() : null;

        assertThat(v1).isNotNull();
        assertThat(v1.getVoucherId()).isEqualTo(id);
    }

    @Test
    @DisplayName("레포지토리에서 전부 가져오는 테스트")
    void findAllTest(){
        int expected_size = 10;
        for(int i = 0; i < expected_size; i++)
            repository.insert(new FixedAmountVoucher(UUID.randomUUID(), (long)i));

        assertThat(repository.findAll().size()).isEqualTo(expected_size);}
}