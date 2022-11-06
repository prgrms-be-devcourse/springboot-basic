package org.prgrms.kdt.command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.kdt.voucher.VoucherManager;
import org.prgrms.kdt.voucher.VoucherMetaData;

@ExtendWith({MockitoExtension.class})
class CommandExecutorTest {

    @InjectMocks
    private CommandExecutor commandExecutor;

    @Mock
    private VoucherManager voucherManager;

    @Nested
    class create_명령_테스트 {
        @Nested
        class fixed_타입 {
            @Test
            void 값_10_생성() {
                // given
                VoucherMetaData voucherMetaData = new VoucherMetaData("fixed", "10");

                // when
                commandExecutor.create(voucherMetaData);

                // then
            }

            @Test
            void 값_1000_생성() {
                // given
                VoucherMetaData voucherMetaData = new VoucherMetaData("fixed", "1000");

                // when
                commandExecutor.create(voucherMetaData);

                // then
            }

            @Test
            void 값_실수형_생성_예외_발생() {
                // given
                VoucherMetaData voucherMetaData = new VoucherMetaData("fixed", "100.5");

                // when
                Assertions.assertThrows(NumberFormatException.class, () -> commandExecutor.create(voucherMetaData));

                // then
            }

            @Test
            void 값_음수_생성_예외_발생() {
                // given
                VoucherMetaData voucherMetaData = new VoucherMetaData("fixed", "-1");

                // when
                Assertions.assertThrows(NumberFormatException.class, () -> commandExecutor.create(voucherMetaData));

                // then
            }
        }

        @Nested
        class percent_타입 {
            @Test
            void 값_10_생성() {
                // given
                VoucherMetaData voucherMetaData = new VoucherMetaData("percent", "10");

                // when
                commandExecutor.create(voucherMetaData);

                // then
            }

            @Test
            void 값_1000_생성_예외_발생() {
                // given
                VoucherMetaData voucherMetaData = new VoucherMetaData("percent", "1000");

                // when
                Assertions.assertThrows(NumberFormatException.class, () -> commandExecutor.create(voucherMetaData));

                // then
            }

            @Test
            void 값_실수형_생성_예외_발생() {
                // given
                VoucherMetaData voucherMetaData = new VoucherMetaData("percent", "100.5");

                // when
                Assertions.assertThrows(NumberFormatException.class, () -> commandExecutor.create(voucherMetaData));

                // then
            }

            @Test
            void 값_음수_생성_예외_발생() {
                // given
                VoucherMetaData voucherMetaData = new VoucherMetaData("percent", "-1");

                // when
                Assertions.assertThrows(NumberFormatException.class, () -> commandExecutor.create(voucherMetaData));

                // then
            }
        }
    }
}