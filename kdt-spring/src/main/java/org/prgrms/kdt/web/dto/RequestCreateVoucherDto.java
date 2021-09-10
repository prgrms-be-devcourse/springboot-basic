package org.prgrms.kdt.web.dto;


public class RequestCreateVoucherDto {

    private Integer type;
    private Long value;

    public RequestCreateVoucherDto(Integer type, Long value) {
        this.type = type;
        this.value = value;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
