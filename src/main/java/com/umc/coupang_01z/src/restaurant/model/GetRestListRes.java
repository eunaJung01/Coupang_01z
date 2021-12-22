package com.umc.coupang_01z.src.restaurant.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetRestListRes {
    private int restIdx;
    private String restName;
    private String restImg;
    private int categoryIdx;
    private int isCheetah;
    private double rate;
    private int deliveryFee;
}
