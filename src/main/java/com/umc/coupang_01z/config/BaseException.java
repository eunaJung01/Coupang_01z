package com.umc.coupang_01z.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BaseException extends Exception {
    private com.umc.coupang_01z.config.BaseResponseStatus status;  // BaseResponseStatus 객체에 매핑
}
