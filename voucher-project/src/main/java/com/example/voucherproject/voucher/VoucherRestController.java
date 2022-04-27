package com.example.voucherproject.voucher;

import com.example.voucherproject.voucher.exception.VoucherNotFoundException;
import com.example.voucherproject.voucher.model.Voucher;
import com.example.voucherproject.voucher.dto.VoucherDTO;
import com.example.voucherproject.voucher.dto.VoucherQueryDTO;
import com.example.voucherproject.voucher.service.VoucherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class VoucherRestController {
    private final VoucherService voucherService;

    // 전체 조회기능 v
    @GetMapping(value = "/vouchers")
    public List<Voucher> vouchersView(){
        return voucherService.findAll();
    }

    // 바우처 추가 기능 v
    @PostMapping("/vouchers/new")
    public Voucher addVoucherRedirect(@RequestBody VoucherDTO voucherDTO){
        System.out.println(voucherDTO);
        return voucherService.createVoucher(voucherDTO.type().toString(), voucherDTO.amount());
    }

    // 바우처 상세 조회 v
    @GetMapping("/voucher/{id}")
    public Voucher voucherDetailView(@PathVariable UUID id){
        var maybeVoucher = voucherService.findById(id);

        if (maybeVoucher.isPresent()){
            return maybeVoucher.get();
        }
        throw new VoucherNotFoundException(String.format("%s voucher not found",id));
    }

    // 바우처 삭제 v
    @PostMapping("/voucher/{id}")
    public void deleteUser(@PathVariable UUID id){
        voucherService.deleteById(id);
    }



    // 조건별 조회 기능 v
    @PostMapping("/voucher/query")
    public List<Voucher> queryUser2(@RequestBody VoucherQueryDTO queryDTO){
        return voucherService.findByTypeAndDate(queryDTO.type(),queryDTO.from(), queryDTO.to());
    }

}
