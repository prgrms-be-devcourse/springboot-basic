package com.dojinyou.devcourse.voucherapplication.voucher;

import com.dojinyou.devcourse.voucherapplication.common.exception.NotFoundException;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.Voucher;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherAmount;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherType;
import com.dojinyou.devcourse.voucherapplication.voucher.dto.VoucherResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.JavaTimeConversionPattern;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(VoucherApiController.class)
@DisplayName("VoucherApiController 테스트")
class VoucherApiControllerTest {
    public static final String BASE_API_URL = "/api/v1/vouchers";
    @Autowired
    private MockMvc mvc;

    @MockBean
    private VoucherService voucherService;

    @Autowired
    private ObjectMapper objectMapper;

    private final LocalDateTime testTime = LocalDateTime.of(2022, 5, 8,
                                                            1, 13, 20, 0);

    private Voucher insertedVoucher1 = Voucher.of(1L, VoucherType.FIXED,
                                                  VoucherAmount.of(VoucherType.FIXED, 100),
                                                  testTime, testTime);
    private Voucher insertedVoucher2 = Voucher.of(2L, VoucherType.PERCENT,
                                                  VoucherAmount.of(VoucherType.PERCENT, 50),
                                                  testTime.plusHours(1), testTime.plusHours(1));
    private List<Voucher> allObject = List.of(insertedVoucher1, insertedVoucher2);


    @Nested
    @DisplayName("전체 상품 시")
    public class 전체_상품_조회_시 {
        @Nested
        @DisplayName("정상 조회 시")
        public class 정상_조회_시 {
            @Test
            @DisplayName("상품이 담긴 리스트를 응답한다.")
            public void 상품이_담긴_리스트를_응답한다() throws Exception {
                when(voucherService.findAll()).thenReturn(allObject);
                String returnJson = toJson(allObject.stream()
                                                    .map(VoucherResponse::from)
                                                    .collect(Collectors.toList()));

                var response = mvc.perform(get(BASE_API_URL));

                response.andExpect(status().isOk())
                        .andExpect(content().json(returnJson));
                verify(voucherService, atLeastOnce()).findAll();
            }

            @Test
            @DisplayName("조회될 데이터가 없을 경우 빈 리스트를 응답한다.")
            public void 조회될_데이터가_없을_경우_빈_리스트를_응답한다() throws Exception {
                when(voucherService.findAll()).thenReturn(List.of());

                var response = mvc.perform(get(BASE_API_URL));

                response.andExpect(status().isOk())
                        .andExpect(content().json("[]"));
            }
        }
    }

    @Nested
    @DisplayName("타입을 이용한 상품 조회 시")
    public class 타입을_이용한_상품_조회시 {
        @Nested
        @DisplayName("정상 조회될 경우")
        public class 정상_조회될_경우 {
            @ParameterizedTest
            @EnumSource(VoucherType.class)
            @DisplayName("상품 응답객체가 담긴 리스트를 응답한다.")
            public void 상품_응답객체가_담긴_리스트를_응답한다(VoucherType type) throws Exception {
                List<Voucher> returnObject = allObject.stream()
                                                      .filter(voucher -> voucher.getType() == type)
                                                      .collect(Collectors.toList());
                when(voucherService.findAllByType(type)).thenReturn(returnObject);
                String jsonObject = toJson(returnObject.stream()
                                                       .map(VoucherResponse::from)
                                                       .collect(Collectors.toList()));

                var response = mvc.perform(get(BASE_API_URL + "?type=" + type));

                response.andExpect(status().isOk())
                        .andExpect(content().json(jsonObject));
                verify(voucherService, atLeastOnce()).findAllByType(type);
            }


            @ParameterizedTest
            @EnumSource(VoucherType.class)
            @DisplayName("타입을 이용한 상품 조회 시 조회된 데이터가 없는 경우 빈 리스트를 응답한다.")
            public void 타입을_이용한_상품_조회_시_조회될_데이터가_없는_경우_빈_리스트를_응답한다(VoucherType type) throws Exception {
                when(voucherService.findAllByType(type)).thenReturn(List.of());

                var response = mvc.perform(get(BASE_API_URL + "?type=" + type));

                response.andExpect(status().isOk())
                        .andExpect(content().json("[]"));
                verify(voucherService, atLeastOnce()).findAllByType(type);
            }

            @Test
            @DisplayName("타입을 이용한 상품 조회 시 잘못된 타입을 입력할 경우 400 Bad Request로 응답한다.")
            public void 타입을_이용한_상품_조회_시_잘못된_타입을_입력할_경우_400_Bad_Reqeust로_응답한다() throws Exception {
                var response = mvc.perform(get(BASE_API_URL + "?type=invalidType"));

                response.andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    @DisplayName("날짜를 이용한 상품 조회시")
    public class 날짜를_이용한_상품_조회시 {
        @Nested
        @DisplayName("정상 조회될 경우")
        public class 정상_조회될_경우 {
            @Test
            @DisplayName("해당 날짜 범위 내 객체 데이터를 응답한다.")
            public void 해당_날짜_범위_내_객체_데이터를_응답한다() throws Exception {
                LocalDate startDate = testTime.minusDays(1).toLocalDate();
                LocalDate endDate = testTime.plusDays(1).toLocalDate();
                List<Voucher> returnObject = allObject.stream()
                                                      .filter(voucher -> (voucher.getCreatedAt().toLocalDate().isAfter(startDate)
                                                              && voucher.getCreatedAt().toLocalDate().isBefore(endDate)))
                                                      .collect(Collectors.toList());
                when(voucherService.findAllByDate(startDate, endDate)).thenReturn(returnObject);
                String jsonObject = toJson(returnObject.stream()
                                                       .map(VoucherResponse::from)
                                                       .collect(Collectors.toList()));

                var response = mvc.perform(get(BASE_API_URL +
                                                       "?fromDate=" + startDate + "&toDate=" + endDate));

                response.andExpect(status().isOk())
                        .andExpect(content().json(jsonObject));
                verify(voucherService, atLeastOnce()).findAllByDate(startDate, endDate);
            }

            @Test
            @DisplayName("조회된 데이터가 없는 경우 빈 리스트를 응답한다.")
            public void 조회될_데이터가_없는_경우_빈_리스트를_응답한다() throws Exception {
                LocalDate startDate = testTime.plusDays(1).toLocalDate();
                LocalDate endDate = testTime.plusDays(2).toLocalDate();
                List<Voucher> returnObject = allObject.stream()
                                                      .filter(voucher -> (voucher.getCreatedAt().toLocalDate().isAfter(startDate)
                                                              && voucher.getCreatedAt().toLocalDate().isBefore(endDate)))
                                                      .collect(Collectors.toList());
                when(voucherService.findAllByDate(startDate, endDate)).thenReturn(returnObject);

                var response = mvc.perform(get(BASE_API_URL +
                                                       "?fromDate=" + startDate + "&toDate=" + endDate));

                response.andExpect(status().isOk())
                        .andExpect(content().json(toJson(returnObject)));
            }
        }
    }

    @Nested
    @DisplayName("바우처 추가 시")
    public class 바우처_추가_시 {
        @Nested
        @DisplayName("정상적인 데이터가 입력되는 경우")
        public class 정상적인_데이터가_입력되는_경우 {
            @Test
            @DisplayName("생성된 데이터와 201 Create로 응답한다.")
            public void 생성된_데이터와_201_Create로_응답한다() throws Exception {
                Map<String, Object> requestBody = new HashMap<>();
                requestBody.put("type", "FIXED");
                requestBody.put("amount", "100");
                when(voucherService.create(any(Voucher.class))).thenReturn(insertedVoucher1);

                var response = mvc.perform(post(BASE_API_URL)
                                                   .contentType(MediaType.APPLICATION_JSON)
                                                   .content(objectMapper.writeValueAsString(requestBody)));

                String jsonString = toJson(VoucherResponse.from(insertedVoucher1));
                response.andExpect(status().isCreated())
                        .andExpect(content().json(jsonString));
            }

            @Test
            @DisplayName("amount가 범위 밖일 경우 400 Bad Request로 응답한다.")
            public void amount가_범위_밖일_경우_400_Bad_Request로_응답한다() throws Exception {
                Map<String, Object> requestBody = new HashMap<>();
                requestBody.put("type", "PERCENT");
                requestBody.put("amount", "150");

                var response = mvc.perform(post(BASE_API_URL)
                                                   .contentType(MediaType.APPLICATION_JSON)
                                                   .content(objectMapper.writeValueAsString(requestBody)));

                response.andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    @DisplayName("id를 이용한 상품 조회시")
    public class id를_이용한_상품_조회_시 {
        @Nested
        @DisplayName("정상 조회될 경우")
        public class 정상_조회될_경우 {
            @Test
            @DisplayName("해당 상품 데이터와 200 OK로 응답한다.")
            public void 해당_상품_데이터와_200_OK로_응답한다() throws Exception {
                long inputId = 1L;
                when(voucherService.findById(inputId)).thenReturn(insertedVoucher1);

                var response = mvc.perform(get(BASE_API_URL + "/" + inputId));

                response.andExpect(status().isOk())
                        .andExpect(content().json(toJson(VoucherResponse.from(insertedVoucher1))));
            }
        }

        @Nested
        @DisplayName("입력된 id값을 가진 상품이 없는 경우")
        public class 입력된_id값을_가진_상품이_없는_경우 {
            @Test
            @DisplayName("Not Found로 응답한다.")
            public void Not_Found로_응답한다() throws Exception {
                long inputId = 10L;
                when(voucherService.findById(inputId)).thenThrow(NotFoundException.class);

                var response = mvc.perform(get(BASE_API_URL + "/" + inputId));

                response.andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    @DisplayName("id를 이용한 상품 삭제 시")
    public class id를_이용한_상품_삭제_시 {
        @Nested
        @DisplayName("정상 삭제될 경우")
        public class 정상_삭제될_경우 {
            @Test
            @DisplayName("No Content로 응답한다.")
            public void No_Content로_응답한다() throws Exception {
                var response = mvc.perform(delete(BASE_API_URL + "/" + insertedVoucher1.getId()));

                response.andExpect(status().isNoContent());
            }
        }

        @Nested
        @DisplayName("비정상적인 id값을 입력할 경우")
        public class 비정상적인_id값을_입력할_경우 {
            @Test
            @DisplayName("Bad Request로 응답한다.")
            public void Bad_Request로_응답한다() throws Exception {
                String inputId = "invalidId";

                var response = mvc.perform(delete(BASE_API_URL + "/" + inputId));

                response.andExpect(status().isBadRequest());
            }
        }
    }

    private String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException();
        }
    }
}