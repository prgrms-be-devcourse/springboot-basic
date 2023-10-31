package devcourse.springbootbasic.service;

import devcourse.springbootbasic.domain.voucher.Voucher;
import devcourse.springbootbasic.domain.voucher.VoucherType;
import devcourse.springbootbasic.dto.voucher.VoucherCreateRequest;
import devcourse.springbootbasic.dto.voucher.VoucherFindResponse;
import devcourse.springbootbasic.dto.voucher.VoucherUpdateDiscountValueRequest;
import devcourse.springbootbasic.exception.VoucherErrorMessage;
import devcourse.springbootbasic.exception.VoucherException;
import devcourse.springbootbasic.repository.voucher.VoucherRepository;
import devcourse.springbootbasic.util.UUIDUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static devcourse.springbootbasic.TestDataFactory.generateUnassignedVoucher;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class VoucherServiceTest {

    @InjectMocks
    private VoucherService voucherService;
    @Mock
    private VoucherRepository voucherRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("바우처 조회 시 전체 바우처 Reponse DTO를 반환합니다.")
    void testFindAllVouchers() {
        // Given
        List<Voucher> vouchers = createSampleVouchers();
        when(voucherRepository.findAll()).thenReturn(vouchers);

        // When
        List<VoucherFindResponse> voucherResponses = voucherService.findAllVouchers();

        // Then
        assertThat(voucherResponses).hasSize(vouchers.size());
    }

    @Test
    @DisplayName("바우처 생성 시 생성된 바우처를 반환합니다.")
    void testCreateVoucher() {
        // Given
        VoucherCreateRequest createRequest = new VoucherCreateRequest(VoucherType.FIXED, 100);
        Voucher voucher = createRequest.toEntity(UUIDUtil.generateRandomUUID());
        when(voucherRepository.save(any(Voucher.class))).thenReturn(voucher);

        // When
        Voucher createdVoucher = voucherService.createVoucher(createRequest);

        // Then
        assertThat(createdVoucher).isEqualTo(voucher);
    }

    @Test
    @DisplayName("바우처 할인 금액 수정 시 수정된 바우처를 반환합니다.")
    void testUpdateDiscountValue() {
        // Given
        UUID voucherId = UUIDUtil.generateRandomUUID();
        VoucherUpdateDiscountValueRequest updateRequest = new VoucherUpdateDiscountValueRequest(200);
        Voucher existingVoucher = generateUnassignedVoucher(VoucherType.FIXED, 100);
        when(voucherRepository.findById(voucherId)).thenReturn(Optional.of(existingVoucher));
        when(voucherRepository.update(any(Voucher.class))).thenReturn(true);

        // When
        Voucher updatedVoucher = voucherService.updateDiscountValue(voucherId, updateRequest);

        // Then
        assertThat(updatedVoucher.getDiscountValue()).isEqualTo(updateRequest.getDiscountValue());
    }

    @Test
    @DisplayName("바우처 할인 금액 수정 시 존재하지 않는 바우처를 수정하려고 하면 예외를 발생시킵니다.")
    void testUpdateDiscountValueFailNotFound() {
        // Given
        UUID voucherId = UUIDUtil.generateRandomUUID();
        VoucherUpdateDiscountValueRequest updateRequest = new VoucherUpdateDiscountValueRequest(200);
        when(voucherRepository.findById(voucherId)).thenReturn(Optional.empty());

        // When, Then
        assertThatThrownBy(() -> voucherService.updateDiscountValue(voucherId, updateRequest))
                .isInstanceOf(VoucherException.class)
                .hasMessage(VoucherErrorMessage.NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("바우처 삭제 시 삭제된 바우처를 반환합니다.")
    void testDeleteVoucher() {
        // Given
        Voucher existingVoucher = generateUnassignedVoucher(VoucherType.FIXED, 100);
        when(voucherRepository.findById(existingVoucher.getId())).thenReturn(Optional.of(existingVoucher));

        // When
        Voucher deletedVoucher = voucherService.deleteVoucher(existingVoucher.getId());

        // Then
        assertThat(deletedVoucher).isEqualTo(existingVoucher);
    }

    private List<Voucher> createSampleVouchers() {
        List<Voucher> vouchers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            vouchers.add(generateUnassignedVoucher(VoucherType.FIXED, 100));
        }
        return vouchers;
    }
}
