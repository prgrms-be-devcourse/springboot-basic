package com.devcourse.springbootbasic.application.global.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.MatcherAssert.*;

class UtilsTest {

    @ParameterizedTest
    @DisplayName("바이트 배열을 UUID로 변환한다.")
    @MethodSource("provideBytes")
    void toUUID_ParamByteArray_ReturnUUID(byte[] bytes) {
        var uuid = Utils.toUUID(bytes);
        assertThat(uuid, instanceOf(UUID.class));
    }

    static Stream<Arguments> provideBytes() {
        return Stream.of(
                Arguments.of("207f96ea-2472-4aca-b90b-aa8f15b0583a".getBytes()),
                Arguments.of("207f96ea-2472-4aca-b90b-aa8f15b0583a".getBytes()),
                Arguments.of("207f96ea-2472-4aca-b90b-aa8f15b0583a".getBytes())
        );
    }

}