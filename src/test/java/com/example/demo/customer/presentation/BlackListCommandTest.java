package com.example.demo.customer.presentation;

import com.example.demo.common.io.Output;
import com.example.demo.customer.presentation.dto.BlackCustomer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BlackListCommandTest {

    @Mock
    private Output output;

    private Set<BlackCustomer> blackList;
    private BlackListCommand blackListCommand;

    @BeforeEach
    void setUp() {
        blackList = new HashSet<>();
        blackList.add(new BlackCustomer("test1@example.com"));
        blackList.add(new BlackCustomer("test2@example.com"));

        blackListCommand = new BlackListCommand(blackList, output);
    }

    @Test
    @DisplayName("blacklist 명령 테스트")
    void execute() {
        // Given
        String expectedMessage1 = "=== Blacklist ===";
        String expectedMessage2 = "test1@example.com";
        String expectedMessage3 = "test2@example.com";

        // When
        blackListCommand.execute();

        // Then
        verify(output, times(1)).printLine(expectedMessage1);
        verify(output, times(1)).printLine(expectedMessage2);
        verify(output, times(1)).printLine(expectedMessage3);
    }
}
