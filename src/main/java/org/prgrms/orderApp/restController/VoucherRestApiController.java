package org.prgrms.orderApp.restController;

import lombok.AllArgsConstructor;
import org.prgrms.orderApp.common.DateFormat;
import org.prgrms.orderApp.error.CustomRuntimeException;
import org.prgrms.orderApp.common.BasicResponse;
import org.prgrms.orderApp.voucher.VoucherDto;
import org.prgrms.orderApp.voucher.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.prgrms.orderApp.error.RestApiErrorCode.NOT_FOUNT_SEARCH_TYPE;
import static org.prgrms.orderApp.common.BasicCode.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/rest-api/v1/voucher")
public class VoucherRestApiController {
    private static Logger logger = LoggerFactory.getLogger(VoucherRestApiController.class);
    private VoucherService voucherService;



    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity getAllList(){
        return BasicResponse
                .generateResponse(
                        GET_ALL_LIST_SUCCESS,
                        voucherService.getAllVouchers()
                );
    }


    @RequestMapping(value = "/list/{search_type}", method = RequestMethod.GET)
    public ResponseEntity getListByCriteria(@PathVariable("search_type") String searchType,
                                            @RequestParam("first_criteria") String firstCriteria,
                                            @RequestParam(value = "seconds_criteria", required = false, defaultValue = "none") String secondsCriteria){

        if("TYPE".equals(searchType)) {
            return BasicResponse
                    .generateResponse(
                            GET_LIST_BY_TYPE_SUCCESS,
                            voucherService.getAllVoucherByType(firstCriteria)
                    );

        }else if("DATE".equals(searchType)){
            return BasicResponse.generateResponse(
                    GET_LIST_BY_DATE_SUCCESS,
                    voucherService.getAllVoucherByCreateDate(
                            LocalDateTime.parse(firstCriteria, DateTimeFormatter.ofPattern(DateFormat.SEARCH_DATE_FORMAT.getDataFormat())),
                            LocalDateTime.parse(secondsCriteria, DateTimeFormatter.ofPattern(DateFormat.SEARCH_DATE_FORMAT.getDataFormat()))
                    )
            );
        }else {
           throw new CustomRuntimeException(NOT_FOUNT_SEARCH_TYPE);
        }
    }


    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public ResponseEntity getDetails(@RequestParam("voucher_id") String voucherId){
        return BasicResponse
                .generateResponse(
                        GET_DETAIL_SUCCESS,
                        voucherService.getVoucherById(UUID.fromString(voucherId)
                        )
                );
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity deleteVoucherById(@RequestParam("voucher_id") String voucherId){
        var dataToDelete = voucherService.getVoucherById(UUID.fromString(voucherId));
        voucherService.deleteById(UUID.fromString(voucherId));
        return BasicResponse
                .generateResponse(
                        DELETE_VOUCHER_BY_ID_SUCCESS,
                        dataToDelete
                );
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity create(VoucherDto voucherDto){
        return BasicResponse
                .generateResponse(
                        CREATE_VOUCHER_SUCCESS,
                        voucherService.saveVoucher(voucherDto)
                );
    }
}
