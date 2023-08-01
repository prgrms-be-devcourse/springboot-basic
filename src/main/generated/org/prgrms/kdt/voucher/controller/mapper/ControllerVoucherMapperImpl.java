package org.prgrms.kdt.voucher.controller.mapper;

import javax.annotation.processing.Generated;
import org.prgrms.kdt.voucher.controller.dto.CreateVoucherApiRequest;
import org.prgrms.kdt.voucher.controller.dto.SearchApiRequest;
import org.prgrms.kdt.voucher.domain.VoucherType;
import org.prgrms.kdt.voucher.service.dto.CreateVoucherRequest;
import org.prgrms.kdt.voucher.service.dto.SearchRequest;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-01T21:55:34+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Component
public class ControllerVoucherMapperImpl implements ControllerVoucherMapper {

    @Override
    public CreateVoucherRequest convertRequest(CreateVoucherApiRequest createVoucherApiRequest) {
        if ( createVoucherApiRequest == null ) {
            return null;
        }

        VoucherType voucherType = null;
        double discountAmount = 0.0d;

        voucherType = createVoucherApiRequest.voucherType();
        discountAmount = createVoucherApiRequest.discountAmount();

        CreateVoucherRequest createVoucherRequest = new CreateVoucherRequest( voucherType, discountAmount );

        return createVoucherRequest;
    }

    @Override
    public SearchRequest convertRequest(SearchApiRequest searchApiRequest) {
        if ( searchApiRequest == null ) {
            return null;
        }

        long page = 0L;
        long recordSize = 0L;
        VoucherType voucherType = null;

        page = searchApiRequest.getPage();
        recordSize = searchApiRequest.getRecordSize();
        voucherType = searchApiRequest.getVoucherType();

        SearchRequest searchRequest = new SearchRequest( page, recordSize, voucherType );

        return searchRequest;
    }
}
