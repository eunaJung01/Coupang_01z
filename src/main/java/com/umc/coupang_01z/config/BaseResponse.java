package com.umc.coupang_01z.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.umc.coupang_01z.config.BaseResponseStatus.SUCCESS;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class BaseResponse<T> { // BaseResponse 객체를 사용할 때 성공, 실패 경우
    @JsonProperty("isSuccess")
    private final Boolean isSuccess;
    private final int code;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    // 요청에 성공한 경우 (클라이언트가 받을 정보들)
    public BaseResponse(T result) {
        this.isSuccess = SUCCESS.isSuccess();
        this.message = SUCCESS.getMessage();
        this.code = SUCCESS.getCode();
        this.result = result;
    }

    // 요청에 실패한 경우
    public BaseResponse(BaseResponseStatus status) {
        this.isSuccess = status.isSuccess();
        this.message = status.getMessage();
        this.code = status.getCode();
    }

}
