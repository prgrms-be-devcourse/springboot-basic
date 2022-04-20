package com.blessing333.springbasic.voucher.ui;

import com.blessing333.springbasic.common.ui.CommandOptionConfigurer;
import com.blessing333.springbasic.common.ui.CommandOptions;
import com.blessing333.springbasic.voucher.VoucherServiceCommandOptionType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

class VoucherServiceCommandOptionManagerTest {

    @ParameterizedTest
    @EnumSource(VoucherServiceCommandOptionType.class)
    void test(VoucherServiceCommandOptionType definedOptionType){
        CommandOptions commandOptions = CommandOptionConfigurer.configSupportedCommandOptions(VoucherServiceCommandOptionType.getAvailableCommandOptionType());
        assertTrue(commandOptions.hasOption(definedOptionType.getOptionName()));
    }
}