package org.promgrammers.springbootbasic.domain.voucher.controller;

import lombok.RequiredArgsConstructor;
import org.promgrammers.springbootbasic.domain.voucher.dto.request.CreateVoucherRequest;
import org.promgrammers.springbootbasic.domain.voucher.model.VoucherType;
import org.promgrammers.springbootbasic.domain.voucher.service.VoucherService;
import org.promgrammers.springbootbasic.web.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;

@RequestMapping("/api/vouchers")
@RestController
@RequiredArgsConstructor
public class VoucherApiController {

    private final VoucherService voucherService;

    @PostMapping("/create")
    public ResponseEntity<CommonResponse> save(@Valid @RequestBody CreateVoucherRequest request) {
        return new ResponseEntity<>(CommonResponse.builder()
                .code(HttpStatus.CREATED.name())
                .msg("바우처 생성 성공")
                .body(voucherService.create(request))
                .build()
                , HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<CommonResponse> findAll() {
        return new ResponseEntity<>(CommonResponse.builder()
                .code(OK.name())
                .msg("전체 조회 성공")
                .body(voucherService.findAll())
                .build()
                , OK);
    }

    @GetMapping
    public ResponseEntity<CommonResponse> findByType(@RequestParam VoucherType voucherType) {
        return new ResponseEntity<>(CommonResponse.builder()
                .code(OK.name())
                .msg("조회 성공")
                .body(voucherService.findByType(voucherType))
                .build(), OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse> findById(@PathVariable UUID id) {
        return new ResponseEntity<>(CommonResponse.builder()
                .code(OK.name())
                .msg("조회 성공")
                .body(voucherService.findById(id))
                .build(), OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> deleteById(@PathVariable UUID id) {
        voucherService.deleteById(id);
        return new ResponseEntity<>(CommonResponse.builder()
                .code(OK.name())
                .msg("삭제 완료")
                .body(null)
                .build(), OK);
    }

    @DeleteMapping
    public ResponseEntity<CommonResponse> deleteAll() {
        voucherService.deleteAll();
        return new ResponseEntity<>(CommonResponse.builder()
                .code(OK.name())
                .msg("삭제 완료")
                .build(), OK);
    }
}
