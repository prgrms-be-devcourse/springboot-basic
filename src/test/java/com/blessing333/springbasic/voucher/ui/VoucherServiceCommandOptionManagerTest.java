package com.blessing333.springbasic.voucher.ui;

import com.blessing333.springbasic.console_app.ui.CommandOptionConfigurer;
import com.blessing333.springbasic.console_app.ui.CommandOptions;
import com.blessing333.springbasic.console_app.voucher.ui.VoucherCommandOptionType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

class VoucherServiceCommandOptionManagerTest {

    @ParameterizedTest
    @EnumSource(VoucherCommandOptionType.class)
    void test(VoucherCommandOptionType definedOptionType){
        CommandOptions commandOptions = CommandOptionConfigurer.configSupportedCommandOptions(VoucherCommandOptionType.getAvailableCommandOptionType());
        assertTrue(commandOptions.hasOption(definedOptionType.getOptionName()));
    }
}