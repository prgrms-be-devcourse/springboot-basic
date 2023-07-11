package com.wonu606.vouchermanager.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.wonu606.vouchermanager.domain.voucher.PercentageVoucher;
import com.wonu606.vouchermanager.domain.voucher.Voucher;
import com.wonu606.vouchermanager.domain.voucher.discountvalue.PercentageDiscountValue;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DuplicateKeyException;

@DisplayName("LocalMemoryVoucherRepository 테스트")
public class LocalMemoryVoucherVoucherRepositoryTest {

    private LocalMemoryVoucherVoucherRepository repository;
    private Voucher voucher;

    @BeforeEach
    public void setUp() {
        repository = new LocalMemoryVoucherVoucherRepository();

        UUID uuid = UUID.randomUUID();
        PercentageDiscountValue discountValue = new PercentageDiscountValue(20);
        voucher = new PercentageVoucher(uuid, discountValue);
    }

    @AfterEach
    public void tearDown() {
        repository.deleteAll();
    }

    @DisplayName("Save 메소드")
    @Nested
    public class SaveMethod {

        @DisplayName("유효한 바우처이면_저장된바우처가 반환된다.")
        @Test
        public void ValidVoucher_ReturnsSavedVoucher() {
            // When
            Voucher savedVoucher = repository.save(voucher);

            // Then
            assertThat(savedVoucher).isEqualTo(voucher);
        }

        @DisplayName("중복된 UUID 바우처라면_예외가발생한다.")
        @Test
        public void DuplicateUUIDVoucher_ThrowsException() {
            // Given
            repository.save(voucher);

            // When & Then
            assertThatThrownBy(() -> repository.save(voucher))
                    .isInstanceOf(DuplicateKeyException.class)
                    .hasMessage("이미 존재하는 바우처의 uuid입니다. [uuid]: " + voucher.getUuid());
        }
    }

    @DisplayName("findById 메소드")
    @Nested
    public class FindByIdMethod {

        @DisplayName("유효한 UUID이면_바우처가 반환된다.")
        @Test
        public void ValidUUID_ReturnsVoucher() {
            // Given
            repository.save(voucher);

            // When
            Optional<Voucher> foundVoucher = repository.findById(voucher.getUuid());

            // Then
            assertThat(foundVoucher).isPresent();
            assertThat(foundVoucher.get()).isEqualTo(voucher);
        }

        @DisplayName("존재하지않는 UUID라면_빈 Optional이 반환된다.")
        @Test
        public void NonexistentUUID_ReturnsEmptyOptional() {
            // Given
            UUID uuid = UUID.randomUUID();

            // When
            Optional<Voucher> foundVoucher = repository.findById(uuid);

            // Then
            assertThat(foundVoucher).isEmpty();
        }
    }

    @DisplayName("findAll 메소드")
    @Nested
    public class FindAllMethod {

        @DisplayName("모든 바우처를 가져온다.")
        @Test
        public void ReturnsAllVouchers() {
            // Given
            UUID uuid2 = UUID.randomUUID();
            PercentageDiscountValue discountValue2 = new PercentageDiscountValue(30);
            Voucher voucher2 = new PercentageVoucher(uuid2, discountValue2);

            repository.save(voucher);
            repository.save(voucher2);

            // When
            List<Voucher> allVouchers = repository.findAll();

            // Then
            assertThat(allVouchers.size()).isEqualTo(2);
            assertThat(allVouchers).containsExactlyInAnyOrder(voucher, voucher2);
        }
    }

    @DisplayName("deleteById 메소드")
    @Nested
    public class DeleteByIdMethod {

        @DisplayName("UUID가 존재한다면_UUID에 해당하는 바우처를 제거한다.")
        @Test
        public void ExistingUUID_RemovesVoucher() {
            // Given
            repository.save(voucher);

            // When
            repository.deleteById(voucher.getUuid());

            // Then
            assertThat(repository.findById(voucher.getUuid())).isEmpty();
        }

        @DisplayName("UUID가 존재하지 않는다면_아무 일도 일어나지 않는다.")
        @Test
        public void NonExistingUUID_ThrowsException() {
            // Given
            UUID nonExistingUUID = UUID.randomUUID();

            // When & Then
            repository.deleteById(nonExistingUUID);
        }
    }
}
