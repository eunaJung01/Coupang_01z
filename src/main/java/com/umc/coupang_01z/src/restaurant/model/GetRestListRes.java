package com.umc.coupang_01z.src.restaurant.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetRestListRes implements Comparable<GetRestListRes> {
    private int restIdx;
    private String restName;
    private String restImg;
    private int categoryIdx;
    private int isCheetah;
    private double rate;
    private int rateNum;
    private int deliveryFee;
    private double distance;
    private int packaging;

    public GetRestListRes(int restIdx, String restName, String restImg, int categoryIdx, int isCheetah, double rate, int deliveryFee, int packaging) {
        this.restIdx = restIdx;
        this.restName = restName;
        this.restImg = restImg;
        this.categoryIdx = categoryIdx;
        this.isCheetah = isCheetah;
        this.rate = rate;
        this.deliveryFee = deliveryFee;
        this.packaging = packaging;
    }

    @Override
    public int compareTo(GetRestListRes getRestListRes) {
        return Double.compare(distance, getRestListRes.getDistance());
    }

}
