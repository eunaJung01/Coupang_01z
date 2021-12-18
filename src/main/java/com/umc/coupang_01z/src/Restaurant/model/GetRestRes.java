package com.umc.coupang_01z.src.Restaurant.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetRestRes {
    private int restIdx;
    private String restName;
    private String restImg;
    private int categoryIdx;
    private int isCheetah;
    private double rate;
    private int deliveryFee;
    private int minOrderFee;
    private String restAddress;
    private double restLatitude;
    private double restLongitude;
}
