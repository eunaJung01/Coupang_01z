package com.umc.coupang_01z.src.Restaurant.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetRestRes {
    private int categoryIdx;
    private String categoryName;
    private String categoryImg;
}
