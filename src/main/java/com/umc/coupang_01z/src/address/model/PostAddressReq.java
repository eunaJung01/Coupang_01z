package com.umc.coupang_01z.src.address.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostAddressReq {
    private int userIdx;
    private String address;
    private int division;
    private double latitude;
    private double longitude;
}
