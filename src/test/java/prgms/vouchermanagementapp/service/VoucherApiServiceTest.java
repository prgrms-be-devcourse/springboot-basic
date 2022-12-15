package prgms.vouchermanagementapp.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import prgms.vouchermanagementapp.model.dto.VoucherCreationDto;
import prgms.vouchermanagementapp.model.dto.VoucherResponseDto;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@SpringBootTest
@Transactional
class VoucherApiServiceTest {

    @Autowired
    private VoucherApiService voucherApiService;

    @Test
    @DisplayName("[조회] 바우처의 아이디로 조회 가능하다.")
    void test_findById() {
        // given
        VoucherCreationDto voucherCreationDto = new VoucherCreationDto("fixed", 10000);
        UUID savedVoucherId = voucherApiService.save(voucherCreationDto);

        // when
        VoucherResponseDto voucherResponseDto = voucherApiService.findById(savedVoucherId);

        // then
        UUID actualVoucherId = voucherResponseDto.voucherId();
        assertThat(actualVoucherId).isEqualTo(savedVoucherId);
    }

    @Test
    @DisplayName("[조회] 모든 바우처를 조회 가능하다.")
    void test_findAll() {
        // given
        List<VoucherCreationDto> voucherCreationDtos = List.of(
                new VoucherCreationDto("fixed", 10000),
                new VoucherCreationDto("percent", 50)
        );
        voucherCreationDtos.forEach(
                voucherCreationDto -> voucherApiService.save(voucherCreationDto)
        );

        // when
        List<VoucherResponseDto> findAllVoucherResponseDtos = voucherApiService.findAll();

        // then
        int actualSize = findAllVoucherResponseDtos.size();
        int expectedSize = voucherCreationDtos.size();

        assertThat(actualSize).isEqualTo(expectedSize);
    }

    @Test
    @DisplayName("[삭제] 삭제한 바우처를 조회시 에러가 발생한다.")
    void test_deleteById() {
        // given
        VoucherCreationDto voucherCreationDto = new VoucherCreationDto("fixed", 10000);
        UUID savedVoucherId = voucherApiService.save(voucherCreationDto);

        // when
        voucherApiService.deleteById(savedVoucherId);

        // then
        assertThatIllegalArgumentException().isThrownBy(() -> {
            voucherApiService.findById(savedVoucherId);
        });
    }
}