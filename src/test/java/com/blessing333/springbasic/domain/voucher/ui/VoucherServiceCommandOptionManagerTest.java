package com.blessing333.springbasic.domain.voucher.ui;

import com.blessing333.springbasic.console.ui.CommandOptionConfigurer;
import com.blessing333.springbasic.console.ui.CommandOptions;
import com.blessing333.springbasic.console.voucher.ui.VoucherCommandOptionType;
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