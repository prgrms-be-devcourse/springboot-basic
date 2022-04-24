package com.prgrms.management.config.common.service;

import com.prgrms.management.config.ErrorMessageType;
import com.prgrms.management.config.common.CommonResult;
import com.prgrms.management.config.common.ListResult;
import com.prgrms.management.config.common.SingleResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseService {
    private static final String SUCCESS = "성공";
    private static final String FAIL = "실패";

    public <T> SingleResult<T> getSingleResult(T data) {
        SingleResult<T> result = new SingleResult<>();
        result.setData(data);
        setSuccessResult(result);
        return result;
    }

    public <T> ListResult<T> getListResult(List<T> list) {
        ListResult<T> result = new ListResult<>();
        result.setData(list);
        setSuccessResult(result);
        return result;
    }

    public CommonResult getSuccessResult() {
        CommonResult result = new CommonResult();
        setSuccessResult(result);
        return result;
    }

    public CommonResult getFailResult(ErrorMessageType type) {
        CommonResult result = new CommonResult();
        setFailResult(result);
        return result;
    }

    private void setFailResult(CommonResult result) {
        result.setSuccess(false);
        result.setCode(-1);
        result.setMsg(FAIL);
    }

    private void setSuccessResult(CommonResult result) {
        result.setSuccess(true);
        result.setCode(200);
        result.setMsg(SUCCESS);
    }
}