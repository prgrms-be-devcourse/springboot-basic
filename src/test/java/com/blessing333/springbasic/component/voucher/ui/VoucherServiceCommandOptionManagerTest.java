package com.blessing333.springbasic.component.voucher.ui;

import com.blessing333.springbasic.ui.CommandOptionFactory;
import com.blessing333.springbasic.ui.CommandOptions;
import com.blessing333.springbasic.voucher.VoucherServiceCommandOptionType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

class VoucherServiceCommandOptionManagerTest {

    @ParameterizedTest
    @EnumSource(VoucherServiceCommandOptionType.class)
    void test(VoucherServiceCommandOptionType definedOptionType){
        CommandOptions commandOptions = CommandOptionFactory.createCommandOptions(VoucherServiceCommandOptionType.getAvailableCommandOptionType());
        assertTrue(commandOptions.hasOption(definedOptionType.getOptionName()));
    }
}