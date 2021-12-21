package com.umc.coupang_01z.src.Restaurant.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetOptionChildRes {
    private int optCldIdx;
    private String optCldName;
    private int optCldPrice;
    private String status;
}
