package com.example.voucher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.voucher.config.VoucherConfig;
import com.example.voucher.domain.Voucher;
import com.example.voucher.domain.dto.VoucherDto;
import com.example.voucher.service.VoucherService;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class VoucherApplicationTest {
    @Mock
    private VoucherConfig voucherConfig;

    @Mock
    private CommandHandler commandHandler;

    @Mock
    private VoucherService voucherService;

    private VoucherApplication voucherApplication;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(voucherConfig.commandHandler()).thenReturn(commandHandler);
        voucherApplication = new VoucherApplication(voucherService, commandHandler);
    }

    @Test
    public void testRun_CreateCommand() {
        // Arrange
        Command createCommand = Command.CREATE;
        VoucherDto voucherDto = new VoucherDto.Builder()
                .withVoucherId(UUID.randomUUID())
                .withAmount(100)
                .build();
        when(commandHandler.handleCommand()).thenReturn(createCommand);
        when(commandHandler.handleCreateCommand()).thenReturn(voucherDto);

        // Act
        String input = "create\n100.0";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        voucherApplication.run();

        // Assert
        assertThat(commandHandler).isNotNull()
                .extracting(CommandHandler::handleCommand).isEqualTo(createCommand);
        assertThat(commandHandler).isNotNull()
                .extracting(CommandHandler::handleCreateCommand).isEqualTo(voucherDto);
        verify(voucherService).createVoucher(voucherDto);
    }

    @Test
    public void testRun_ListCommand() {
        // Arrange
        Command listCommand = Command.LIST;
        List<Voucher> vouchers = List.of();
        when(commandHandler.handleCommand()).thenReturn(listCommand);

        // Act
        String input = "list";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        voucherApplication.run();

        // Assert
        assertThat(commandHandler).isNotNull()
                .extracting(CommandHandler::handleCommand).isEqualTo(listCommand);
        verify(commandHandler).handleListCommand(vouchers);
        verify(voucherService).getAllVouchers();
    }
}
