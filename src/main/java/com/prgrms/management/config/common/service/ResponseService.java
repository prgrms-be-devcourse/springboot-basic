package com.prgrms.management.config.common.service;

import com.prgrms.management.config.common.CommonResponse;
import com.prgrms.management.config.common.CommonResult;
import com.prgrms.management.config.common.ListResult;
import com.prgrms.management.config.common.SingleResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseService {

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

    public CommonResult getFailResult(CommonResponse commonResponse) {
        CommonResult result = new CommonResult();
        setFailResult(commonResponse, result);
        return result;
    }

    private void setFailResult(CommonResponse commonResponse, CommonResult result) {
        result.setSuccess(false);
        result.setCode(400);
        result.setMsg("실패");
    }

    private void setSuccessResult(CommonResult result) {
        result.setSuccess(true);
        result.setCode(200);
        result.setMsg("성공");
    }
}