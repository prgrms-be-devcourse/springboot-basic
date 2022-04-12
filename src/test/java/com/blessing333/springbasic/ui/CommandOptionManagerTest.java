package com.blessing333.springbasic.ui;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CommandOptionManagerTest {
    private final CommandOptionManager optionManager = new CommandOptionManager();

    //CommandOptionManager는 생성과 동시에 CommandOptionType에 정의된 모든 CommandOption을 CommandOptions에 추가해야한다.
    @ParameterizedTest
    @EnumSource(CommandOptionType.class)
    void test(CommandOptionType definedOptionType){
        CommandOptions supportedCommandOptions = optionManager.getSupportedOptions();
        assertTrue(supportedCommandOptions.hasOption(definedOptionType.getOptionName()));
    }
}