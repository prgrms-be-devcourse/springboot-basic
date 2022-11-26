package com.example.springbootbasic.controller.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static com.example.springbootbasic.controller.request.RequestCode.FAIL;
import static com.example.springbootbasic.controller.request.RequestCode.SUCCESS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class RequestBodyTest {

    @Test
    @DisplayName("요청 실패 판별 성공")
    void whenFailRequestBodyThenSuccessTest() {
        assertThat(RequestBody.fail(1).getCode(), is(FAIL));
    }

    @Test
    @DisplayName("요청 성공 판별 성공")
    void whenSuccessRequestBodyThenSuccessTest() {
        assertThat(RequestBody.success(1).getCode(), is(SUCCESS));
    }


    @ParameterizedTest(name = "[{index}] Request Data Success Test")
    @MethodSource("whenRequestBodyDataThenSuccessDummy")
    @DisplayName("요청 데이터 저장 성공")
    void whenRequestBodyDataThenSuccessTest(char charData, int integerData,
                                            double doubleData, long longData, String stringData,
                                            List<Integer> integerListData, Set<Integer> integerSetData,
                                            Map<Integer, Integer> integerMapData) {
        RequestTestData request =
                RequestTestData.of(charData, integerData,
                doubleData, longData, stringData,
                integerListData, integerSetData, integerMapData);

        assertThat(request.charData, is(charData));
        assertThat(request.integerData, is(integerData));
        assertThat(request.doubleData, is(doubleData));
        assertThat(request.longData, is(longData));
        assertThat(request.stringData, is(stringData));
        assertThat(request.integerListData, is(integerListData));
        assertThat(request.integerSetData, is(integerSetData));
        assertThat(request.integerMapData, is(integerMapData));
    }

    static Stream<Arguments> whenRequestBodyDataThenSuccessDummy() {
        return Stream.of(
            Arguments.arguments('a', 100, 123.4D, 30000L, "testtestttttest",
                    List.of(1,2,3,4), Set.of(1,2,3,4), Map.of(1,4231,2423,2423,3,3,47564,4534)),
                Arguments.arguments('a', 100, 13423.4D, 30000L, "testtegfdgstttttest",
                        List.of(1,2222,32222,4), Set.of(1,222,3,4), Map.of(1,4234,2,2343,3443,3,4534,4)),
                Arguments.arguments( 'a', 100, 1623.4434D, 30000L, "tegfdsgttes14tttttest",
                        List.of(1,2,3,30,104), Set.of(1,2,343,454), Map.of(54231,45631,2,86752,6363,3534,62,4)),
                Arguments.arguments( 'a', 100, 123.4D, 30000L, "testtegdfgttttesgdfsgdsfgt",
                        List.of(10,21,3,4), Set.of(16341,42342,3,4), Map.of(143124,1,2,2867,3236,30568,4,4))
        );
    }

    static class RequestTestData {
        private final char charData;
        private final int integerData;
        private final double doubleData;
        private final long longData;
        private final String stringData;
        private final List<Integer> integerListData;
        private final Set<Integer> integerSetData;
        private final Map<Integer, Integer> integerMapData;

        private RequestTestData(char charData, int integerData, double doubleData, long longData, String stringData, List<Integer> integerListData, Set<Integer> integerSetData, Map<Integer, Integer> integerMapData) {
            this.charData = charData;
            this.integerData = integerData;
            this.doubleData = doubleData;
            this.longData = longData;
            this.stringData = stringData;
            this.integerListData = integerListData;
            this.integerSetData = integerSetData;
            this.integerMapData = integerMapData;
        }

        public static RequestTestData of(char charData, int integerData, double doubleData, long longData, String stringData, List<Integer> integerListData, Set<Integer> integerSetData, Map<Integer, Integer> integerMapData) {
            return new RequestTestData(charData, integerData, doubleData, longData, stringData, integerListData, integerSetData, integerMapData);
        }
    }
}