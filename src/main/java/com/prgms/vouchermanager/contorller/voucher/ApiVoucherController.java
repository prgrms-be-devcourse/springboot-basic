package com.prgms.vouchermanager.contorller.voucher;

import com.prgms.vouchermanager.domain.voucher.Voucher;
import com.prgms.vouchermanager.domain.voucher.VoucherType;
import com.prgms.vouchermanager.dto.CreateVoucherDto;
import com.prgms.vouchermanager.dto.UpdateVoucherDto;
import com.prgms.vouchermanager.service.voucher.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.List;
import java.util.UUID;

import static com.prgms.vouchermanager.exception.ExceptionType.INVALID_VOUCHER_INFO;
import static com.prgms.vouchermanager.exception.ExceptionType.INVALID_VOUCHER_PERCENT;

@RestController
@RequestMapping("/api/vouchers")
@RequiredArgsConstructor
public class ApiVoucherController {

    private final VoucherService voucherService;

    @GetMapping("/{id}")
    ResponseEntity<Voucher> findById(@PathVariable String id) {
        return ResponseEntity.ok(voucherService.findById(UUID.fromString(id)));
    }

    @GetMapping("/date")
    ResponseEntity<List<Voucher>> findByDate(@RequestParam String startTime, @RequestParam String endTime) {
        LocalDateTime startDate = LocalDateTime.parse(startTime);
        LocalDateTime endDate = LocalDateTime.parse(endTime);
        return ResponseEntity.ok(voucherService.findByDate(startDate, endDate));
    }

    @GetMapping("/type")
    ResponseEntity<List<Voucher>> findByType(@RequestParam String type) {
        VoucherType voucherType = VoucherType.valueOf(type);
        return ResponseEntity.ok(voucherService.findByType(voucherType));
    }

    @GetMapping("")
    ResponseEntity<List<Voucher>> findAll() {
        return ResponseEntity.ok(voucherService.findAll());
    }

    @PostMapping("/create")
    ResponseEntity<Voucher> create(@RequestBody CreateVoucherDto dto) {
        if (dto.getVoucherType() == 2 && dto.getValue() > 100) {
            throw new InputMismatchException(INVALID_VOUCHER_PERCENT.getMessage());
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(voucherService.create(dto));
    }


    @PutMapping("/{id}/edit")
    ResponseEntity<?> update(@PathVariable String id, @RequestBody UpdateVoucherDto dto) {
        voucherService.update(UUID.fromString(id), dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable String id) {
        voucherService.deleteById(UUID.fromString(id));
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler
    String ArgumentTypeMistMatch(HttpMessageNotReadableException e) {
        return INVALID_VOUCHER_INFO.getMessage();
    }

    @ExceptionHandler
    String DuplicatedId(DuplicateKeyException e) {
        return e.getMessage();
    }

    @ExceptionHandler
    String ExceedVoucherPercent(InputMismatchException e) {
        return e.getMessage();
    }

    @ExceptionHandler
    String EmptyResult(EmptyResultDataAccessException e) {
        return e.getMessage();
    }
}
